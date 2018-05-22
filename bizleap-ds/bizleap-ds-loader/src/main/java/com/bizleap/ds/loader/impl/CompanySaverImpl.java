package com.bizleap.ds.loader.impl;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizleap.commons.domain.Company;
import com.bizleap.commons.domain.exception.ServiceUnavailableException;
import com.bizleap.commons.domain.utils.Printer;
import com.bizleap.ds.loader.CompanySaver;
import com.bizleap.ds.service.CompanyService;

@Service("companySaver")
public class CompanySaverImpl implements CompanySaver {
	private static Logger logger = Logger.getLogger(CompanySaverImpl.class);
	private static Printer printer = new Printer(logger);
	
	@Autowired
	CompanyService companyService;
	List<Company> companyList;
	
	@Override
	public void savePass1() throws ServiceUnavailableException, IOException {
		printer.line2(companyList.toString());
		for(Company company:getCompanyList()) {
			printer.line("About to save: "+company);
			companyService.saveCompany(company);
			printer.line("Company successfully saved.");
		}
	}
	
	@Override
	public void setCompanyList(List<Company> companyList) {
		this.companyList=companyList;
	}
	
	public List<Company> getCompanyList()
	{
		return this.companyList;
	}
}