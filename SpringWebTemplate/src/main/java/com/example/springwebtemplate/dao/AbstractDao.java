package com.example.springwebtemplate.dao;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.List;

public interface AbstractDao<E, I extends Serializable> {

	void bulkInsert(List<E> bulkList);
    void saveOrUpdate(E e);
    void delete(E e);
    void bulkDelete(List<E> bulkList);
    List<E> findByCriteriaInAllItems(Criterion criterion, Order order);
    List<E> findByCriteria(Criterion criterion, Order order);
    List<E> findByCriteria(Criterion criterion, Order order,int firstResult, int maxResult);
    List<E> getAll();
    List<E> getAll(Order order, int maxResult);
    List<E> getAll(Order order);
    List<E> search(String searchText, String[] fields, int firstResult, int maxResult);
    int getRowCount(Criterion criterion);
    void indexItems();
}
