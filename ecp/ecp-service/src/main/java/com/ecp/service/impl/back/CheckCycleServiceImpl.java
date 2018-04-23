package com.ecp.service.impl.back;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONArray;
import com.ecp.common.util.CalendarUtil;
import com.ecp.dao.CheckCycleMapper;
import com.ecp.entity.CheckCycle;
import com.ecp.service.back.ICheckCycleService;
import com.ecp.service.impl.AbstractBaseService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("checkCycleServiceBean")
public class CheckCycleServiceImpl extends AbstractBaseService<CheckCycle, Long> implements ICheckCycleService {

	private CheckCycleMapper checkCycleMapper;

	/**
	 * @param checkCycleMapper
	 *            the checkCycleMapper to set set方式注入
	 */
	public void setCheckCycleMapper(CheckCycleMapper checkCycleMapper) {
		this.checkCycleMapper = checkCycleMapper;
		this.setMapper(checkCycleMapper);
	}

	/**
	 * 逻辑删除
	 * 
	 * @see com.ecp.service.back.ICustomerTypeService#logicDelById(java.lang.Long)
	 */
	@Override
	public int logicDelById(Long id) {
		try {
			CheckCycle cycle = new CheckCycle();
			cycle.setId(id);
			// cycle.setDeleted(2);//是否删除（1-未删除，2-删除，默认1）
			int rows = checkCycleMapper.updateByPrimaryKeySelective(cycle);
			return rows;
		} catch (Exception e) {
			e.printStackTrace();
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}

	/**
	 * 根据年度名称查询
	 * @see com.ecp.service.back.ICheckCycleService#getListByYearName(java.lang.String)
	 */
	@Override
	public List<CheckCycle> getListByYearName(String yearName) {
		Example example = new Example(CheckCycle.class);
		example.createCriteria().andEqualTo("yearName", yearName);
		List<CheckCycle> list = checkCycleMapper.selectByExample(example);
		return list;
	}
	
	/**
	 * 根据pid查询
	 * @see com.ecp.service.back.ICheckCycleService#getListByPid(java.lang.Long)
	 */
	@Override
	public List<CheckCycle> getListByPid(Long pid) {
		Example example = new Example(CheckCycle.class);
		example.createCriteria().andEqualTo("pid", pid);
		example.setOrderByClause("year_name");
		List<CheckCycle> list = checkCycleMapper.selectByExample(example);
		return list;
	}

	@Override
	@Transactional
	public int save(String yearName, String cycleArrJSON) {
		int rows = 0;
		CheckCycle cycle = this.getDB(yearName, "全年");
		if(cycle==null){
			cycle = new CheckCycle();
			cycle.setYearName(yearName);
			cycle.setCycleName("全年");
			cycle.setCalType((byte) 1);
			cycle.setStartDate(CalendarUtil.getFirstDayOfYear(Integer.parseInt(yearName)));
			cycle.setEndDate(CalendarUtil.getLastDayOfYear(Integer.parseInt(yearName)));
			cycle.setPid(0l);
			cycle.setSort(0);
			rows = checkCycleMapper.insertSelective(cycle);
		}
		if(rows>0){
			rows = this.save(cycle.getId(), yearName, cycleArrJSON);
			if(rows>0){
				return rows;
			}
		}
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return 0;
	}
	
	/**
	 * 判断数据库中是否包含此数据
	 * @param yearName
	 * @param cycleName
	 * @return
	 */
	private CheckCycle getDB(String yearName, String cycleName){
		Example example = new Example(CheckCycle.class);
		Criteria criteria = example.createCriteria();
		if(StringUtils.isNotBlank(yearName)){
			criteria.andEqualTo("yearName", yearName);
		}
		if(StringUtils.isNotBlank(cycleName)){
			criteria.andEqualTo("cycleName", cycleName);
		}
		List<CheckCycle> cycleList = checkCycleMapper.selectByExample(example);
		if(cycleList!=null && !cycleList.isEmpty()){
			return cycleList.get(0);
		}
		return null;
	}
	
	/**
	 * 保存考核周期
	 * @param pid
	 * @param yearName
	 * @param cycleArrJSON
	 * @return
	 */
	@Transactional
	private int save(Long pid, String yearName, String cycleArrJSON){
		int rows = 0;
		JSONArray array = JSONArray.parseArray(cycleArrJSON);
		for(int i=0; i<array.size(); i++){
			String tempStr = array.getObject(i, String.class);
			String[] tempArr = tempStr.split("-");
			String flag = tempArr[0];//标志：半年、季度、月
			String currNum = tempArr[1];//当前半年、季度、月是第几个
			String cycleName = tempArr[2];//周期名称
			String sort = tempArr[3];//排序
			
			CheckCycle cycle = this.getDB(yearName, cycleName);
			if(cycle!=null){//如果数据库中有考核年度为yearName，且周期名称为cycleName的数据，则直接跳过并继续
				continue;
			}
			
			Date startDate = null;
			Date endDate = null;
			if(flag.equalsIgnoreCase("halfAYear")){
				startDate = CalendarUtil.getFirstDayOfHalfAYear(Integer.parseInt(yearName), Integer.parseInt(currNum));
				endDate = CalendarUtil.getLastDayOfHalfAYear(Integer.parseInt(yearName), Integer.parseInt(currNum));
			}else if(flag.equalsIgnoreCase("quarter")){
				startDate = CalendarUtil.getFirstDayOfQuarter(Integer.parseInt(yearName), Integer.parseInt(currNum));
				endDate = CalendarUtil.getLastDayOfQuarter(Integer.parseInt(yearName), Integer.parseInt(currNum));
			}else if(flag.equalsIgnoreCase("month")){
				startDate = CalendarUtil.getFirstDayOfMonth(Integer.parseInt(yearName), Integer.parseInt(currNum));
				endDate = CalendarUtil.getLastDayOfMonth(Integer.parseInt(yearName), Integer.parseInt(currNum));
			}else{
				continue;
			}
			
			cycle = new CheckCycle();
			cycle.setYearName(yearName);
			cycle.setCycleName(cycleName);
			cycle.setCalType((byte) 1);
			cycle.setStartDate(startDate);
			cycle.setEndDate(endDate);
			cycle.setPid(pid);
			cycle.setSort(Integer.parseInt(sort));
			rows = checkCycleMapper.insertSelective(cycle);
			if(rows<=0){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				break;
			}
		}
		return rows;
	}

}
