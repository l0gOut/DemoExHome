package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.Roles;

import java.util.List;

public class ServiceRoles implements DAO<Roles, Integer> {

    private SessionFactory factory;

    public ServiceRoles(SessionFactory factory){
        this.factory = factory;
    }


    @Override
    public void create(Roles roles) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(roles);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Roles roles) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(roles);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Roles roles) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(roles);
            session.getTransaction().commit();
        }
    }

    @Override
    public Roles read(Integer integer) {
        try(Session session = factory.openSession()){
            return session.get(Roles.class, integer);
        }
    }

    @Override
    public List<Roles> readAll() {
        try(Session session = factory.openSession()){
            return session.createQuery("FROM Roles").list();
        }
    }
}
