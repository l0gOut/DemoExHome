package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.ClientService;
import ru.sapteh.model.Service;

import java.util.List;

public class ServiceSS implements DAO<Service, Integer> {

    private SessionFactory factory;

    public ServiceSS(SessionFactory factory){
        this.factory = factory;
    }

    @Override
    public void create(Service service) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(service);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Service service) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(service);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Service service) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(service);
            session.getTransaction().commit();
        }
    }

    @Override
    public Service read(Integer integer) {
        try(Session session = factory.openSession()){
            return session.get(Service.class, integer);
        }
    }

    @Override
    public List<Service> readAll() {
        try(Session session = factory.openSession()){
            return session.createQuery("FROM Service").list();
        }
    }
}
