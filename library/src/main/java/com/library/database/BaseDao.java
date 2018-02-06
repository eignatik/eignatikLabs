package com.library.database;

import com.library.domain.BaseObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sql2o.Connection;
import org.sql2o.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class BaseDao<T extends BaseObject> implements Dao<T> {

    private static final Logger logger = LogManager.getLogger(BaseDao.class);

    @Override
    public T saveOrUpdate(T object) {
        if (alreadyExists(object)) {
            update(object);
        } else {
            save(object);
        }
        return object;
    }

    private void update(T object) {
        try (Connection connection = DbConnection.getConnection()) {
            Query query = connection.createQuery(getUpdateQuery());
            object.getParameterMapping().forEach((name, value) -> {
                if (value != null) {
                    query.addParameter(name, value);
                }
            });
            query.addParameter("id", object.getId());
            query.executeUpdate();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void save(T object) {
        try (Connection connection = DbConnection.getConnection()) {
            Query query = connection.createQuery(getInsertQuery(), true);
            object.getParameterMapping().forEach(query::addParameter);
            Integer id = query.executeUpdate().getKey(Integer.class);
            object.setId(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<T> saveOrUpdateAll(List<T> objects) {
        objects.forEach(this::saveOrUpdate);
        return objects;
    }

    @Override
    public T getById(int id) {
        try (Connection connection = DbConnection.getConnection()) {
            Query query = connection.createQuery(getSelectByIdQuery());
            getMapping().forEach(query::addColumnMapping);
            query.addParameter("id", id);
            return (T) query.executeAndFetch(getThisClass()).iterator().next();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection()) {
            Query query = connection.createQuery(getSelectAllQuery());
            getMapping().forEach(query::addColumnMapping);
            list.addAll(query.executeAndFetch(getThisClass()));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return list;
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = DbConnection.getConnection()) {
            connection.createQuery(getDeleteByIdQuery())
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = DbConnection.getConnection()) {
            connection.createQuery(getDeleteAllQuery()).executeUpdate();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    protected boolean alreadyExists(BaseObject object) {
        return object.getId() != null && getById(object.getId()) != null;
    }

    public T getByName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException(name + " is empty or null");
        }
        try (Connection connection = DbConnection.getConnection()) {
            Query query = connection.createQuery(getSelectByNameQuery());
            getMapping().forEach(query::addColumnMapping);
            query.addParameter("name", name);
            return (T) query.executeAndFetch(getThisClass()).iterator().next();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract String getSelectAllQuery();

    protected abstract String getSelectByIdQuery();

    protected abstract String getSelectByNameQuery();

    protected abstract String getDeleteByIdQuery();

    protected abstract String getDeleteAllQuery();

    protected abstract Class getThisClass();

    protected abstract Map<String, String> getMapping();

}
