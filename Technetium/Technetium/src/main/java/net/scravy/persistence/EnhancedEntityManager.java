package net.scravy.persistence;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;

class EnhancedEntityManager implements EntityManager {

	private final javax.persistence.EntityManager em;

	public EnhancedEntityManager(javax.persistence.EntityManager em) {
		this.em = em;
	}

	@Override
	public void clear() {
		em.clear();
	}

	@Override
	public void close() {
		if (isOpen()) {
			em.close();
		}
	}

	@Override
	public boolean contains(Object arg0) {
		return em.contains(arg0);
	}

	@Override
	public Query createNamedQuery(String arg0) {
		return new EnhancedQuery(em.createNamedQuery(arg0));
	}

	@Override
	public <T> TypedQuery<T> createNamedQuery(String arg0, Class<T> arg1) {
		return new EnhancedTypedQuery<T>(em.createNamedQuery(arg0, arg1));
	}

	@Override
	public Query createNativeQuery(String arg0) {
		return new EnhancedQuery(em.createNativeQuery(arg0));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Query createNativeQuery(String arg0, Class arg1) {
		return new EnhancedQuery(em.createNativeQuery(arg0, arg1));
	}

	@Override
	public Query createNativeQuery(String arg0, String arg1) {
		return new EnhancedQuery(em.createNativeQuery(arg0, arg1));
	}

	@Override
	public Query createQuery(String arg0) {
		return new EnhancedQuery(em.createQuery(arg0));
	}

	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> arg0) {
		return new EnhancedTypedQuery<T>(em.createQuery(arg0));
	}

	@Override
	public <T> TypedQuery<T> createQuery(String arg0, Class<T> arg1) {
		return new EnhancedTypedQuery<T>(em.createQuery(arg0, arg1));
	}

	@Override
	public void detach(Object arg0) {
		em.detach(arg0);
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1) {
		return em.find(arg0, arg1);
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, Map<String, Object> arg2) {
		return em.find(arg0, arg1, arg2);
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2) {
		return em.find(arg0, arg1, arg2);
	}

	@Override
	public <T> T find(Class<T> arg0, Object arg1, LockModeType arg2,
			Map<String, Object> arg3) {
		return em.find(arg0, arg1, arg2, arg3);
	}

	@Override
	public void flush() {
		em.flush();
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return em.getCriteriaBuilder();
	}

	@Override
	public Object getDelegate() {
		return em.getDelegate();
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return em.getEntityManagerFactory();
	}

	@Override
	public FlushModeType getFlushMode() {
		return em.getFlushMode();
	}

	@Override
	public LockModeType getLockMode(Object arg0) {
		return em.getLockMode(arg0);
	}

	@Override
	public Metamodel getMetamodel() {
		return em.getMetamodel();
	}

	@Override
	public Map<String, Object> getProperties() {
		return em.getProperties();
	}

	@Override
	public <T> T getReference(Class<T> arg0, Object arg1) {
		return em.getReference(arg0, arg1);
	}

	@Override
	public EntityTransaction getTransaction() {
		return em.getTransaction();
	}

	@Override
	public boolean isOpen() {
		try {
			return em.isOpen();
		} catch (Exception exc) {
			return false;
		}
	}

	@Override
	public void joinTransaction() {
		em.joinTransaction();
	}

	@Override
	public void lock(Object object, LockModeType arg1) {
		em.lock(object, arg1);
	}

	@Override
	public void lock(Object arg0, LockModeType arg1, Map<String, Object> arg2) {
		em.lock(arg0, arg1, arg2);
	}

	@Override
	public <T> T merge(T object) {
		return em.merge(object);
	}

	@Override
	public void persist(Object object) {
		EntityTransaction transaction = getTransaction();
		boolean commitTransaction = false;
		if (!transaction.isActive()) {
			transaction.begin();
			commitTransaction = true;
		}
		em.persist(object);
		if (commitTransaction) {
			transaction.commit();
		}
	}

	@Override
	public void refresh(Object object) {
		em.refresh(object);
	}

	@Override
	public void refresh(Object object, Map<String, Object> options) {
		em.refresh(object, options);
	}

	@Override
	public void refresh(Object object, LockModeType lockModeType) {
		em.refresh(object, lockModeType);
	}

	@Override
	public void refresh(Object object, LockModeType lockModeType,
			Map<String, Object> options) {
		em.refresh(object, lockModeType, options);
	}

	@Override
	public void remove(Object arg0) {
		EntityTransaction transaction = getTransaction();
		boolean commitTransaction = false;
		if (!transaction.isActive()) {
			transaction.begin();
			commitTransaction = true;
		}
		em.remove(arg0);
		if (commitTransaction) {
			transaction.commit();
		}
	}

	@Override
	public void setFlushMode(FlushModeType arg0) {
		em.setFlushMode(arg0);
	}

	@Override
	public void setProperty(String arg0, Object arg1) {
		em.setProperty(arg0, arg1);
	}

