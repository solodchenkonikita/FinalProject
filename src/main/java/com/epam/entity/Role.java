package com.epam.entity;

/**
 * User role type.
 *
 * @author Nikita Solodchenko
 */
public enum Role {
    CLIENT, MASTER, ADMINISTRATOR;

    public String getName() {
        return name().toLowerCase();
    }
}
