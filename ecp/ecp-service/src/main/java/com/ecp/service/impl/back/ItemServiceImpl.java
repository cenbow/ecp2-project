package com.ecp.service.impl.back;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ecp.bean.DeletedType;
import com.ecp.bean.ItemStatusType;
import com.ecp.common.util.FileUploadUtil;
import com.ecp.dao.ItemMapper;
import com.ecp.entity.CategoryAttr;
import com.ecp.entity.CategoryAttrValue;
import com.ecp.entity.Item;
import com.ecp.entity.ItemAttrValue;
import com.ecp.entity.ItemPicture;
import com.ecp.entity.Sku;
import com.ecp.entity.SkuPicture;
import com.ecp.entity.SkuPrice;
import com.ecp.service.back.ICategoryAttrService;
import com.ecp.service.back.ICategoryAttrValueService;
import com.ecp.service.back.IFavouriteService;
import com.ecp.service.back.IItemAttrValueService;
import com.ecp.service.back.IItemPictureService;
import com.ecp.service.back.IItemService;
import com.ecp.service.back.ISkuPictureService;
import com.ecp.service.back.ISkuPriceService;
import com.ecp.service.back.ISkuService;
import com.ecp.service.commons.DefaultConstants;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;

@Service("itemServiceBean")
public class ItemServiceImpl extends AbstractBaseService<Item, Long> implements IItemService {

	private static final Logger log = Logger.getLogger(ItemServiceImpl.class);
	
	private ItemMapper itemMapper;
	
	/**
	 * @param itemMapper the itemMapper to set
	 * set方式注入
	 */
	public void setItemMapper(ItemMapper itemMapper) {
		this.itemMapper = itemMapper;
		this.setMapper(itemMapper);
	}
	
	@Resource(name="itemPictureServiceBean")
	private IItemPictureService itemPictureService;//商品图片
	
	@Resource(name="skuServiceBean")
	private ISkuService skuService;//SKU
	@Resource(name="skuPictureServiceBean")
	private ISkuPictureService skuPictureService;//SKU图片
	@Resource(name="skuPriceServiceBean")
	private ISkuPriceService skuPriceService;//SKU价格
	
	@Resource(name="categoryAttrServiceBean")
	private ICategoryAttrService categoryAttrService;//类目属性
	@Resource(name="categoryAttrValueServiceBean")
	private ICategoryAttrValueService categoryAttrValueService;//类目属性值
	
	@Resource(name="itemAttrValueServiceBean")
	private IItemAttrValueService itemAttrValueService;//商品-属性值关系
	
	@Resource(name="favouriteServiceBean")
	private IFavouriteService favouriteService;//购物车
	
	
	/** 
	 * (non-Javadoc)
	 * @see com.ecp.service.IProductService#selectItemsByCondition(java.util.Map)
	 * 根据条件查询列表（条件为空时查询所有）
	 */
	@Override
	public List<Map<String, Object>> selectItemsByCondition(Map<String, Object> map) {
		return itemMapper.selectItemsByCondition(map);
	}

