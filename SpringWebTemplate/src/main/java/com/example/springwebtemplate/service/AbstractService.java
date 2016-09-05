package com.example.springwebtemplate.service;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public interface AbstractService<E> {
	int getRowCount(Criterion criterion);
	int getPageNumber(Criterion criterion);
	List<E> getPageResult(Criterion criterion, Order order, int pageNumber);
	List<E> search(String searchText, String[] fields, int pageNumber);
	void indexItems();
}
