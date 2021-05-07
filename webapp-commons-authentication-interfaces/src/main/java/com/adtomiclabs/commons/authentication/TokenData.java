package com.adtomiclabs.commons.authentication;

import com.adtomiclabs.commons.roles.Role;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * A wrapper class that encapsulates information taken from a token.
 */
public final class TokenData {

    /**
     * The token's id.
     */
    private final long id;

    /**
     * The token's owner username.
     */
    private final String username;

    /**
     * The token's owner roles.
     */
    private final List<Role> roles;

    /**
     * @param id       The token's id.
     * @param username The token's owner username.
     * @param roles    The token's owner roles.
     */
    public TokenData(long id, String username, Collection<Role> roles) {
        this.id = id;
        this.username = username;
        this.roles = new LinkedList<>(roles);
    }

    /**
     * @return The token's id.
     */
    public long getId() {
        return id;
    }

    /**
     * @return The token's owner username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The token's owner roles.
     */
    public List<Role> getRoles() {
        return roles;
    }
    
}
