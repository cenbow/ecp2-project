package com.ecp.web.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecp.bean.CategoryAttrBean;
import com.ecp.bean.CategoryBrandBean;
import com.ecp.bean.ItemStatusType;
import com.ecp.bean.SearchCondBean;
import com.ecp.bean.constants.AttributeType;
import com.ecp.entity.Category;
import com.ecp.entity.Item;
import com.ecp.entity.ItemPicture;
import com.ecp.service.front.IBrandService;
import com.ecp.service.front.ICategoryAttrService;
import com.ecp.service.front.ICategoryBrandService;
import com.ecp.service.front.ICategoryService;
import com.ecp.service.front.IItemPictureService;
import com.ecp.service.front.IItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName ProductSearchController
 * @Description 产品查询-控制器（包括：分类查询、关键字查询、产品代码）
 * @author Administrator
 * @Date 2017年5月6日 下午9:43:10
 * @version 1.0.0
 */
@Controller
@RequestMapping("/front/search")
public class SearchController {
	final String RESPONSE_THYMELEAF = "thymeleaf/front/";
	final String RESPONSE_JSP = "jsps/front/";
	final int PAGE_SIZE = 10;

	/**
	 * 查询的数据格式为：
	 * 
	 * { "cid": "1", "brands": "1,2,3", "attrs": [ { "attr_id": "1",
	 * "attr_vals": "1,2,3" }, { "attr_id": "2", "attr_vals": "5,6,7" } ] }
	 * 对应的POJO类 SearchCondBean
	 * 
	 */

	@Autowired
	IItemService itemService;
	@Autowired
	ICategoryBrandService cateBrandService;
	@Autowired
	ICategoryAttrService categoryAttrService;
	@Autowired
	IItemPictureService itemPictureService;
	@Autowired
	ICategoryService categoryService;
	@Autowired
	IBrandService brandService;

	/**
	 * @Description 通过关键字查询
	 * @param keywords
	 *            关键字
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/keywords", method = RequestMethod.GET)
	public String keywordsSearch(String keywords, Model model) {
		String url = RESPONSE_THYMELEAF + "search_no_result";
		String resultUrl = "";

		displayKeywords(keywords); //显示分词结果， 此为调试程序，可以屏蔽
		if (!keywodsIsValid(keywords))
			return url;

		if (keywords.trim() == "")
			resultUrl = url;
		
		if (resultUrl.equals("")) {
			resultUrl = keywordsSearch_SPU_Keywords(model, keywords, "", "", 1, PAGE_SIZE); //按SPU的关键字查询
		}
	
		if (resultUrl.equals("")) {
			resultUrl = keywordsSearch_brand(model, keywords, "", "", 1, PAGE_SIZE); //按品牌查询
		}
		if (resultUrl.equals("")) {
			resultUrl = keywordsSearch_category(keywords); //按类目查询
		}

		if (!resultUrl.equals("")) {
			url = resultUrl;
		}

		model.addAttribute("keywords", keywords);
		model.addAttribute("brandCond", "");
		model.addAttribute("categoryCond", "");
		model.addAttribute("searchCond", "");

		return url;

	}
	
	
	/**
	 * @Description 通过关键字查询（条件查询）
	 * @param keywords
	 *            搜索关键字
	 * @param brandCond
	 *            品牌查询条件
	 * @param categoryCond
	 *            类目查询条件
	 * @param pageNum
	 *            页号
	 * @param pageSize
	 *            页大小
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/keywordscond", method = RequestMethod.GET)
	public String keywordsSearch_cond(String keywords, String brandCond, String categoryCond, int pageNum, int pageSize,
			String searchCond, Model model) {
		String url = RESPONSE_THYMELEAF + "search_no_result";
		String resultUrl = "";

		displayKeywords(keywords); //显示分词结果， 此为调试程序，可以屏蔽

		if (!keywodsIsValid(keywords))
			return url;

		if (keywords.trim() == "")
			resultUrl = url;
		
		if (resultUrl.equals("")) {
			resultUrl = keywordsSearch_SPU_Keywords(model, keywords, brandCond, categoryCond, pageNum, pageSize); //按SPU的关键字查询
		}
		
		if (resultUrl.equals("")) {
			resultUrl = keywordsSearch_brand(model, keywords, brandCond, categoryCond, pageNum, pageSize); //按品牌查询
		}
		
		if (resultUrl.equals("")) {
			resultUrl = keywordsSearch_category(keywords); //按类目查询
		}

		if (!resultUrl.equals("")) {
			url = resultUrl;
		}

		model.addAttribute("keywords", keywords);
		model.addAttribute("brandCond", brandCond);
		model.addAttribute("categoryCond", categoryCond);
		model.addAttribute("searchCond", searchCond);

		return url;

	}

	/**
	 * @Description 通过三级类目进行查询
	 * @param categoryId
	 *            类目ID
	 * @param model
	 *            spring model
	 * @return
	 */
	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
	public String categorySearch(@PathVariable("categoryId") Long categoryId, Model model) {
		String url = "";
		//在此判定是三级类目还是二级类目,如果是三级类目,则按原来的逻辑进行处理,否则按二级类目来处理
		Category cate = categoryService.getCategoryByCid(categoryId);
		switch (cate.getLev()) {
		case 3:
			url = category_search_level3(categoryId, model);
			break;
		case 2:
			url = category_search_level2(categoryId, "", "","", 1, PAGE_SIZE,model);
			break;
		}

		return url;

	}
	
	
	

