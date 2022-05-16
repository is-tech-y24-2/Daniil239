package ru.itmo.kotiki.dao;

import ru.itmo.kotiki.dao.entity.Owner;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.itmo.kotiki.util.HibernateSessionFactoryUtil;

import java.util.List;

public class OwnerDaoImpl implements OwnerDao{

    @Override
    public Owner findById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Owner owner = session.get(Owner.class, id);
        session.close();
        return owner;
    }

    @Override
    public void save(Owner owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public void update(Owner owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public void delete(Owner owner) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(owner);
        transaction.commit();
        session.close();
    }

    @Override
    public List<Owner> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Owner> cr = cb.createQuery(Owner.class);
        Root<Owner> root = cr.from(Owner.class);
        cr.select(root);
        Query<Owner> query = session.createQuery(cr);
        List<Owner> results = query.getResultList();
        session.close();
        return results;
    }
}
