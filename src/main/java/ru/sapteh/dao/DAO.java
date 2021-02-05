package ru.sapteh.dao;

import java.util.List;

public interface DAO<Entity, Integer> {
    void create (Entity entity);
    void update (Entity entity);
    void delete (Entity entity);
    Entity read (Integer integer);
    List<Entity> readAll ();
}
