package com.bizleap.ds.loader.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizleap.commons.domain.Company;
import com.bizleap.commons.domain.Employee;
import com.bizleap.ds.loader.DataLoader;
import com.bizleap.ds.loader.DataManager;

@Service("dataLoader")
public class DataLoaderImpl implements DataLoader {
	
	@Autowired
	private DataManager dataManager;
	
	@Autowired
	private FileLoaderImpl fileLoaderImpl;
	
	public void loadEmployee(String fileName) {
		try {
			fileLoaderImpl.start(fileName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Employee employee;
		try {
			while(fileLoaderImpl.hasNext()) {
				try {
					employee=Employee.parseEmployee(fileLoaderImpl.getLine());
					if(employee!=null) {
						dataManager.getEmployeeList().add(employee);
					}
				}catch(Exception e){
					fileLoaderImpl.error(e);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileLoaderImpl.finish();
	}
	

	public void loadCompany(String fileName) {
		try {
			fileLoaderImpl.start(fileName);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Company company;
		try {
			while(fileLoaderImpl.hasNext()) {
				try {
					company=Company.parseCompany(fileLoaderImpl.getLine());
					if(company!=null) {
						dataManager.getCompanyList().add(company);
					}
				}catch(Exception e){
					fileLoaderImpl.error(e);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fileLoaderImpl.finish();
	}

	@Override
	public void loadData() {
		loadEmployee("employee.txt");
		loadCompany("company.txt");
	}
}