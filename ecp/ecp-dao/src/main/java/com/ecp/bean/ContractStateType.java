package com.ecp.bean;

/**
 * @ClassName ContractStateType
 * @Description 合同状态常量类
 * @author Administrator
 * @Date 2017年7月13日 下午5:33:18
 * @version 1.0.0
 */
public class ContractStateType {

	//各状态是互斥的
	public static final int CREATED_NO=1;   //未建合同
	public static final int CREATED_YES=2;  //己建合同
	public static final int SECOND_PARTY_CONFIRMED=3; //乙方确认
	public static final int FIRST_PARTY_CONFIRMED=4; //甲方确认
	public static final int EXECUTING=5; //执行中
	public static final int FINISHED=6;  //执行完毕
	
	
	
}
