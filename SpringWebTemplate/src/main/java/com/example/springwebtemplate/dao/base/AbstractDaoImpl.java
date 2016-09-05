package com.example.springwebtemplate.dao.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.errors.EmptyQueryException;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.springwebtemplate.dao.AbstractDao;
import com.example.springwebtemplate.dbo.base.MappedDomainObjectBase;

public abstract class AbstractDaoImpl<E, I extends Serializable> implements AbstractDao<E, I> {

	private Class<E> entityClass;

	protected AbstractDaoImpl(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void saveOrUpdate(E e) {
		getCurrentSession().saveOrUpdate(e);
	}

	@Override
	public void bulkInsert(List<E> bulkList) {
		if (bulkList != null && bulkList.size() > 0) {
			Session session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			int index = 0;

			try {
				for (E entity : bulkList) {
					session.saveOrUpdate(entity);
					if (index % 20 == 0) {
						// 20, same as the JDBC batch size
						// flush a batch of inserts and release memory:
						session.flush();
						session.clear();
					}
					index++;
				}

				tx.commit();
			} catch (Exception e) {
				e.printStackTrace();
				tx.rollback();
			} finally {
				session.close();
			}
		}
	}

	@Override
	public void delete(E e) {
		MappedDomainObjectBase mappedDomainObjectBase = (MappedDomainObjectBase) e;
		mappedDomainObjectBase.setDeleted(true);
		getCurrentSession().saveOrUpdate(mappedDomainObjectBase);
	}

	public void bulkDelete(List<E> bulkList) {
		if (bulkList != null && bulkList.size() > 0) {
			Session session = this.sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			int index = 0;

			try {
				for (E entity : bulkList) {
					MappedDomainObjectBase mappedDomainObjectBase = (MappedDomainObjectBase) entity;
					mappedDomainObjectBase.setDeleted(true);
					session.saveOrUpdate(mappedDomainObjectBase);
					if (index % 20 == 0) {
						// 20, same as the JDBC batch size
						// flush a batch of inserts and release memory:
						session.flush();
						session.clear();
					}
					index++;
				}

				tx.commit();
			} catch (Exception e) {
				e.printStackTrace();
				tx.rollback();
			} finally {
				session.close();
			}
		}
	}

	/*
	 * This method is used when making a search in all items in database. Thıs
	 * method returns the all deleted and non deleted rows.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<E> findByCriteriaInAllItems(Criterion criterion, Order order) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(null);
		criteria.add(criterion);
		if (order != null) {
			criteria.addOrder(order);
		} else {
			criteria.addOrder(Order.desc("storeDate"));
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findByCriteria(Criterion criterion, Order order) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(null);
		Criterion deletedFalse = Restrictions.eq("deleted", false);
		Criterion andCriterion = Restrictions.and(criterion, deletedFalse);
		criteria.add(andCriterion);
		if (order != null) {
			criteria.addOrder(order);
		} else {
			criteria.addOrder(Order.desc("storeDate"));
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findByCriteria(Criterion criterion, Order order, int firstResult, int maxResult) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(null);
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResult);
		Criterion deletedFalse = Restrictions.eq("deleted", false);
		if (criterion != null) {
			Criterion andCriterion = Restrictions.and(criterion, deletedFalse);
			criteria.add(andCriterion);
		} else {
			criteria.add(deletedFalse);
		}
		if (order != null) {
			criteria.addOrder(order);
		} else {
			criteria.addOrder(Order.desc("storeDate"));
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> getAll() {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(null);
		Criterion deletedFalse = Restrictions.eq("deleted", false);
		criteria.add(deletedFalse);
		criteria.addOrder(Order.desc("storeDate"));
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> getAll(Order order) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(null);
		Criterion deletedFalse = Restrictions.eq("deleted", false);
		criteria.add(deletedFalse);
		if (order != null) {
			criteria.addOrder(order);
		} else {
			criteria.addOrder(Order.desc("storeDate"));
		}
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> getAll(Order order, int maxResult) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(null);
		criteria.setMaxResults(maxResult);
		Criterion deletedFalse = Restrictions.eq("deleted", false);
		criteria.add(deletedFalse);
		if (order != null) {
			criteria.addOrder(order);
		} else {
			criteria.addOrder(Order.desc("storeDate"));
		}
		return criteria.list();
	}

	@Override
	public int getRowCount(Criterion criterion) {
		Criteria criteria = getCurrentSession().createCriteria(entityClass);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		Criterion deletedFalse = Restrictions.eq("deleted", false);
		if (criterion != null) {
			Criterion andCriterion = Restrictions.and(criterion, deletedFalse);
			criteria.add(andCriterion);
		} else {
			criteria.add(deletedFalse);
		}
		Long result = Long.valueOf(criteria.uniqueResult().toString());
		return result.intValue();
	}

	@SuppressWarnings("unchecked")
	public List<E> search(String searchText, String[] fields, int firstResult, int maxResult) {
		if(searchText == null || searchText.length() < 1){
			return new ArrayList<E>();
		}
		
		try {
			FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
			QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(entityClass).get();
			Query luceneQuery = qb
				    .bool()
				      .must( qb.keyword().onField("deleted").matching(false).createQuery() )
				      .must( qb.keyword().onFields(fields).matching(searchText).createQuery())
				    .createQuery();
			org.hibernate.Query fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, entityClass);
			fullTextQuery.setFirstResult(firstResult); //start from the firstResult element
			fullTextQuery.setMaxResults(maxResult); //return max elements
			return fullTextQuery.list();
		} catch(EmptyQueryException e){
			// do nothing!!!
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional
	public void indexItems() {
		try {
			FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());
			fullTextSession.createIndexer().startAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
