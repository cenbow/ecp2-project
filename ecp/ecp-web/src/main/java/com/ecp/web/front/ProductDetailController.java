package com.ecp.web.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.ecp.bean.CategoryAttrBean;
import com.ecp.bean.SkuPriceBean;
import com.ecp.bean.SkuSpecBean;
import com.ecp.bean.SkuType;
import com.ecp.bean.constants.AttributeType;
import com.ecp.entity.Brand;
import com.ecp.entity.Item;
import com.ecp.entity.SkuPicture;
import com.ecp.service.front.IBrandService;
import com.ecp.service.front.ICategoryAttrService;
import com.ecp.service.front.IItemAttrService;
import com.ecp.service.front.IItemService;
import com.ecp.service.front.ISkuPictureService;
import com.ecp.service.front.ISkuService;

/**
 * @ClassName ProductDetailController
 * @Description 商品详情-控制器
 * @author Administrator
 * @Date 2017年5月7日 下午5:54:02
 * @version 1.0.0
 */
@Controller
@RequestMapping("/front/product")
public class ProductDetailController {
	final String RESPONSE_THYMELEAF = "thymeleaf/front/";
	final String RESPONSE_JSP = "jsps/front/";

	@Autowired
	IItemService itemService;
	@Autowired
	ICategoryAttrService categoryAttrService;
	@Autowired
	IItemAttrService itemAttrService;
	@Autowired
	ISkuService skuService;
	@Autowired
	ISkuPictureService skuPictureService;
	/*@Autowired
	IFavouriteService userFavourite;*/ //用户收藏
	@Autowired
	IBrandService brandService;

	/**
	 * @Description 按spuId 导航到 产品详情
	 * @param itemId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail/{itemId}", method = RequestMethod.GET)
	public String productDetail(@PathVariable("itemId") long itemId, Model model) {

		// (1)读取SPU信息
		Item item = itemService.getItemById(itemId);
		model.addAttribute("item", item);
		
		//(2)查询品牌
		Brand brand=brandService.selectByPrimaryKey(item.getBrand());
		model.addAttribute("brand",brand);

		// (3)读取SPU所属类目的销售属性,此处只使用属性的名称、个数
		getItemSaleAttr(item,model);

		// (4)读取主sku信息 包括三个方面的内容 :sku信息、价格、图片
		List<SkuPriceBean> skuList = skuService.getSkuByIdAndType(item.getItemId(), SkuType.PRIMARY);
		model.addAttribute("sku", skuList.get(0)); // 由于返回的是list类型

		// (5)读取主sku图片
		SkuPriceBean tempSku = skuList.get(0);
		List<SkuPicture> skuPicts = skuPictureService.getSkuPictureById(tempSku.getSku_id());
		model.addAttribute("skuPicts", skuPicts);

		// (6)产品介绍、规则包装、售后保障。
		getSkuSpec(tempSku.getSku_id(),model);
		return RESPONSE_THYMELEAF + "product_detail";
	}
	
	/**
	 * @Description 获取sku 技术规格说明并置于model
	 * @param skuId
	 * @param model
	 */
	private void getSkuSpec(long skuId,Model model){
		List<Map<String, String>> skuIntroduce = skuService.getSkuIntroduce(skuId);
		model.addAttribute("skuIntroduce", skuIntroduce.get(0));
		List<SkuSpecBean> skuSpecList=json2List(skuIntroduce.get(0).get("sku_spec"));
		model.addAttribute("skuSpecList", skuSpecList);  //技术规格
	}

	/**
	 * @Description 通过SPU'ID , SKU'ID 查询产品详情
	 * @param itemId
	 *            spu id
	 * @param skuId
	 *            sku id
	 * @param model
	 * @return 详情页面
	 */
	@RequestMapping(value = "/detail/{itemId}/{skuId}", method = RequestMethod.GET)
	public String skuDetailById(@PathVariable("itemId") long itemId, @PathVariable("skuId") long skuId, Model model) {

		// (1)读取SPU信息
		Item item = itemService.getItemById(itemId);
		model.addAttribute("item", item);

		// (2)读取SPU所属类目的销售属性,此处只使用属性的名称、个数
		getItemSaleAttr(item,model);
		
		//(3)查询品牌
		Brand brand=brandService.selectByPrimaryKey(item.getBrand());
		model.addAttribute("brand",brand);

		// (4)读取sku信息 包括三个方面的内容 :sku信息、价格、图片
		SkuPriceBean sku = skuService.getSkuBySkuId(skuId);
		model.addAttribute("sku", sku);

		// (5)读取sku图片
		List<SkuPicture> skuPicts = skuPictureService.getSkuPictureById(sku.getSku_id());
		model.addAttribute("skuPicts", skuPicts);

		// (6)产品介绍、规则包装、售后保障、用户评论。
		getSkuSpec(skuId,model);

		return RESPONSE_THYMELEAF + "product_detail";
	}
	
	/**
	 * @Description 自JSON生成对象
	 * @param resp
	 * @return
	 */
	private List<SkuSpecBean> json2List(String resp) {
		List<SkuSpecBean> list = JSONArray.parseArray(resp,SkuSpecBean.class);
		return list; 
	} 

	/**
	 * @Description 通过itemid 及 skuAttribute查询SKU详情
	 * @param itemId
	 *            SPU ID
	 * @param skuAttribute
	 *            sku attribute
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sku/detail", method = RequestMethod.POST)
	public String skuDetail(long itemId, String skuAttribute, Model model) {

		// (1)读取SPU信息
		Item item = itemService.getItemById(itemId);
		model.addAttribute("item", item);

		// (2)读取SPU所属类目的销售属性,此处只使用属性的名称、个数
		getItemSaleAttr(item,model);
		
		//(3)查询品牌
		Brand brand=brandService.selectByPrimaryKey(item.getBrand());
		model.addAttribute("brand",brand);

		// (4)读取sku信息 包括三个方面的内容 :sku信息、价格、图片
		List<SkuPriceBean> skuList = skuService.getSkuByIdAndAttr(item.getItemId(), skuAttribute);
		model.addAttribute("sku", skuList.get(0)); // 由于返回的是list类型

		// (5)读取主sku图片
		SkuPriceBean tempSku = skuList.get(0);
		List<SkuPicture> skuPicts = skuPictureService.getSkuPictureById(tempSku.getSku_id());
		model.addAttribute("skuPicts", skuPicts);

		// (6)产品介绍、规则包装、售后保障、用户评论。
		getSkuSpec(tempSku.getSku_id(),model);

		return RESPONSE_THYMELEAF + "product_detail";
	}
	
	/**
	 * @Description 获取spu销售属性
	 * @param spuId
	 */
	private void getItemSaleAttr(Item item,Model model){
		List<Map<String, Object>> attrValueList = new ArrayList<Map<String, Object>>();

		List<CategoryAttrBean> cateAttrList = categoryAttrService.getCategoryAttrByCidAndType(item.getCid(),
				AttributeType.SALE_ATTR);
		for (CategoryAttrBean attrBean : cateAttrList) {
			HashMap<String, Object> attrValMap = new HashMap<String, Object>(); // 属性及属性值列表
			attrValMap.put("attrName", attrBean.getAttr_name()); // 属性名称
			List<Map<String, String>> valueList = itemAttrService.getItemAttrValList(item.getItemId(),
					attrBean.getAttr_id()); // 读取 item属性值 列表
			attrValMap.put("valueList", valueList);
			if (valueList.size() > 0) // 只有属性值个数大于0时才显示
				attrValueList.add(attrValMap); // 加入列表中
		}
		model.addAttribute("attrValueList", attrValueList);
	}

}