	/** 
	 * (non-Javadoc)
	 * @see com.ecp.service.IProductService#deleteByIds(java.lang.String)
	 * 批量删除（逻辑删除）
	 */
	@Override
	@Transactional
	public int deleteByIds(String ids) {
		try {
			String[] idArr = ids.split(",");
			int rows = 0;
			for(int i=0; i<idArr.length; i++){
				String itemId = idArr[i];
				if(StringUtils.isNotBlank(itemId)){
					rows = this.deleteByItemId(Long.valueOf(itemId));
					if(rows<=0){
						log.error("删除 id 为 "+idArr[i]+" 的商品信息异常，回滚sql");
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return 0;
					}
				}
			}
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}
	
	/**
	 * 删除单个商品（逻辑删除）
	 * @param itemId
	 * @return
	 */
	@Transactional
	private int deleteByItemId(Long itemId){
		try {
			int rows = 0;
			//删除商品表	item
			Example example = new Example(Item.class);
			example.createCriteria().andEqualTo("itemId", itemId);
			Item itemTemp = new Item();
			itemTemp.setDeleted(DeletedType.YES);
			rows = itemMapper.updateByExampleSelective(itemTemp, example);
			if(rows>0){
				//删除商品图片表	item_picture
				Example e = new Example(ItemPicture.class);
				e.createCriteria().andEqualTo("itemId", itemId);
				ItemPicture pictureTemp = new ItemPicture();
				pictureTemp.setDeleted(DeletedType.YES);
				itemPictureService.updateByExampleSelective(pictureTemp, e);
			}
			//删除商品属性值关系表	item_attr_value_item
			itemAttrValueService.deleteByItemId(itemId);
			this.deleteSkuRelateByItemId(itemId);//删除SKU相关数据（删除SKU表（item_sku）、删除SKU价格表（trade_sku_price）、删除SKU图片表（item_sku_picture））
			//删除购物车中的商品	item_favourite
			favouriteService.deleteByItemId(itemId);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return 0;
	}

	/**
	 * @see com.ecp.service.back.IItemService#saveItem(javax.servlet.http.HttpServletRequest, com.ecp.entity.Item, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 * 保存商品
	 */
	@Override
	@Transactional
	public int saveItem(HttpServletRequest request, Item item, String skuJson, String skuPriceJson, String skuShortSpec, String skuSpec) {
		
		try {
			//添加商品 item
			item.setHasPrice(1);
			item.setItemStatus(ItemStatusType.IS_PUT_OFF_SHELVES);
			
			int rows = itemMapper.insertSelective(item);
			if(rows>0){
				log.info("添加商品 item 成功");
			}
			//TODO 添加商品图片 item_picture
			//处理上传缩略图
			List<String> filePathList = this.processUploadFile(request, new ArrayList<ItemPicture>());
			//如果上传图片为空时，添加默认图片
			if(filePathList!=null && filePathList.size()>0){
				
			}else{
				//TODO 此处添加默认图片
				filePathList = new ArrayList<String>();
				filePathList.add(DefaultConstants.DEFAULT_PICTURE_PATH);
			}
			
			//根据上传图片数量创建商品图片List集合
			List<ItemPicture> pictureList = new ArrayList<ItemPicture>();
			for(String filePath : filePathList){
				pictureList.add(new ItemPicture(item.getItemId(), filePath));
			}
			
			//循环商品图片List集合，添加商品图片信息
			for(int i=0; i<pictureList.size(); i++){
				ItemPicture picture = pictureList.get(i);
				//添加商品图片
				rows = itemPictureService.insertSelective(picture);
			}
			
			if(rows>0){
				//添加商品属性值关系表
				if(StringUtils.isNotBlank(item.getAttrSale())){
					//TODO 如果销售属性为空时，是否添加？
					rows = this.saveItemAttrValue(item.getItemId(), item.getAttrSale());
				}
				if(rows>0){
					//添加SKU信息
					if(StringUtils.isBlank(skuJson) || skuJson.equals("[]")){
						//TODO 如果没有SKU信息时，手动创建一个SKU，此处添加默认SKU；注：商品需要有默认图片和默认SKU
						List<Sku> skuList = new ArrayList<Sku>();
						Sku sku = new Sku();
						sku.setVolume(item.getVolume());
						sku.setWeight(item.getWeight());
						sku.setSkuShortSpec(skuShortSpec);
						sku.setSkuSpec(skuSpec);
						skuList.add(sku);
						skuJson = JSON.toJSONString(skuList);
						List<SkuPrice> skuPriceList = new ArrayList<SkuPrice>();
						SkuPrice skuPrice = new SkuPrice();
						skuPrice.setCostPrice(item.getMarketPrice2());
						skuPrice.setMarketPrice(item.getGuidePrice());
						skuPrice.setSellPrice(item.getMarketPrice());
						skuPrice.setHighestPrice(item.getHighestPrice());
						skuPrice.setLowestPrice(item.getLowestPrice());
						skuPrice.setHardCostPrice(item.getHardCostPrice());
						skuPriceList.add(skuPrice);
						skuPriceJson = JSON.toJSONString(skuPriceList);
					}
					rows = this.processSkuRelate(item, filePathList, skuJson, skuPriceJson);
					if(rows>0){
						return rows;
					}else{
						log.error("保存SKU相关数据异常");
					}
				}else{
					log.error("保存商品属性值关系 item_attr_value_item 异常");
				}
			}else{
				log.error("添加商品图片 item_picture 异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存商品异常，事务回滚！", e);
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}
	

	/**
	 * @see com.ecp.service.back.IItemService#updateItem(javax.servlet.http.HttpServletRequest, com.ecp.entity.Item, java.lang.String, java.lang.String, boolean, java.lang.String, java.lang.String)
	 * 修改商品
	 */
	@Override
	@Transactional
	public int updateItem(HttpServletRequest request, Item item, String skuJson, String skuPriceJson, boolean isSaveSku, String skuShortSpec, String skuSpec) {
		try {
			if(item.getItemId()==null || item.getItemId()<=0){
				return 0;
			}
			//修改商品 item
			int rows = itemMapper.updateByPrimaryKeySelective(item);
			if(rows>0){
				log.info("修改商品 item 成功");
			}
			//TODO 修改商品图片 item_picture
			//处理上传缩略图
			List<String> filePathList = this.processUploadFile(request, new ArrayList<ItemPicture>());
			//如果上传图片为空时，添加默认图片
			if(filePathList!=null && filePathList.size()>0){
				//根据上传图片数量创建商品图片List集合
				List<ItemPicture> pictureList = new ArrayList<ItemPicture>();
				for(String filePath : filePathList){
					pictureList.add(new ItemPicture(item.getItemId(), filePath));
				}
				//逻辑删除原来的图片数据
				//TODO 修改时如果未选择上传图片，则图片还是原来的图片，不修改
				/*List<ItemPicture> pictList = itemPictureService.getByItemId(item.getItemId());
				for(int i=0; i<pictList.size(); i++){
					ItemPicture pict = pictList.get(i);
					FileUploadUtil.deleteFile(request, pict.getPictureUrl());
				}*/
				
				//逻辑删除商品图片
				Example e = new Example(ItemPicture.class);
				e.createCriteria().andEqualTo("itemId", item.getItemId());
				ItemPicture pictureTemp = new ItemPicture();
				pictureTemp.setDeleted(DeletedType.YES);
				itemPictureService.updateByExampleSelective(pictureTemp, e);
				
				//循环商品图片List集合，添加商品图片信息
				for(int i=0; i<pictureList.size(); i++){
					ItemPicture picture = pictureList.get(i);
					//添加商品图片
					rows = itemPictureService.insertSelective(picture);
				}
			}else{
				if(isSaveSku){//如果更新sku时
					log.info("更新sku，且未上传图片；获取原来商品的图片作为sku图片");
					//TODO 修改时如果未选择上传图片，则图片还是原来的图片，不修改
					filePathList = new ArrayList<String>();
					
					List<ItemPicture> pictureList = itemPictureService.getByItemId(item.getItemId());
					//循环商品图片List集，获取商品图片信息并添加到缩略图集合（filePathList）
					for(int i=0; i<pictureList.size(); i++){
						ItemPicture picture = pictureList.get(i);
						//商品图片不为空则添加到缩略图集合
						if(StringUtils.isNotBlank(picture.getPictureUrl())){
							filePathList.add(picture.getPictureUrl());
						}
					}
				}
			}
			
			if(rows>0){
				//修改商品属性值关系表
				//添加新商品属性值关系之前先删除该商品对应原来数据
				itemAttrValueService.deleteByItemId(item.getItemId());
				
				//添加新商品属性值关系
				if(StringUtils.isNotBlank(item.getAttrSale())){
					rows = this.saveItemAttrValue(item.getItemId(), item.getAttrSale());
				}else{
					//TODO 如果销售属性为空时，是否添加？如何添加？
				}
				if(rows>0){
					/*if(!isSaveSku){//如果不更新sku时
						log.info("未重新选择sku，只更新sku信息");
						//如果上传图片不为空时，更新sku图片
						if(filePathList!=null && filePathList.size()>0){
							log.info("已上传图片，更新sku图片");
							rows = this.updateSkuInfo(skuJson, skuPriceJson, filePathList, item.getItemId());//如果未重新选择sku，只更新sku信息时，且上传图片不为空时，更新sku图片
							if(rows<=0){
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							}
						}
						return rows;
					}*/
					//修改SKU相关信息
					//添加新SKU相关信息前先删除该商品对应的SKU相关信息（删除sku相关信息放在处理sku相关信息方法中处理）
					//this.deleteSkuRelateByItemId(item.getItemId());//逻辑删除SKU相关信息
					
					//添加新SKU相关信息
					if(isSaveSku){//如果重新更新sku时
						if(StringUtils.isBlank(skuJson) || skuJson.equals("[]")){
							//TODO 如果没有SKU信息时，手动创建一个SKU，此处添加默认SKU；注：商品需要有默认图片和默认SKU
							List<Sku> skuList = new ArrayList<Sku>();
							Sku sku = new Sku();
							sku.setVolume(item.getVolume());
							sku.setWeight(item.getWeight());
							sku.setSkuShortSpec(skuShortSpec);
							sku.setSkuSpec(skuSpec);
							skuList.add(sku);
							skuJson = JSON.toJSONString(skuList);
							List<SkuPrice> skuPriceList = new ArrayList<SkuPrice>();
							SkuPrice skuPrice = new SkuPrice();
							skuPrice.setCostPrice(item.getMarketPrice2());
							skuPrice.setMarketPrice(item.getGuidePrice());
							skuPrice.setSellPrice(item.getMarketPrice());
							skuPrice.setHighestPrice(item.getHighestPrice());
							skuPrice.setLowestPrice(item.getLowestPrice());
							skuPrice.setHardCostPrice(item.getHardCostPrice());
							skuPriceList.add(skuPrice);
							skuPriceJson = JSON.toJSONString(skuPriceList);
						}
						this.deleteSkuRelateByItemId(item.getItemId());//逻辑删除SKU相关信息
					}else{//如果不重新更新sku时
						log.info("未重新选择sku，只更新sku信息");
						if(StringUtils.isBlank(skuJson) || skuJson.equals("[]")){
							//更新默认sku价格和重量等
							rows = this.updateDefaultSkuByItemId(item);
							if(rows<=0){
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							}
							//如果上传图片不为空时，更新sku图片
							if(filePathList!=null && filePathList.size()>0){
								log.info("已上传图片，更新sku图片");
								rows = this.updateSkuPictureByItemId(filePathList, item.getItemId());//如果未重新选择sku，只更新sku信息时，且上传图片不为空时，更新sku图片
								if(rows<=0){
									log.error("sku信息为空，只更新sku图片异常");
									TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
								}
							}
							return rows;
						}
					}
					if((filePathList==null || filePathList.size()<=0) && (StringUtils.isBlank(skuJson) || skuJson.equals("[]"))){
						//图片和sku都为空时不做任何操作
					}else{
						rows = this.processSkuRelate(item, filePathList, skuJson, skuPriceJson);
					}
					if(rows>0){
						return rows;
					}else{
						log.error("修改SKU相关数据异常");
					}
				}else{
					log.error("修改商品属性值关系 item_attr_value_item 异常");
				}
			}else{
				log.error("修改商品图片 item_picture 异常");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("修改商品异常，事务回滚！", e);
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}
	
	/**
	 * 修改默认sku相关信息
	 * @param item
	 * @return
	 */
	@Transactional
	private int updateDefaultSkuByItemId(Item item){
		log.info("修改默认sku相关信息");
		int rows = 1;
		List<Sku> skuListTemp = skuService.getByItemId(item.getItemId());
		List<Long> skuIds = new ArrayList<Long>();
		for(int i=0; i<skuListTemp.size(); i++){
			Sku sku = skuListTemp.get(i);
			skuIds.add(sku.getSkuId());
		}
		if(skuIds!=null && skuIds.size()>0){
			for(int j=0; j<skuIds.size(); j++){
				Example example = new Example(Sku.class);
				example.createCriteria().andEqualTo("skuId", skuIds.get(j));
				Sku sku = new Sku();
				sku.setVolume(item.getVolume());
				sku.setWeight(item.getWeight());
				rows = skuService.updateByExampleSelective(sku, example);
				if(rows>0){
					example = new Example(SkuPrice.class);
					example.createCriteria().andEqualTo("skuId", skuIds.get(j));
					SkuPrice skuPrice = new SkuPrice();
					skuPrice.setCostPrice(item.getMarketPrice2());
					skuPrice.setMarketPrice(item.getGuidePrice());
					skuPrice.setSellPrice(item.getMarketPrice());
					skuPrice.setHighestPrice(item.getHighestPrice());
					skuPrice.setLowestPrice(item.getLowestPrice());
					skuPrice.setHardCostPrice(item.getHardCostPrice());
					rows = skuPriceService.updateByExampleSelective(skuPrice, example);
					if(rows<=0){
						log.error("更新sku价格等异常");
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						break;
					}
				}else{
					log.error("更新sku重量等异常");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					break;
				}
				
			}
		}
		return rows;
	}
	
	/**
	 * 如果不更新sku信息时，且上传图片不为空时，更新sku图片，根据商品id
	 * @param filePathList
	 * @param itemId
	 */
	@Transactional
	private int updateSkuPictureByItemId(List<String> filePathList, Long itemId){
		try {
			//如果上传图片不为空时，更新sku图片
			List<Sku> skuListTemp = skuService.getByItemId(itemId);
			List<Long> skuIds = new ArrayList<Long>();
			for(int i=0; i<skuListTemp.size(); i++){
				Sku sku = skuListTemp.get(i);
				skuIds.add(sku.getSkuId());
			}
			if(skuIds!=null && skuIds.size()>0){
				skuPictureService.deleteBySkuIds(skuIds);//逻辑删除SKU图片信息
				
				int rows = 0;
				for(int j=0; j<skuIds.size(); j++){
					Long skuId = skuIds.get(j);
					for(String filePath : filePathList){
						SkuPicture skuPicture = new SkuPicture();
						skuPicture.setCreated(new Date());
						skuPicture.setModified(new Date());
						skuPicture.setPictureStatus((byte)1);
						skuPicture.setSortNumber((byte)1);
						skuPicture.setPictureUrl(filePath);
						skuPicture.setSkuId(skuId);
						rows = skuPictureService.insertSelective(skuPicture);
						if(rows>0){
							continue;
						}else{
							log.error("循环更新SKU图片 item_sku_picture 出错");
							break;
						}
					}
					if(rows<=0){
						log.error("循环更新SKU图片 item_sku_picture 出错");
						break;
					}
				}
				return rows;
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("循环更新SKU图片 item_sku_picture 异常");
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}
	
	/**
	 * 如果不更新sku信息时，且上传图片不为空时，更新sku图片，根据skuid
	 * @param filePathList
	 * @param itemId
	 * @param skuId
	 * @return
	 */
	@Transactional
	private int updateSkuPictureBySkuId(List<String> filePathList, Long skuId){
		try {
			int rows = 1;
			//如果上传图片不为空时，更新sku图片
			if(skuId!=null && skuId>0){
				
				Example example = new Example(SkuPicture.class);
				example.createCriteria().andEqualTo("skuId", skuId).andEqualTo("deleted", DeletedType.NO);
				SkuPicture skuPicture = new SkuPicture();
				skuPicture.setDeleted(DeletedType.YES);
				skuPictureService.updateByExampleSelective(skuPicture, example);//逻辑删除SKU图片信息
				
				for(String filePath : filePathList){
					SkuPicture temp = new SkuPicture();
					temp.setCreated(new Date());
					temp.setModified(new Date());
					temp.setPictureStatus((byte)1);
					temp.setSortNumber((byte)1);
					temp.setPictureUrl(filePath);
					temp.setSkuId(skuId);
					rows = skuPictureService.insertSelective(temp);
					if(rows>0){
						continue;
					}else{
						log.error("循环更新SKU图片 item_sku_picture 出错");
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						break;
					}
				}
			}
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("循环更新SKU图片 item_sku_picture 异常");
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}
	
	/**
	 * 逻辑删除SKU相关数据
	 * @param itemId
	 * @param isDelskuPicture	是否删除SKU图片
	 */
	@Transactional
	private void deleteSkuRelateByItemId(Long itemId){
		try {
			
			List<Long> skuIds = new ArrayList<Long>();
			List<Sku> skuListTemp = skuService.getByItemId(itemId);
			for(Sku sku : skuListTemp){
				skuIds.add(sku.getSkuId());
			}
			if(skuIds!=null && skuIds.size()>0){
				Example skuExample = new Example(Sku.class);
				skuExample.createCriteria().andEqualTo("itemId", itemId).andEqualTo("deleted", DeletedType.NO);
				Sku skuTemp = new Sku();
				skuTemp.setDeleted(DeletedType.YES);
				skuService.updateByExampleSelective(skuTemp, skuExample);//逻辑删除SKU信息
				
				Example priceExample = new Example(Sku.class);
				priceExample.createCriteria().andEqualTo("itemId", itemId).andEqualTo("deleted", DeletedType.NO);
				SkuPrice priceTemp = new SkuPrice();
				priceTemp.setDeleted(DeletedType.YES);
				skuPriceService.updateByExampleSelective(priceTemp, priceExample);//逻辑删除SKU价格信息
				
				skuPictureService.deleteBySkuIds(skuIds);//逻辑删除SKU图片信息
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return;
		}
	}
	
	/**
	 * 处理SKU相关表
	 * @param itemId
	 * @param pictureUrl
	 * @param skuJson
	 * @param skuPriceJson
	 * @return
	 */
	@Transactional
	private int processSkuRelate(Item item, List<String> filePathList, String skuJson, String skuPriceJson){
		try {
			
			int rows = 0;
			//添加sku item_sku
			List<Sku> skuList = JSONArray.parseArray(skuJson, Sku.class);
			int size = skuList.size();
			for(int i=0; i<size; i++){
				Sku sku = skuList.get(i);
				sku.setItemId(item.getItemId());
				sku.setSkuStatus(1);
				sku.setSkuType(1);
				if(sku.getSkuId()!=null && sku.getSkuId()>0){
					sku.setModified(new Date());
					rows = skuService.updateByPrimaryKeySelective(sku);
				}else{
					sku.setCreated(new Date());
					rows = skuService.insertSelective(sku);//添加新的sku信息
				}
				if(rows>0){
					//添加sku价格 trade_sku_price
					List<SkuPrice> skuPriceList = JSONArray.parseArray(skuPriceJson, SkuPrice.class);
					if(skuPriceList!=null && !skuPriceList.isEmpty()){
						SkuPrice skuPrice = skuPriceList.get(i);
						skuPrice.setItemId(item.getItemId());
						skuPrice.setSkuId(sku.getSkuId());
						if(skuPrice.getId()!=null && skuPrice.getId()>0){
							skuPrice.setUpdateTime(new Date());
							rows = skuPriceService.updateByPrimaryKeySelective(skuPrice);
						}else{
							skuPrice.setCreateTime(new Date());
							rows = skuPriceService.insertSelective(skuPrice);//添加新的sku价格信息
						}
					}
					
					if(rows>0){
						//添加sku图片 item_sku_picture
						if(filePathList!=null && filePathList.size()>0){
							rows = this.updateSkuPictureBySkuId(filePathList, sku.getSkuId());//如果未重新选择sku，只更新sku信息时，且上传图片不为空时，更新sku图片
							if(rows>0){
								continue;
							}else{
								log.error("循环添加SKU图片 item_sku_picture 异常");
								break;
							}
							/*for(String filePath : filePathList){
								SkuPicture skuPicture = new SkuPicture();
								skuPicture.setCreated(new Date());
								skuPicture.setModified(new Date());
								skuPicture.setPictureStatus((byte)1);
								skuPicture.setSortNumber((byte)1);
								skuPicture.setPictureUrl(filePath);
								skuPicture.setSkuId(sku.getSkuId());
								rows = skuPictureService.insertSelective(skuPicture);
								if(rows>0){
									continue;
								}else{
									log.error("循环添加SKU图片 item_sku_picture 异常");
									break;
								}
							}*/
						}
						if(rows>0){
							continue;
						}else{
							log.error("添加SKU图片 item_sku_picture 异常");
							break;
						}
					}else{
						log.error("添加SKU价格 item_sku_price 异常");
						break;
					}
				}else{
					log.error("添加SKU item_sku_price 异常");
					break;
				}
			}
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存SKU相关数据异常", e);
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}
	
	/**
	 * 保存类目属性值关系
	 * @param itemId
	 * @param attrSale
	 * @return
	 */
	@Transactional
	private int saveItemAttrValue(Long itemId, String attrSale){
		
		try {
			
			int rows = 0;
			String[] attrSaleArrStr = attrSale.split(",");
			for(int i=0; i<attrSaleArrStr.length; i++){
				String str = attrSaleArrStr[i];
				String[] attrSaleStr = str.split(":");
				Long attrId = Long.valueOf(attrSaleStr[0].toString());
				Long valueId = Long.valueOf(attrSaleStr[1].toString());
				CategoryAttr attr = categoryAttrService.getByAttrId(attrId);
				CategoryAttrValue value = categoryAttrValueService.getByValueId(valueId);
				ItemAttrValue temp = new ItemAttrValue();
				temp.setAttrId(attrId);
				temp.setAttrType(attr.getAttrType().intValue());
				temp.setCreated(new Date());
				temp.setItemId(itemId);
				temp.setModified(new Date());
				temp.setSortNumber(value.getSortNumber().intValue());
				temp.setStatus(value.getStatus().intValue());
				temp.setValueId(valueId);
				rows = itemAttrValueService.insertSelective(temp);
			}
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("保存类目属性值关系异常", e);
		}
		return 0;
	}
	
	/**
	 * 方法功能：处理上传文件
	 * @param request
	 * @param 
	 * @return
	 * 		List<String>:上传成功返回路径集合
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年1月11日 下午6:09:34</p>
	 */
	private List<String> processUploadFile(HttpServletRequest request, List<ItemPicture> pictureList){
		try {
			//获取上传背景图文件
			List<String> filePathList = FileUploadUtil.getFiles2UploadPath(request, "item", "pictureImg");
			if(filePathList!=null && filePathList.size()>0){
				if(pictureList!=null && pictureList.size()>0){
					for(ItemPicture picture : pictureList){
						if(!FileUploadUtil.deleteFile(request, picture.getPictureUrl())){
							log.error("文件不存在或已删除 缩略图路径："+picture.getPictureUrl());
						}
					}
				}
				return filePathList;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.info("上传文件为空，返回null");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("上传文件异常，返回null");
		}
		
		return null;
	}
	
	/**
	 * 批量修改商品状态
	 * @see com.ecp.service.back.IItemService#updateStatusBatchByIds(java.lang.String, java.lang.Integer)
	 */
	@Override
	public int updateStatusBatchByIds(String itemIds, Integer itemStatus){
		String[] itemIdArr = itemIds.split(",");
		int rows = 0;
		for(int i=0; i<itemIdArr.length; i++){
			String itemId = itemIdArr[i];
			if(StringUtils.isNotBlank(itemId)){
				Item item = new Item();
				item.setItemId(Long.valueOf(itemId));
				item.setItemStatus(itemStatus);
				rows = itemMapper.updateByPrimaryKeySelective(item);
				if(rows<=0){
					log.error("修改 id 为 "+itemIdArr[i]+" 的商品上下架状态异常，回滚sql");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return 0;
				}
			}
		}
		return rows;
	}
	
	public static void main(String[] args) {
		for(int j=0; j<10; j++){
			System.out.println("外循环 第 "+j+" 次循环。");
			for(int i=0; i<10; i++){
				System.out.println("内循环 第 "+i+" 次循环。");
				if(i==5){
					break;
				}
			}
			if(j==3){
				break;
			}
		}
	}

}
