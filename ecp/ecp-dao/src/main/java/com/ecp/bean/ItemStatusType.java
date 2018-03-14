package com.ecp.bean;

/**
 * @ClassName ItemStatusType
 * @Description 商品状态
 * @author Administrator
 * @Date 2017年7月29日 下午1:21:33
 * @version 1.0.0
 */
public class ItemStatusType {
	public static final int NO_PUBLISH=1;  //未发布
	public static final int WAITTING_AUDIT=2; //待审核
	public static final int AUDIT_NO=20;  //审核驳回
	public static final int WAITTING_PUT_ON_SHELVES=3;  //待上架 shelves
	public static final int IS_SALING=4; //在售
	public static final int IS_PUT_OFF_SHELVES=5; //己下架
	public static final int IS_LOCKING=6;  //锁定
	public static final int APPLY_FOR_UNLOCK=7;  //申请解锁
	
}
