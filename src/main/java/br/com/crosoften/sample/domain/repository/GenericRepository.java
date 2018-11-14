package br.com.crosoften.sample.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository
public abstract class GenericRepository<T>  {

	@PersistenceContext
    EntityManager em;
	private Class<T> persistedClass;

	public T findById(Long id) {
		return this.em.find(getPersistedClass(), id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT T FROM ");
		sb.append(getPersistedClass().getSimpleName());
		sb.append(" T ");
		return this.em.createQuery(sb.toString(), getPersistedClass()).getResultList();
	}

	protected EntityManager getEm() {
		return this.em;
	}

	protected void setEm(EntityManager em) {
		this.em = em;
	}

	@Transactional(rollbackFor = Throwable.class)
	public void insert(T entity) {
		this.em.persist(entity);
	}

	@Transactional(rollbackFor = Throwable.class)
	public void insert(List<T> entities) {
		entities.forEach(entity -> this.em.persist(entity));
	}

	@Transactional(rollbackFor = Throwable.class)
	public void delete(Long id) {
		T entity =  this.em.find(getPersistedClass(), id);
		this.em.remove(entity);
	}
	@Transactional(rollbackFor = Throwable.class)
	public void delete(T t) {
		t = this.em.merge(t);
		this.em.remove(t);
	}

	@Transactional(rollbackFor = Throwable.class)
	public T update(T t) {
		return this.em.merge(t);
	}


	public Class<T> getPersistedClass() {
		if (persistedClass == null) {
			persistedClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return persistedClass;
	}

}