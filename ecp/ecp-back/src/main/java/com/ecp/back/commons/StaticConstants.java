package com.ecp.back.commons;

public class StaticConstants {

	private StaticConstants() {
	    throw new IllegalStateException("Utility class");
	}
	
	public static final String LOGIN = "/login";//登录页面
	
	public static final String MAIN = "/main";//系统首页面
	
	public static final String CATEGORY_MANAGE_PAGE = "back/jsp/category/categoryManage";//商品分类管理页面
	public static final String CATEGORY_BRAND_MANAGE_PAGE = "back/jsp/category/categoryBrandManage";//类目品牌管理页面
	public static final String CATEGORY_BRAND_MANAGE_ITEMS_PAGE = "back/jsp/category/categoryBrandItems";//类目品牌管理品牌列表页面
	
	public static final String CATEGORY_ATTR_MANAGE_PAGE = "back/jsp/categoryattr/category_attr";//类目属性管理页面
	public static final String CATEGORY_ATTR_MANAGE_TABLE_PAGE = "back/jsp/categoryattr/category_attr_table";//类目属性列表页面
	public static final String CATEGORY_ATTR_VALUE_MANAGE_TABLE_PAGE = "back/jsp/categoryattr/category_attr_val_table";//类目属性值列表页面
	
	public static final String ADD_ITEM_PAGE = "back/jsp/item/addItem";//添加商品页面，不包括商品价格和sku价格
	public static final String ADD_ITEM_PRICE_PAGE = "back/jsp/item/addItemPrice";//添加商品页面，包括商品价格和sku价格
	public static final String ITEM_MANAGE_PAGE = "back/jsp/item/itemManage";//商品管理页面，不包括商品价格和sku价格
	public static final String ITEM_PRICE_MANAGE_PAGE = "back/jsp/item/itemPriceManage";//商品管理页面，包括商品价格和sku价格
	public static final String ITEM_MANAGE_TABLE_PAGE = "back/jsp/item/itemManageTable";//商品管理页面
	
	public static final String ITEM_DIALOG_PAGE = "back/jsp/common/item";//对话框中商品页面
	public static final String ITEM_DIALOG_TABLE_PAGE = "back/jsp/common/item_table";//对话框中商品列表页面
	
	public static final String ATTR_PAGE = "back/jsp/item/attrPage";//商品属性或销售属性页面，不包括sku价格
	public static final String ATTR_SKU_PRICE_PAGE = "back/jsp/item/attrPageSkuPrice";//商品属性或销售属性页面，包括sku价格
	
	public static final String BRAND_MANAGE_PAGE = "back/jsp/brand/brandManage";//品牌管理页面
	public static final String BRAND_MANAGE_TABLE_PAGE = "back/jsp/brand/brandManageTable";//品牌分类管理页面
	
	public static final String USER_MANAGE_PAGE = "back/jsp/user/user_manage";//用户管理页面
	public static final String USER_MANAGE_TABLE_PAGE = "back/jsp/user/user_table";//用户管理列表页面
	
	public static final String ROLE_MANAGE_PAGE = "back/jsp/role/role_manage";//角色管理页面
	public static final String ROLE_MANAGE_TABLE_PAGE = "back/jsp/role/role_table";//角色管理列表页面
	
	public static final String MENU_MANAGE_TABLE_PAGE = "back/jsp/menu/menu_manage";//菜单管理列表页面
	
	public static final String RECOMMEND_MANAGE_PAGE = "back/jsp/recommend/recommend";//推荐管理页面
	public static final String RECOMMEND_MANAGE_TABLE_PAGE = "back/jsp/recommend/recommend_table";//推荐管理列表页面
	
	public static final String SLIDESHOW_MANAGE_PAGE = "back/jsp/slideshow/slideshow";//轮播图管理页面
	public static final String SLIDESHOW_MANAGE_TABLE_PAGE = "back/jsp/slideshow/slideshow_table";//轮播图管理列表页面
	
	public static final String CUSTOMER_TYPE_MANAGE_PAGE = "back/jsp/customer_type/customer_type_manage";//客户类型管理页面
	public static final String CUSTOMER_TYPE_MANAGE_TABLE_PAGE = "back/jsp/customer_type/customer_type_table";//客户类型管理列表页面
	
	public static final String CUSTOMER_LEVEL_MANAGE_PAGE = "back/jsp/customer_level/customer_level_manage";//客户级别管理页面
	public static final String CUSTOMER_LEVEL_MANAGE_TABLE_PAGE = "back/jsp/customer_level/customer_level_table";//客户级别管理列表页面
	
	public static final String CUSTOMER_LEVEL_RULE_MANAGE_PAGE = "back/jsp/customer_level_rule/customer_level_rule_manage";//客户级别规则管理页面
	public static final String CUSTOMER_LEVEL_RULE_MANAGE_TABLE_PAGE = "back/jsp/customer_level_rule/customer_level_rule_table";//客户级别规则管理列表页面
	
	public static final String CHECK_CYCLE_MANAGE_PAGE = "back/jsp/check_cycle/check_cycle_manage";//考核周期管理页面
	public static final String CHECK_CYCLE_MANAGE_TABLE_PAGE = "back/jsp/check_cycle/check_cycle_table";//考核周期管理列表页面
	
	public static final String SALES_TARGET_MANAGE_PAGE = "back/jsp/sales_target/sales_target_manage";//销售指标管理页面
	public static final String SALES_TARGET_MANAGE_TABLE_PAGE = "back/jsp/sales_target/sales_target_table";//销售指标管理列表页面
	
}
