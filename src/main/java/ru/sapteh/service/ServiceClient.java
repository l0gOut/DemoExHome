package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.Client;

import java.util.List;

public class ServiceClient implements DAO<Client, Integer> {

    private SessionFactory factory;

    public ServiceClient(SessionFactory factory){
        this.factory = factory;
    }

    @Override
    public void create(Client client) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(client);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Client client) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(client);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Client client) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(client);
            session.getTransaction().commit();
        }
    }

    @Override
    public Client read(Integer integer) {
        try(Session session = factory.openSession()){
            return session.get(Client.class, integer);
        }
    }

    @Override
    public List<Client> readAll() {
        try(Session session = factory.openSession()){
            return session.createQuery("FROM Client").list();
        }
    }
}
