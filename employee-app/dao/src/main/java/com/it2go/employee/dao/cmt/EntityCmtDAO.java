package com.it2go.employee.dao.cmt;

import com.it2go.employee.entities.DomainEntity;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityNotFoundException;
import com.it2go.framework.dao.EntityNotPersistedException;
import com.it2go.framework.dao.EntityRemovedException;
import com.it2go.framework.dao.IEntityDAO;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class EntityCmtDAO<T extends DomainEntity> implements IEntityDAO<T> {

    abstract Class<T> getEntityClass();

    @Inject
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public T save(T entity) throws EntityConcurrentModificationException, EntityRemovedException {
        Objects.requireNonNull(entity);

        final T res;
        if(entity.isNew()) {

            entity.setCreatedAt(new Date());
            //entity.setCreatedBy(userSession.getTestCreationUser());
        }
        else{
                Date dbLastUpdate = this.getLastUpdateTime(entity);
                T db_entity = this.getByIdentityKey(entity.getId());
                if(db_entity == null)
                    throw new EntityRemovedException();

                //if (dbLastUpdate != null && !dbLastUpdate.equals(entity.getLastUpdateTime()))
                if(db_entity.getUpdatedAt() != null && !db_entity.getUpdatedAt().equals(entity.getUpdatedAt()))
                    throw new EntityConcurrentModificationException();

           // entity.setUpdatedBy(userSession.getTestUpdateUser());
              entity.setUpdatedAt(new Date());
        }

        return entityManager.merge(entity);
    }

    public Date getLastUpdateTime(T entity){
        Objects.requireNonNull(entity);
        Objects.requireNonNull(entity.getId());

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(this.getEntityClass());
        Root root = criteriaQuery.from(this.getEntityClass());

        ParameterExpression<Long> p = criteriaBuilder.parameter(Long.class);
        criteriaQuery.select(root.get("lastUpdateTime")).where(criteriaBuilder.equal(p, root.get("id")));

        TypedQuery<Date> query = entityManager.createQuery(criteriaQuery);
        query.setParameter(p, entity.getId());

        Date res = query.getSingleResult();

        return res;
    }

    public T delete(T entity) throws EntityNotPersistedException {
        Objects.requireNonNull(entity);
        if(entity.isNew()) throw new EntityNotPersistedException();

        try {
            return this.deleteByIdentityKey(entity.getId());
        } catch (EntityNotFoundException e) {
            throw new EntityNotPersistedException();
        }
    }

    @Override
    public <K> T deleteByIdentityKey(K key) throws EntityNotFoundException {
        Objects.requireNonNull(key);
        final T merged  = entityManager.find(this.getEntityClass(),key);
        entityManager.lock(merged, LockModeType.PESSIMISTIC_WRITE);
        entityManager.remove(merged);

        return merged;
    }

    public <K> T getByIdentityKey(K key) {
        return entityManager.find(this.getEntityClass(), key);
    }

    public List<T> getAll() {
        final String simpleName = this.getEntityClass().getSimpleName();
        final String query = String.format("SELECT entity FROM %s entity", simpleName);
        final List<T> result;
        final TypedQuery<T> typedQuery;

        typedQuery = entityManager.createQuery(query, this.getEntityClass());

        return typedQuery.getResultList();
    }

    public List<T> getByQuery(String jpaQuery, Object... params) {

        TypedQuery<T> typedQuery;

        typedQuery = entityManager.createQuery(jpaQuery, this.getEntityClass());
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                typedQuery.setParameter(i+1, params[i]);
            }
        }

        return typedQuery.getResultList();
    }

    public List<T> getByQuery(String jpaQuery, Map<String, Object> paramMap) {

        TypedQuery<T> typedQuery = entityManager.createQuery(jpaQuery, this.getEntityClass());
        paramMap.forEach(typedQuery::setParameter);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public int executeUpdate(String query) {
        return entityManager.createQuery(query).executeUpdate();
    }

    @Override
    public List<T> testCriteriaAPI() {
        return null;
    }
}
