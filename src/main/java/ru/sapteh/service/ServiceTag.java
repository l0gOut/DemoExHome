package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.Tag;

import java.util.List;

public class ServiceTag implements DAO<Tag, Integer> {

    private final SessionFactory factory;

    public ServiceTag(SessionFactory factory){
        this.factory = factory;
    }

    @Override
    public void create(Tag tag) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(tag);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Tag tag) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(tag);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Tag tag) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(tag);
            session.getTransaction().commit();
        }
    }

    @Override
    public Tag read(Integer integer) {
        try(Session session = factory.openSession()) {
            return session.get(Tag.class, integer);
        }
    }

    @Override
    public List<Tag> readAll() {
        try(Session session = factory.openSession()){
            return session.createQuery("FROM Tag").list();
        }
    }
}
