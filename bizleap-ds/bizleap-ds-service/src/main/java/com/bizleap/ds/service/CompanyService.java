package com.bizleap.ds.service;

import java.io.IOException;
import java.util.List;

import com.bizleap.commons.domain.Company;
import com.bizleap.commons.domain.exception.ServiceUnavailableException;

public interface CompanyService {
	public List<Company> findByCompanyBoId(String boId)throws ServiceUnavailableException;
	public Company findByCompanyBoIdSingle(String boId)throws ServiceUnavailableException;
	public void saveCompany(Company company) throws IOException, ServiceUnavailableException;
	public List<Company> getAllCompany()throws ServiceUnavailableException;
}
