package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.sapteh.dao.DAO;
import ru.sapteh.model.Gender;

public class ServiceGender {
    private final SessionFactory factory;

    public ServiceGender (SessionFactory factory) {
        this.factory = factory;
    }



    public Gender read(char code) {
        try(Session session = factory.openSession()){
            return session.get(Gender.class, code);
        }
    }
}
