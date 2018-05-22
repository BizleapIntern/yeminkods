package com.bizleap.ds.service.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bizleap.commons.domain.Company;
import com.bizleap.commons.domain.Employee;
import com.bizleap.commons.domain.enums.EntityType;
import com.bizleap.commons.domain.exception.ServiceUnavailableException;
import com.bizleap.ds.service.CompanyService;
import com.bizleap.ds.service.EmployeeService;
import com.bizleap.ds.service.dao.CompanyDao;

@Service("companyService")
@Transactional(readOnly = true)
public class CompanyServiceImpl extends AbstractServiceImpl implements CompanyService {
	
	@Autowired
	CompanyDao companyDao;
	
	@Autowired
	EmployeeService employeeService;
	
	@Override
	public List<Company> findByCompanyBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "select company from Company company where company.boId=:dataInput";
		List<Company> companyList=companyDao.findByString(queryStr, boId);
		hibernateInitializeCompanyList(companyList);
		return companyList;
	}
	
	@Override
	public Company findByCompanyBoIdSingle(String boId) throws ServiceUnavailableException {
		List<Company> companyList = findByCompanyBoId(boId);
		if (!CollectionUtils.isEmpty(companyList)) {
			if (companyList.size() > 0) {
				return companyList.get(0);
			}
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=false)
	public void saveCompany(Company company) throws ServiceUnavailableException {
		if(company.isBoIdRequired()) {
			company.setBoId(getNextBoId());
		}
		companyDao.save(company);
	}

	@Override
	public List<Company> getAllCompany() throws ServiceUnavailableException {
		List<Company> companyList = companyDao.getAll("From Company company");
		hibernateInitializeCompanyList(companyList);
		return companyList;
	}
	
	@Override
	public long getCount() {
		return companyDao.getCount("select count(comp) from Company comp");
	}
	
	public String getNextBoId() {
		return getNextBoId(EntityType.COMPANY);
	}
	
	public void hibernateInitializeCompanyList(List<Company> companies) throws ServiceUnavailableException {
		for (Company company : companies)
			hibernateInitializeCompany(company);
	}

	public void hibernateInitializeCompany(Company company) throws ServiceUnavailableException {
		employeeService.hibernateInitializeEmployeeList(company.getEmployeeList());
	}
}
