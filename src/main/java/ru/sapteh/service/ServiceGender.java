package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.Gender;

import java.util.List;

public class ServiceGender implements DAO <Gender, Character> {
    private final SessionFactory factory;

    public ServiceGender (SessionFactory factory) {
        this.factory = factory;
    }



    public Gender read(Character code) {
        try(Session session = factory.openSession()){
            return session.get(Gender.class, code);
        }
    }

    @Override
    public void create(Gender gender) {

    }

    @Override
    public void update(Gender gender) {

    }

    @Override
    public void delete(Gender gender) {

    }

    @Override
    public List<Gender> readAll() {
        try (Session session = factory.openSession()){
            return session.createQuery("FROM Gender ").list();
        }
    }
}
