/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spring.web.mvc.immo.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import spring.web.mvc.immo.model.BaseModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author UCER
 */
public class HibernateDAO implements InterfaceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public int save(BaseModel baseModel) throws Exception {
        Session session = null;
        Transaction transaction = null;
        Object id;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            id = session.save(baseModel);
            transaction.commit();
            System.out.println("Insert successfully!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return (int)id;
    }

    @Override
    public List findAll(BaseModel baseModel) throws Exception {
        List<BaseModel> retour = null;
        Session session = sessionFactory.openSession();
        try {
            Transaction t = session.beginTransaction();
            Example ex = Example.create(baseModel).ignoreCase().excludeZeroes();
            t.commit();
            retour = session.createCriteria(baseModel.getClass()).add(ex).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return retour;
    }

    @Override
    public BaseModel findById(BaseModel baseModel) throws Exception {
        Session session = sessionFactory.openSession();
        Criteria crit = null;
        try {
            crit = session.createCriteria(baseModel.getClass());
            crit.add(Restrictions.eq("id", baseModel.getId()));
            baseModel = (BaseModel) crit.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseModel;
    }
    
    @Override
    public BaseModel findByRehetra(BaseModel baseModel) throws Exception {
        Session session = sessionFactory.openSession();
        Criteria crit = null;
        try {
            Example ex = Example.create(baseModel).ignoreCase().excludeZeroes();
            crit = session.createCriteria(baseModel.getClass()).add(ex);
//            crit.add(Restrictions.eq("id", baseModel.getId()));
            baseModel = (BaseModel) crit.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseModel;
    }

    @Override
    public List findByAnnee(BaseModel baseModel) throws Exception {
        Session session = sessionFactory.openSession();
        Criteria crit = null;
        List<BaseModel> liste = new ArrayList();
        try {

            Example ex = Example.create(baseModel).ignoreCase().excludeZeroes();
            crit = session.createCriteria(baseModel.getClass()).add(ex);
//            crit.add(Restrictions.eq("article", article));
//            baseModel = (BaseModel) crit.uniqueResult();
            liste = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public List pagination(BaseModel baseModel, int first, int max) throws Exception {
        Session session = sessionFactory.openSession();
        Criteria crit = null;
        List<BaseModel> results = null;
        try {
            Transaction t = session.beginTransaction();
            Example ex = Example.create(baseModel).ignoreCase().excludeZeroes();
            t.commit();

            crit = session.createCriteria(baseModel.getClass()).add(ex);
            crit.setFirstResult(first);
            crit.setMaxResults(max);
            results = crit.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }
}
