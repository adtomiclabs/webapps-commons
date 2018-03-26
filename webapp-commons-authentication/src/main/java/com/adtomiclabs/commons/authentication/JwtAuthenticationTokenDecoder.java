package com.adtomiclabs.commons.authentication;

import com.adtomiclabs.commons.roles.Role;
import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.security.PublicKey;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.adtomiclabs.commons.authentication.JwtAuthenticationTokenConstants.ROLES_CLAIM_NAME;

/**
 * Concrete implementation of {@link AuthenticationTokenDecoder}, using jwt tokens.
 */
public class JwtAuthenticationTokenDecoder implements AuthenticationTokenDecoder {

    /**
     * The {@link PublicKey} used to verify the jwt token signature.
     */
    private final PublicKey publicKey;

    /**
     * Constructor.
     *
     * @param publicKey The {@link PublicKey} used to verify the jwt token signature.
     */
    public JwtAuthenticationTokenDecoder(PublicKey publicKey) {
        this.publicKey = publicKey;
    }


    @Override
    public TokenData decode(String encodedToken) throws TokenException {
        if (!StringUtils.hasText(encodedToken)) {
            throw new IllegalArgumentException("The token must not be null or empty");
        }
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(publicKey)
                    .parse(encodedToken, CustomJwtHandlerAdapter.getInstance())
                    .getBody();

            // Previous step validated the following values
            final long tokenId = Long.valueOf(claims.getId());
            final String username = claims.getSubject();
            @SuppressWarnings("unchecked") final Set<Role> roles = (Set<Role>) claims.get(ROLES_CLAIM_NAME);

            return new TokenData(tokenId, username, roles);

        } catch (MalformedJwtException | SignatureException | ExpiredJwtException | UnsupportedJwtException
                | MissingClaimException e) {
            throw new TokenDecodingException("There was a problem with the jwt token", e);
        }
    }

    /**
     * Custom implementation of {@link JwtHandlerAdapter}.
     */
    private static class CustomJwtHandlerAdapter extends JwtHandlerAdapter<Jws<Claims>> {

        /**
         * Single instance of this class.
         */
        private static CustomJwtHandlerAdapter singleton = new CustomJwtHandlerAdapter();

        /**
         * @return The single instance of this class.
         */
        private static CustomJwtHandlerAdapter getInstance() {
            return singleton;
        }


        @Override
        public Jws<Claims> onClaimsJws(Jws<Claims> jws) {
            final JwsHeader<?> header = jws.getHeader();
            final Claims claims = jws.getBody();

            // Check jti is not missing
            final String jtiString = claims.getId();
            if (!StringUtils.hasText(jtiString)) {
                throw new MissingClaimException(header, claims, "Missing \"jwt id\" claim");
            }
            // Check if the jtiString is a long
            try {
                //noinspection ResultOfMethodCallIgnored
                Long.valueOf(jtiString);
            } catch (NumberFormatException e) {
                throw new MalformedJwtException("The \"jwt id\" claim must be an integer or a long", e);
            }

            // Check roles is not missing
            final Object rolesObject = claims.get(ROLES_CLAIM_NAME);
            if (rolesObject == null) {
                throw new MissingClaimException(header, claims, "Missing \"roles\" claim");
            }
            // Check roles is a Collection
            if (!(rolesObject instanceof Collection)) {
                throw new MalformedJwtException("The \"roles\" claim must be a collection");
            }
            // Transform the collection into a Set of Role
            @SuppressWarnings("unchecked") final Set<Role> roles = ((Collection<String>) rolesObject).stream()
                    .map(Role::fromString)
                    .collect(Collectors.toSet());
            claims.put(ROLES_CLAIM_NAME, roles);

            // Check issued at date is present and it is not a future date
            final Date issuedAt = Optional.ofNullable(claims.getIssuedAt())
                    .orElseThrow(() ->
                            new MissingClaimException(header, claims, "Missing \"issued at\" date"));
            if (issuedAt.after(new Date())) {
                throw new MalformedJwtException("The \"issued at\" date is a future date");
            }
            // Check expiration date is not missing
            if (claims.getExpiration() == null) {
                throw new MissingClaimException(header, claims, "Missing \"expiration\" date");
            }

            return jws;
        }
    }

}