	/**
	 * @Description 用户选择筛选条件时 处理
	 * @param searchBean
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/categorycond", method = RequestMethod.POST)
	// @ResponseBody @RequestBody
	public String categoryCond(SearchCondBean searchBean, Model model) {

		/*
		 * (1)筛选条件（动态） 筛选条件分为两种: 类目的品牌条件 类目的关键属性 (2)当前条件下的SPU列表 三个数据列表： 品牌列表
		 * 类目其它属性列表（包括属性名称及属性值列表） SPU列表
		 */

		Long cid = Long.parseLong(searchBean.getCid());

		// ------- 筛选条件数据---------
		// （1）类目-品牌列表 筛选条件
		if (searchBean.getBrands().equals("")) {
			getCategoryBrands(model, cid);
		} else {
			model.addAttribute("brandList", null); // 用户已经选择品牌，查询结果中不再显示此筛选条件（筛选条件-品牌）
		}

		// (2)属性-属性值列表 筛选条件
		getCategoryAttrValue_cond(model, searchBean.getAttrs(), cid);

		// (3) 查询类目下的SPU并分页
		getSPU_cond(model, cid, searchBean.getBrands(), searchBean.getAttrs(), searchBean.getPageNum(),
				searchBean.getPageSize());

		//get category name by cid;
		Category cate = categoryService.getCategoryByCid(cid);

		// 向页面传递的数据
		model.addAttribute("cid", cid);
		model.addAttribute("cName", cate.getcName());
		model.addAttribute("brandCond", searchBean.getBrands());
		model.addAttribute("attrCond", searchBean.getAttrs());
		model.addAttribute("searchCond", searchBean.getSearchCond());

