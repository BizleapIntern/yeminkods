package com.bizleap.ds.loader.impl;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizleap.commons.domain.Company;
import com.bizleap.commons.domain.Employee;
import com.bizleap.commons.domain.utils.Printer;
import com.bizleap.ds.loader.AssociationMapper;
import com.bizleap.ds.loader.DataManager;

@Service
public class AssociationMapperImpl implements AssociationMapper {
	
	List<Employee> employeeList=new ArrayList<Employee>();
	List<Company> companyList=new ArrayList<Company>();
	
	@Autowired
	private DataManager dataManager;
	private static Logger logger = Logger.getLogger(AssociationMapperImpl.class);
	private static Printer printer = new Printer(logger);
	 
	private void addCompanyToEmployee(Employee employee) {
		
		if(employee==null)
			return;
		
		for(Company company:dataManager.getCompanyList()) {
			if(company.sameBoId(employee.getWorkForCompany())) {
				employee.setWorkForCompany(company);
				return;
			}
		}
		handleLinkageError("Company in employee cannot be linked!",employee,employee.getWorkForCompany());
	}
	
	private void addEmployeeToCompany(Company company) {
		//add employeeList to company first
		if(company==null) {
			return;
		}
		
		for(Employee employee:dataManager.getEmployeeList()) {
			if(employee.getWorkForCompany().sameBoId(company)) {
				company.getEmployeeList().add(employee);
				//return; 
			}
		}
		//handleLinkageError("Employee in company cannot be linked!", company);
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}
	
	private void processEmployeeAssociations() {
		for(Employee employee:dataManager.getEmployeeList()) {
			addCompanyToEmployee(employee);
		}
	}
	
	private void processCompanyAssociations() {
		for(Company company:dataManager.getCompanyList()) {
			addEmployeeToCompany(company);
		}
	}
	
	public void buildAssociations() {
		processEmployeeAssociations();
		processCompanyAssociations();
	}
	
	@Override
	public void handleLinkageError(String message, Object object) {
		handleLinkageError(message, object, null);
	}

	@Override
	public void handleLinkageError(String message, Object source, Object issue) {
		reportError(message, source, issue);
		logger.error("", new Exception("Object Linkage Exception"));
		//System.exit(0);
	}

	@Override
	public void reportError(String message, Object source) {
		reportError(message, source, null);
	}

	@Override
	public void reportError(String message, Object source, Object issue) {

		printer.error("Object Link Error", message);

		// if( object != null ) {
		// printer.line( "Source object BoId is: " + ((AbstractEntity)
		// object).getBoId());
		// }

		if (issue != null)
			printer.line2(">>>> Object with the issue is :::: " + issue);

		if (source != null)
			printer.line(">>>> Source object is :::: " + source);

		printer.line("----------------------------------");
	}
}