	@Override
	public <T> T unwrap(Class<T> arg0) {
		return em.unwrap(arg0);
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		CriteriaBuilder b = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = b.createQuery(entityClass);
		return em.createQuery(cq).getResultList();
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass, int limit) {
		CriteriaBuilder b = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = b.createQuery(entityClass);
		javax.persistence.TypedQuery<T> q = em.createQuery(cq);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass, int limit, int offset) {
		CriteriaBuilder b = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = b.createQuery(entityClass);
		javax.persistence.TypedQuery<T> q = em.createQuery(cq);
		q.setFirstResult(limit);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public String escapeLike(String value) {
		return value.replace("\\", "\\\\").replace("%", "\\%")
				.replace("_", "\\_");
	}

	@Override
	public List<?> query(String query) {
		javax.persistence.Query q = em.createQuery(query);
		return q.getResultList();
	}

	@Override
	public <T> List<T> query(String query, Class<T> clazz) {
		javax.persistence.TypedQuery<T> q = em.createQuery(query, clazz);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> mapQuery(String query) {
		javax.persistence.Query q = em.createQuery(query);
		q.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
		return q.getResultList();
	}

	@Override
	public List<?> namedQuery(String namedQuery) {
		javax.persistence.Query q = em.createNamedQuery(namedQuery);
		return q.getResultList();
	}

	@Override
	public <T> List<T> namedQuery(String query, Class<T> clazz) {
		javax.persistence.TypedQuery<T> q = em.createNamedQuery(query, clazz);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> nativeQuery(String nativeQuery) {
		javax.persistence.Query q = em.createNativeQuery(nativeQuery);
		q.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
		return q.getResultList();
	}

	@Override
	public Object querySingle(String query) {
		try {
			javax.persistence.Query q = em.createQuery(query);
			return q.getSingleResult();
		} catch (NoResultException exc) {
			return null;
		}
	}

	@Override
	public <T> T querySingle(String query, Class<T> clazz) {
		try {
			javax.persistence.TypedQuery<T> q = em.createQuery(query, clazz);
			return q.getSingleResult();
		} catch (NoResultException exc) {
			return null;
		}
	}

	@Override
	public Object namedQuerySingle(String namedQuery) {
		try {
			javax.persistence.Query q = em.createNamedQuery(namedQuery);
			return q.getSingleResult();
		} catch (NoResultException exc) {
			return null;
		}
	}

	@Override
	public <T> T namedQuerySingle(String query, Class<T> clazz) {
		try {
			javax.persistence.TypedQuery<T> q = em.createNamedQuery(query,
					clazz);
			return q.getSingleResult();
		} catch (NoResultException exc) {
			return null;
		}
	}

	@Override
	public Object nativeQuerySingle(String nativeQuery) {
		try {
			javax.persistence.Query q = em.createNativeQuery(nativeQuery);
			return q.getSingleResult();
		} catch (NoResultException exc) {
			return null;
		}
	}

	@Override
	public List<?> query(String query, int limit) {
		javax.persistence.Query q = em.createQuery(query);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> mapQuery(String query, int limit) {
		javax.persistence.Query q = em.createQuery(query);
		q.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public <T> List<T> query(Class<T> clazz, String query, int limit) {
		javax.persistence.TypedQuery<T> q = em.createQuery(query, clazz);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public List<?> namedQuery(String namedQuery, int limit) {
		javax.persistence.Query q = em.createNamedQuery(namedQuery);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public <T> List<T> namedQuery(Class<T> clazz, String namedQuery, int limit) {
		javax.persistence.TypedQuery<T> q = em.createNamedQuery(
				namedQuery, clazz);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> nativeQuery(String nativeQuery, int limit) {
		javax.persistence.Query q = em.createNativeQuery(nativeQuery);
		q.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public List<?> query(String query, int offset, int limit) {
		javax.persistence.Query q = em.createQuery(query);
		q.setFirstResult(offset);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> mapQuery(String query, int offset,
			int limit) {
		javax.persistence.Query q = em.createQuery(query);
		q.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
		q.setFirstResult(offset);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public <T> List<T> query(Class<T> clazz, String query, int offset, int limit) {
		javax.persistence.TypedQuery<T> q = em.createQuery(query, clazz);
		q.setFirstResult(offset);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public List<?> namedQuery(String namedQuery, int offset, int limit) {
		javax.persistence.Query q = em.createNamedQuery(namedQuery);
		q.setFirstResult(offset);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@Override
	public <T> List<T> namedQuery(
			Class<T> clazz, String namedQuery, int offset, int limit) {
		javax.persistence.TypedQuery<T> q = em.createNamedQuery(namedQuery,
				clazz);
		q.setFirstResult(offset);
		q.setMaxResults(limit);
		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> nativeQuery(String nativeQuery,
			int offset, int limit) {
		javax.persistence.Query q = em.createNativeQuery(nativeQuery);
		q.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
		q.setFirstResult(offset);
		q.setMaxResults(limit);
		return q.getResultList();
	}
}