		return RESPONSE_THYMELEAF + "search_category_result";
	}
	
	/**
	 * @Description 二级类目查询
	 * @param categoryId
	 * @param brandCond
	 * @param categoryCond
	 * @param pageNum
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/category2cond")
	public String category2Cond(long categoryId, String brandCond, String categoryCond, int pageNum,String searchCond,
			int pageSize, Model model) {
		String url=category_search_level2(categoryId, brandCond, categoryCond,searchCond, pageNum, pageSize,model);
		return url;
	}

	/**
	 * @Description 主机频道查询
	 * @param cid 三级类目ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/channel/{categoryId}")
	public String home_channel_search(@PathVariable("categoryId") Long categoryId, Model model) {
		getSPU(model, categoryId, PAGE_SIZE);
		return RESPONSE_THYMELEAF + "channel";
	}
	
	/**
	 * @Description 主机频道查询
	 * @param cid 三级类目ID
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/channel2/{categoryId}")
	public String home_channel2_search(@PathVariable("categoryId") Long categoryId, Model model) {
		getSPU(model, categoryId, PAGE_SIZE);
		return RESPONSE_THYMELEAF + "channel2";
	}


	/**
	 * @Description 三级类目查询
	 * @param categoryId
	 *            类目ID
	 * @return 查询结果页URL
	 */
	private String category_search_level3(long categoryId, Model model) {
		/*
		 * (1)筛选条件（动态） 筛选条件分为两种: 类目的品牌条件 类目的其它属性 
		 * (2)当前条件下的SPU列表 三个数据列表： 
		 * 			品牌列表
		 * 			类目关键属性列表（包括属性名称及属性值列表） 
		 * 			SPU列表
		 */
	
		/*---------- 筛选条件--------------- */
		// （1）类目-品牌列表
		getCategoryBrands(model, categoryId);
		// (2)类目-属性-属性值列表
		getCategoryAttrValue(model, categoryId);
		// (3)查询类目下的SPU
		getSPU(model, categoryId,PAGE_SIZE);
	
		//get category name by cid;
		Category cate = categoryService.getCategoryByCid(categoryId);
	
		model.addAttribute("cid", categoryId);
		model.addAttribute("cName", cate.getcName());
		model.addAttribute("brandCond", "");
		model.addAttribute("attrCond", "");
		model.addAttribute("searchCond", "");
	
		return RESPONSE_THYMELEAF + "search_category_result";
	}

	/**
	 * @Description 关键字查询：按类目进行查询
	 * @param keywords
	 * @return
	 */
	private String keywordsSearch_category(String keywords) {
		String url = "";
		List<Category> cateList = categoryService.getCategoryByKeywords(keywords);
		if (cateList.size() > 0) {
			String cid = cateList.get(0).getCid().toString();
			url = "redirect:/front/search/category/" + cid;
		}
		return url;
	}

	/**
	 * @Description 关键字查询：按品牌查询
	 * @param model
	 * @param keywords
	 * @return
	 */
	private String keywordsSearch_brand(Model model, String keywords, String brandCond, String categoryCond,
			int pageNum, int pageSize) {
		String url = "";
		List<Map<String, Object>> item_pict_list = new ArrayList<Map<String, Object>>(); // 向前台传递的SPU数据
		// 按品牌查询
		List<Map<String, Object>> filterList = new ArrayList<Map<String, Object>>();
		List<CategoryBrandBean> brandList = new ArrayList<CategoryBrandBean>(); // 品牌列表
		// 如果查询不到相应的类目，查询类目中的品牌
		List<CategoryBrandBean> cateBrandList = null;

		//处理用户选择的品牌条件		
		Map<Long, Long> brandCondMap = processBrandCond(brandCond);

		// 根据关键词（品牌）查询类目品牌(叶子结点)
		cateBrandList = cateBrandService.getCategoryByBrand(keywords); // 品牌列表
		if (cateBrandList.size() > 0) {
			// 过滤重复品牌（获取品牌列表）
			// TODO 可通过数据库来处理
			List<Long> brandIds = new ArrayList<Long>(); // 品牌ID列表
			Map<Long, Long> brandMap = new HashMap<Long, Long>();
			for (CategoryBrandBean cateBrand : cateBrandList) {
				if (brandMap.get(cateBrand.getBrandId()) == null) {

					brandMap.put(cateBrand.getBrandId(), cateBrand.getBrandId()); //排重过滤器

					if (brandCondMap.size() == 0) {//如果用户没有选择品牌条件
						brandList.add(cateBrand);
						brandIds.add(cateBrand.getBrandId());
					} else { //如果用选择选择了品牌条件
						if (brandCondMap.get(cateBrand.getBrandId()) != null) { //如果是用户选择的品牌
							brandIds.add(cateBrand.getBrandId());
						}
					}

				}
			}

			//处理用户选择的品牌条件		
			Map<Long, Long> categoryCondMap = processCategoryCond(categoryCond); //获取二级类目ID

			// 获取二级类目ID
			Map<Long, Long> secondLevMap = new HashMap<Long, Long>();
			for (CategoryBrandBean cateBrand : cateBrandList) {
				if (secondLevMap.get(cateBrand.getSecondLevCid()) == null) {
					if (categoryCondMap.size() == 0) { //如果用户没有选择类目条件
						secondLevMap.put(cateBrand.getSecondLevCid(), cateBrand.getSecondLevCid());
					}
				}
			}
			// 读取二级类目
			for (long secondLevCid : secondLevMap.keySet()) {
				Category secondCategory = categoryService.getCategoryByCid(secondLevCid);
				Map<String, Object> condMap = new HashMap<String, Object>();
				condMap.put("secondCategory", secondCategory);
				// 读取三级类目列表
				List<Category> thirdCategoryList = categoryService.getCategoryByParentCid(secondLevCid);
				condMap.put("thirdCategoryList", thirdCategoryList);

				filterList.add(condMap);
			}

			List<Long> thirdCategoryCids = processCategoryCond_Cids(categoryCond); //获取三级类目id列表

			//查询SPU并分页
			if (pageNum != 0)
				PageHelper.startPage(pageNum, pageSize); // PageHelper
			else
				PageHelper.startPage(1, PAGE_SIZE); // PageHelper

			List<Item> itemList = itemService.getItemByBrandAndCid(brandIds, thirdCategoryCids,ItemStatusType.IS_SALING); // 查询商品列表

			PageInfo<Item> pageInfo = new PageInfo<>(itemList);
			//setPageInfo(model, pageInfo); // 向前台传递分页信息
			model.addAttribute("pageInfo", pageInfo);

			// --------- 按SPU查询SPU的picture----------
			for (Item item : itemList) {
				List<ItemPicture> pictList = itemPictureService.getItemPictureByItemId(item.getItemId());

				Map<String, Object> itemPictMap = new HashMap<String, Object>();
				itemPictMap.put("item", item);
				itemPictMap.put("pict", pictList);
				item_pict_list.add(itemPictMap);
			}

			model.addAttribute("brandList", brandList);
			model.addAttribute("filterList", filterList);
			model.addAttribute("itemList", item_pict_list);

			url = RESPONSE_THYMELEAF + "search_keyword_result";
		}

		return url;
	}

	/**
	 * @Description 二级类目查询
	 * @param categoryId
	 *            类目ID
	 * @param brandCond
	 *            品牌条件
	 * @param categoryCond
	 *            类目条件
	 * @param pageNum
	 *            页号
	 * @param pageSize
	 *            页大小
	 * @param model
	 * @return
	 */
	private String category_search_level2(long categoryId, String brandCond, String categoryCond,String searchCond, int pageNum,
			int pageSize, Model model) {
		String url = RESPONSE_THYMELEAF + "search_no_result";
		//(1)查询此类目下的三级类目
		//(3)三级类目条件:显示此二级类目下的三级类目,以作为查询条件		
		//(4)查询结果:根据三级类目ID及品牌ID查询SPU并显示

		
		//getCategoryLevelSecondBrands(model,categoryId);  //品牌条件:查询三级类目的所有品牌,以作为查询条件
		
		List<Map<String, Object>> item_pict_list = new ArrayList<Map<String, Object>>(); // 向前台传递的SPU数据
		// 按品牌查询
		List<Map<String, Object>> filterList = new ArrayList<Map<String, Object>>(); //类目条件
		List<CategoryBrandBean> brandList = new ArrayList<CategoryBrandBean>(); // 品牌列表
		// 如果查询不到相应的类目，查询类目中的品牌
		List<CategoryBrandBean> cateBrandList = null;

		//处理用户选择的品牌条件		
		Map<Long, Long> brandCondMap = processBrandCond(brandCond);

		// 根据关键词（品牌）查询类目品牌(叶子结点)
		cateBrandList = cateBrandService.getBrandByLevelSecondCid(categoryId); // 品牌列表
		if (cateBrandList.size() > 0) {
			// 过滤重复品牌（获取品牌列表）
			// TODO 可通过数据库来处理
			List<Long> brandIds = new ArrayList<Long>(); // 品牌ID列表
			Map<Long, Long> brandMap = new HashMap<Long, Long>();
			for (CategoryBrandBean cateBrand : cateBrandList) {
				if (brandMap.get(cateBrand.getBrandId()) == null) {

					brandMap.put(cateBrand.getBrandId(), cateBrand.getBrandId()); //排重过滤器

					if (brandCondMap.size() == 0) {//如果用户没有选择品牌条件
						brandList.add(cateBrand);
						brandIds.add(cateBrand.getBrandId());
					} else { //如果用选择选择了品牌条件
						if (brandCondMap.get(cateBrand.getBrandId()) != null) { //如果是用户选择的品牌
							brandIds.add(cateBrand.getBrandId());
						}
					}

				}
			}

			//处理用户选择的品牌条件		
			Map<Long, Long> categoryCondMap = processCategoryCond(categoryCond); 

			// 获取二级类目ID
			Map<Long, Long> secondLevMap = new HashMap<Long, Long>();
			for (CategoryBrandBean cateBrand : cateBrandList) {
				if (secondLevMap.get(cateBrand.getSecondLevCid()) == null) {
					if (categoryCondMap.size() == 0) { //如果用户没有选择类目条件
						secondLevMap.put(cateBrand.getSecondLevCid(), cateBrand.getSecondLevCid());
					}
				}
			}
			// 读取二级类目
			for (long secondLevCid : secondLevMap.keySet()) {
				Category secondCategory = categoryService.getCategoryByCid(secondLevCid);
				Map<String, Object> condMap = new HashMap<String, Object>();
				condMap.put("secondCategory", secondCategory);
				// 读取三级类目列表
				List<Category> thirdCategoryList = categoryService.getCategoryByParentCid(secondLevCid);
				condMap.put("thirdCategoryList", thirdCategoryList);

				filterList.add(condMap);
			}

			List<Long> thirdCategoryCids = processCategoryCond_Cids(categoryCond); //获取三级类目id列表
			
			if(thirdCategoryCids==null){  //如果没有选择条件
				thirdCategoryCids=getThirdCategoryIds(categoryId);
			}
			

			//查询SPU并分页
			if (pageNum != 0)
				PageHelper.startPage(pageNum, pageSize); // PageHelper
			else
				PageHelper.startPage(1, PAGE_SIZE); // PageHelper

			List<Item> itemList = itemService.getItemByBrandAndCid(brandIds, thirdCategoryCids,ItemStatusType.IS_SALING); // 查询商品列表

			PageInfo<Item> pageInfo = new PageInfo<>(itemList);
			//setPageInfo(model, pageInfo); // 向前台传递分页信息
			model.addAttribute("pageInfo", pageInfo);

			// --------- 按SPU查询SPU的picture----------
			for (Item item : itemList) {
				List<ItemPicture> pictList = itemPictureService.getItemPictureByItemId(item.getItemId());

				Map<String, Object> itemPictMap = new HashMap<String, Object>();
				itemPictMap.put("item", item);
				itemPictMap.put("pict", pictList);
				item_pict_list.add(itemPictMap);
			}

			model.addAttribute("brandList", brandList);
			model.addAttribute("filterList", filterList);
			model.addAttribute("itemList", item_pict_list);

			url = RESPONSE_THYMELEAF + "search_category2_result";
		}
		
		//get category name by cid;
		Category cate = categoryService.getCategoryByCid(categoryId);
		
		
		model.addAttribute("cName", cate.getcName());
		model.addAttribute("categoryId",categoryId);
		model.addAttribute("brandCond", brandCond);
		model.addAttribute("categoryCond", categoryCond);
		model.addAttribute("searchCond", searchCond);

		return url;

	}
	
	/**
	 * @Description 获取二级类目下的三级类目ID列表
	 * @param secondLevCid 二级类目ID
	 * @return
	 */
	private List<Long> getThirdCategoryIds(Long secondLevCid){
		List<Long> idList=new ArrayList<Long>();
		List<Category> thirdCategoryList = categoryService.getCategoryByParentCid(secondLevCid);
		for(Category cate:thirdCategoryList){
			idList.add(cate.getCid());
		}		
		if(idList.size()==0)
			return null;
		else
			return idList;
	}
	

	/**
	 * @Description 通过SPU关键字查询
	 * @param model
	 * @param keywords
	 * @param brandCond
	 * @param categoryCond
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	private String keywordsSearch_SPU_Keywords(Model model, String keywords, String brandCond, String categoryCond,
			int pageNum, int pageSize) {
		String url = "";
		List<Map<String, Object>> item_pict_list = new ArrayList<Map<String, Object>>(); // 向前台传递的数据

		// 按品牌查询

		List<Map<String, Object>> filterList = new ArrayList<Map<String, Object>>(); //查询条件：类目条件
		List<CategoryBrandBean> brandList = new ArrayList<CategoryBrandBean>(); // 查询条件：品牌列表 
		// 如果查询不到相应的类目，查询类目中的品牌
		List<CategoryBrandBean> cateBrandList = null;

		List<Long> brands = processBrandCond_ids(brandCond);
		List<Long> thirdCategoryCids = processCategoryCond_Cids(categoryCond);

		// 按SPU的关键字、商品名称查询 并分页
		if (pageNum != 0)
			PageHelper.startPage(pageNum, pageSize); // PageHelper
		else
			PageHelper.startPage(1, PAGE_SIZE); // PageHelper		

		List<Item> itemList = itemService.getItemByKeywordsAndBrandAndCid(keywords, brands, thirdCategoryCids,ItemStatusType.IS_SALING); //查询商品列表

		PageInfo<Item> pageInfo = new PageInfo<>(itemList);		
		model.addAttribute("pageInfo", pageInfo);  //setPageInfo(model, pageInfo); // 向前台传递分页信息

		List<Item> itemList1 = itemService.getItemByKeywordsAndBrandAndCid(keywords, brands, thirdCategoryCids,ItemStatusType.IS_SALING); //查询商品列表
		
		if (itemList1.size() > 0) {
			// 生成筛选条件，按品牌搜索的方式处理
			// 获取商品列表中的品牌id
			// TODO 可通过数据库来处理
			Map<Long, Long> tempMap = new HashMap<Long, Long>();  // 过滤重复品牌（获取品牌列表）
			Map<Long,Long> filterThirdLevCidMap=new HashMap<Long,Long>();  //商品的三级类目id
			List<Long> brandIdList = new ArrayList<Long>();
			for (Item item : itemList1) {
				if (tempMap.get(item.getBrand()) == null) {
					tempMap.put(item.getBrand(), item.getBrand());
					brandIdList.add(item.getBrand());
				}
				
				//商品三级类目id列表
				if(filterThirdLevCidMap.get(item.getCid())==null){
					filterThirdLevCidMap.put(item.getCid(), item.getCid());
				}
				
			}

			// 以下与品牌查询相同
			cateBrandList = cateBrandService.getCategoryByBrandIds(brandIdList); // 根据品牌读取三级类目-品牌
			
			
			//处理用户选择的品牌条件		
			Map<Long, Long> brandCondMap = processBrandCond(brandCond);
			Map<Long, Long> brandMap = new HashMap<Long, Long>();
			for (CategoryBrandBean cateBrand : cateBrandList) {
				if (brandMap.get(cateBrand.getBrandId()) == null) {
					brandMap.put(cateBrand.getBrandId(), cateBrand.getBrandId());
					if (brandCondMap.size() == 0) //如果用户没有选择品牌条件
						brandList.add(cateBrand);
				}
			}

			//处理用户选择的类目条件		
			Map<Long, Long> categoryCondMap = processCategoryCond(categoryCond); 

			// 获取二级类目ID
			Map<Long, Long> secondLevMap = new HashMap<Long, Long>();
			for (CategoryBrandBean cateBrand : cateBrandList) {
				if (secondLevMap.get(cateBrand.getSecondLevCid()) == null) {
					if (categoryCondMap.size() == 0 && filterThirdLevCidMap.get(cateBrand.getThirdLevCid())!=null)
						secondLevMap.put(cateBrand.getSecondLevCid(), cateBrand.getSecondLevCid());
				}
			}
			
			// 读取二级类目
			for (long secondLevCid : secondLevMap.keySet()) {
				Category secondCategory = categoryService.getCategoryByCid(secondLevCid);
				Map<String, Object> condMap = new HashMap<String, Object>();
				condMap.put("secondCategory", secondCategory);
				// 读取三级类目列表
				List<Category> thirdCategoryList1 = categoryService.getCategoryByParentCid(secondLevCid);
				List<Category> thirdCategoryList=new ArrayList<Category>();
				//过滤不符合条件的三级类目
				for(Category cate:thirdCategoryList1){
					if(filterThirdLevCidMap.get(cate.getCid())!=null){
						//thirdCategoryList.remove(cate);
						thirdCategoryList.add(cate);
					}
				}
				
				condMap.put("thirdCategoryList", thirdCategoryList);

				filterList.add(condMap);
			}

			// --------------- 按SPU查询SPU的picture------------
			for (Item item : itemList) {
				List<ItemPicture> pictList = itemPictureService.getItemPictureByItemId(item.getItemId());

				Map<String, Object> itemPictMap = new HashMap<String, Object>();
				itemPictMap.put("item", item);
				itemPictMap.put("pict", pictList);
				item_pict_list.add(itemPictMap);
			}

			model.addAttribute("brandList", brandList);
			model.addAttribute("filterList", filterList);
			model.addAttribute("itemList", item_pict_list);

			url = RESPONSE_THYMELEAF + "search_keyword_result";
		}

		return url;
	}

	/**
	 * @Description 处理品牌条件
	 * @param brandCond
	 * @return
	 */
	private Map<Long, Long> processBrandCond(String brandCond) {
		Map<Long, Long> map = new HashMap<>();
		String[] brandArr = brandCond.split(",");
		for (String brand : brandArr) {
			if (!brand.equals(""))
				map.put(Long.parseLong(brand), (long) 1);
		}
		return map;
	}

	/**
	 * @Description 处理用户选择的类目条件，获取二级类目map
	 * @param categoryCond
	 * @return
	 */
	private Map<Long, Long> processCategoryCond(String categoryCond) {
		Map<Long, Long> map = new HashMap<>();
		String[] categoryArr = categoryCond.split(",");
		for (String categoryPair : categoryArr) {
			if (!categoryPair.equals("")) {
				String[] tempArr = categoryPair.split("\\|");
				map.put(Long.parseLong(tempArr[0]), (long) 1);
			}
		}

		return map;
	}

	/**
	 * @Description 获取用户选择类目条件中的 三级类目列表
	 * @param categoryCond
	 * @return
	 */
	private List<Long> processCategoryCond_Cids(String categoryCond) {
		List<Long> list = new ArrayList<>();
		String[] categoryArr = categoryCond.split(",");
		for (String categoryPair : categoryArr) {
			if (!categoryPair.equals("")) {
				String[] tempArr = categoryPair.split("\\|"); //需要加入转义，"｜"不可以直接做为分隔符
				list.add(Long.parseLong(tempArr[1]));
			}
		}

		if (list.size() == 0)
			return null;
		else
			return list;
	}

	/**
	 * @Description 获取品牌id列表
	 * @param brandCond
	 * @return
	 */
	private List<Long> processBrandCond_ids(String brandCond) {
		List<Long> list = new ArrayList<>();
		String[] brandArr = brandCond.split(",");
		for (String brand : brandArr) {
			if (!brand.equals("")) {
				list.add(Long.parseLong(brand));
			}
		}

		if (list.size() == 0)
			return null;
		else
			return list;
	}

	/**
	 * @Description 调试用代码，显示分词后的关键字
	 * @param searchStr
	 *            查询字符串
	 */
	private void displayKeywords(String searchStr) {
		List<Word> words = WordSegmenter.seg(searchStr);  //未使用停用词
		//List<Word> words WordSegmenter.segWithStopWords(searchStr);  //使用信用词
		for (Word word : words) {
			System.out.println("---------------分词结果是：" + word.toString());
		}
		List<String> keywordList = new ArrayList<String>();
		for (Word word : words) {
			keywordList.add(word.toString());
		}
	}

	/**
	 * @Description 分词后的结果是否有效（去除停用词后的结果列表是否为空）
	 * @param searchStr
	 *            查询关键字
	 * @return true:去除停用词后的结果列表不为空 ; false:去除停用词后的结果列表为空
	 */
	private boolean keywodsIsValid(String searchStr) {
		List<Word> words = WordSegmenter.seg(searchStr);
		if (words.size() > 0)
			return true;
		else
			return false;
	}

	/**
	 * @Description 类目条件查询 SPU
	 * @param model
	 * @param cid
	 * @param brandCond
	 * @param attrCond
	 * @param pageNum
	 * @param pageSize
	 */
	private void getSPU_cond(Model model, long cid, String brandCond, String attrCond, int pageNum, int pageSize) {
		// ------------ 查询类目下的SPU -------------
		List<Map<String, Object>> item_pict_list = new ArrayList<Map<String, Object>>(); // 向前台传递的数据
		// (1)品牌ID列表 条件列表
		List<Long> brands = null;
		if (!brandCond.equals("")) {
			brands = new ArrayList<Long>();
			String[] brandArr = convertStrToArray(brandCond);
			for (String str : brandArr) {
				brands.add(Long.parseLong(str));
			}
		}
		// (2)属性-属性值 条件列表
		List<String> attrValPair = null;
		if (!attrCond.equals("")) {
			attrValPair = new ArrayList<String>();
			String[] attrArr = convertStrToArray(attrCond);
			for (String str : attrArr) {
				attrValPair.add(str);
			}
		}

		// ---------查询SPU并分页------------
		if (pageNum != 0)
			PageHelper.startPage(pageNum, pageSize); // PageHelper
		else
			PageHelper.startPage(1, PAGE_SIZE); // PageHelper

		List<Item> itemList = itemService.getItemByBrandAndAttr(cid, brands, attrValPair,ItemStatusType.IS_SALING); // 查询spu

		PageInfo<Item> pageInfo = new PageInfo<>(itemList);
		//setPageInfo(model, pageInfo); // 向前台传递分页信息
		model.addAttribute("pageInfo", pageInfo);

		// -------- 按SPU查询SPU的picture-------------
		for (Item item : itemList) {
			List<ItemPicture> pictList = itemPictureService.getItemPictureByItemId(item.getItemId());

			Map<String, Object> itemPictMap = new HashMap<String, Object>();
			itemPictMap.put("item", item);
			itemPictMap.put("pict", pictList);
			item_pict_list.add(itemPictMap);
		}

		model.addAttribute("itemList", item_pict_list);
	}

	/**
	 * @Description 类目条件查询 类目-属性-属性值
	 * @param model
	 * @param attrs
	 * @param cid
	 */
	private void getCategoryAttrValue_cond(Model model, String attrs, long cid) {
		HashMap<String, Integer> attrMap = (HashMap<String, Integer>) processSelectedAttrCond(attrs);
		List<Map<String, Object>> attrValueList = new ArrayList<Map<String, Object>>();

		// read类目属性列表
		List<CategoryAttrBean> cateAttrList = categoryAttrService.getCategoryAttrByCidAndType(cid,
				AttributeType.KEYED_ATTR); // read类目属性列表
		for (CategoryAttrBean attrBean : cateAttrList) {
			// 不再显示用户已经选择的筛选条件
			if (attrMap.get(attrBean.getAttr_id().toString()) == null) {
				// 属性名称
				HashMap<String, Object> attrValMap = new HashMap<String, Object>();
				attrValMap.put("attrName", attrBean.getAttr_name());
				// 属性值列表
				List<Map<String, String>> valueList = categoryAttrService.getCategoryAttrValList(cid,
						attrBean.getAttr_id());
				attrValMap.put("valueList", valueList);

				attrValueList.add(attrValMap); // 加入列表中
			}

		}

		model.addAttribute("attrValueList", attrValueList);
	}

	/**
	 * @Description 处理用户已经选择的属性条件
	 * @param attrConds
	 *            attr_id:val_id,attr_id:val_id
	 * @return attr_id:此属性条件个数
	 */
	private Map<String, Integer> processSelectedAttrCond(String attrConds) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		String[] attrValArr = attrConds.split(",");
		for (String attrVal : attrValArr) {
			String[] attrPair = attrVal.split(":");
			if (map.get(attrPair[0]) == null) {
				map.put(attrPair[0], 1);
			} else {
				map.put(attrPair[0], map.get(attrPair[0]) + 1);
			}
		}

		return map;

	}

	/**
	 * @Description 使用String的split 方法
	 * @param str
	 *            采用","分隔的字符串
	 * @return
	 */
	private String[] convertStrToArray(String str) {
		String[] strArray = null;
		strArray = str.split(","); // 拆分字符为"," ,然后把结果交给数组strArray
		return strArray;
	}

	/**
	 * @Description 三级类目-品牌
	 * @param model
	 * @param cid
	 */
	private void getCategoryBrands(Model model, long cid) {
		// （1）类目-品牌列表
		List<Map<String, String>> brandList = cateBrandService.getBrandByCid(cid);
		model.addAttribute("brandList", brandList);
	}


	/**
	 * @Description 类目-属性-属性值
	 * @param model
	 * @param cid
	 */
	private void getCategoryAttrValue(Model model, long cid) {
		List<Map<String, Object>> attrValueList = new ArrayList<Map<String, Object>>();
		// read类目属性列表
		List<CategoryAttrBean> cateAttrList = categoryAttrService.getCategoryAttrByCidAndType(cid,
				AttributeType.KEYED_ATTR); // read类目属性列表
		for (CategoryAttrBean attrBean : cateAttrList) {
			// 属性名称
			HashMap<String, Object> attrValMap = new HashMap<String, Object>();
			attrValMap.put("attrName", attrBean.getAttr_name());
			// 属性值列表
			List<Map<String, String>> valueList = categoryAttrService.getCategoryAttrValList(cid,
					attrBean.getAttr_id());
			if (valueList.size() > 0)
				System.out.println("test-----------" + valueList.get(0).get("attr_name"));
			attrValMap.put("valueList", valueList);

			attrValueList.add(attrValMap); // 加入列表中
		}

		model.addAttribute("attrValueList", attrValueList);
	}

	/**
	 * @Description 类目-SPU
	 * @param model
	 * @param cid
	 */
	private void getSPU(Model model, long cid,int pageSize) {
		// 查询类目下的SPU
		List<Map<String, Object>> item_pict_list = new ArrayList<Map<String, Object>>(); // 向前台传递的数据

		PageHelper.startPage(1, pageSize); // PageHelper
		List<Long> brands = new ArrayList<Long>(); // (1)品牌ID列表 条件列表
		List<String> params = new ArrayList<String>();// (2)属性-属性值 条件列表
		List<Item> itemList = itemService.getItemByBrandAndAttr(cid, null, null,ItemStatusType.IS_SALING); // 查询spu

		PageInfo<Item> pageInfo = new PageInfo<>(itemList);// (使用了拦截器或是AOP进行查询的再次处理)
		//setPageInfo(model, pageInfo); // 向前台传递分页信息
		model.addAttribute("pageInfo", pageInfo);

		// 按SPU查询SPU的picture
		for (Item item : itemList) {
			List<ItemPicture> pictList = itemPictureService.getItemPictureByItemId(item.getItemId());
			Map<String, Object> itemPictMap = new HashMap<String, Object>();
			itemPictMap.put("item", item);
			itemPictMap.put("pict", pictList);
			item_pict_list.add(itemPictMap);
		}

		// 向页面传递的数据
		model.addAttribute("itemList", item_pict_list);
	}

	private SearchCondBean getEntity(String resp) {
		JSONObject jsonObj = (JSONObject) JSON.parse(resp);
		SearchCondBean entity = JSONObject.toJavaObject(jsonObj, SearchCondBean.class);
		return entity;
	}

}
