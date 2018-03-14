package com.ecp.service.impl.back;

import org.springframework.stereotype.Service;

import com.ecp.dao.CompanyInfoMapper;
import com.ecp.entity.CompanyInfo;
import com.ecp.service.back.ICompanyInfoService;
import com.ecp.service.impl.AbstractBaseService;

@Service
public class CompanyInfoServiceImpl extends AbstractBaseService<CompanyInfo, Long> implements ICompanyInfoService {

	private CompanyInfoMapper companyInfoMapper;

	/**
	 * @param brandMapper the brandMapper to set
	 * set方式注入
	 */
	public void setCompanyInfoMapper(CompanyInfoMapper companyInfoMapper) {
		this.companyInfoMapper = companyInfoMapper;
		this.setMapper(companyInfoMapper);
	}

}
