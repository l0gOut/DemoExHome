package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.ClientService;

import java.util.List;

public class ServiceClientService implements DAO<ClientService, Integer> {

    private SessionFactory factory;

    public ServiceClientService(SessionFactory factory){
        this.factory = factory;
    }

    @Override
    public void create(ClientService clientService) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(clientService);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(ClientService clientService) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(clientService);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(ClientService clientService) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(clientService);
            session.getTransaction().commit();
        }
    }

    @Override
    public ClientService read(Integer integer) {
        try(Session session = factory.openSession()){
            return session.get(ClientService.class, integer);
        }
    }

    @Override
    public List<ClientService> readAll() {
        try(Session session = factory.openSession()){
            return session.createQuery("FROM Client").list();
        }
    }
}
