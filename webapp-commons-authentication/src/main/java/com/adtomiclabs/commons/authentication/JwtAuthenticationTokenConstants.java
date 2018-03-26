package com.adtomiclabs.commons.authentication;

import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Class containing constants to be reused by the jwt token authentication system.
 */
public class JwtAuthenticationTokenConstants {

    /**
     * Claims name for roles in a jwt token.
     */
    public final static String ROLES_CLAIM_NAME = "roles";

    /**
     * Signature algorithm used to sign jwt tokens.
     * Change {@link #KEY_FACTORY_ALGORITHM} if this is changed.
     */
    public final static SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.RS512;

    /**
     * {@link String} used to indicate the algorithm to be used when passed to a {@link java.security.KeyFactory}.
     * Note that if {@link #SIGNATURE_ALGORITHM} is changed, this must be changed to.
     */
    public final static String KEY_FACTORY_ALGORITHM = "RSA";
}
