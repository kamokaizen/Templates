package com.example.springwebtemplate.service.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.transaction.annotation.Transactional;

import com.example.springwebtemplate.dao.AbstractDao;
import com.example.springwebtemplate.service.AbstractService;
import com.example.springwebtemplate.util.SpringPropertiesUtil;

@Transactional
public abstract class AbstractServiceImpl<E,I extends Serializable> implements AbstractService<E> {
  	
	@Override
	public int getRowCount(Criterion criterion)
	{
		int rowCount = 0;
		try{
			rowCount = this.getDao().getRowCount(criterion);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return rowCount;
	}
	
	@Override
	public int getPageNumber(Criterion criterion){
		int pageNumber = 0;
		try{
			int numberOfObjects = this.getRowCount(criterion);
			int pageSize = Integer.parseInt(SpringPropertiesUtil.getProperty("pageSize"));
			pageNumber = numberOfObjects / pageSize + (numberOfObjects % pageSize == 0 ? 0 : 1);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return pageNumber;
	}
	
	@Override
	public List<E> getPageResult(Criterion criterion, Order order, int pageNumber){
		List<E> pageResult = null;
		int pageSize = Integer.parseInt(SpringPropertiesUtil.getProperty("pageSize"));
		try{
			// Page number should start with 1 so we must extract -1 from page number.
			pageResult = this.getDao().findByCriteria(criterion, order, pageSize * (pageNumber-1), pageSize);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return pageResult;
	}
	
	public abstract AbstractDao<E, Long> getDao();
	
	public List<E> search(String searchText, String[] fields, int pageNumber){
		int pageSize = Integer.parseInt(SpringPropertiesUtil.getProperty("pageSize"));
		return this.getDao().search(searchText, fields, pageSize * (pageNumber-1), pageSize);
	}
	
	public void indexItems() {
		this.getDao().indexItems();
	}
}
