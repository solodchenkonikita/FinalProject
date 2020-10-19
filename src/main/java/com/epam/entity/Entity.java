package com.epam.entity;

import java.io.Serializable;

/**
 * Basic common parent for all entities.
 *
 * @author Solodchenko Nikita
 *
 */
public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 2036919835830383616L;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
