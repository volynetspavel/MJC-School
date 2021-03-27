package com.epam.esm.model;

/**
 * Entity is a base class of all other entities.
 */
public abstract class AbstractEntity {
    private int id;

    public AbstractEntity(){}

    public AbstractEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AbstractEntity{" +
                "id=" + id +
                '}';
    }
}
