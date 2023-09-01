package com.example.springbootkeyclock.middleware;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
//Implement Converter inorder to make JWTAuthConverter as Converter Class
public class JWTAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {


    @Value("${PRINCIPLE_ATTRIBUTE}")
    private String principleAttribute;

    @Value("${CLIENT_ID}")
    private String client_ID;


    //This object is used to convert JWT
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();


    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        //Store Extracted data into authorities Collection
        Collection<GrantedAuthority> authorities = Stream.concat(
                //Extract JWT Token 1
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
                //Collect Extracted Date 2
        ).collect(Collectors.toSet());
        return new JwtAuthenticationToken(
                jwt,authorities,getPrincipleGetName(jwt)
        );
    }

    private String getPrincipleGetName(Jwt jwt) {
        String getName = JwtClaimNames.SUB;
        if (principleAttribute != null){
            getName = principleAttribute;
        }
        return jwt.getClaim(getName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String,Object> resourceAccess;
        Map<String,Object> resource;
        //Store the roles Extracted from JWT
        Collection<String> resourceRoles;


        if(jwt.getClaim("resource_access") == null){
            return Set.of();
        }else{
            resourceAccess = jwt.getClaim("resource_access");
            if(resourceAccess.get(client_ID) == null){
                return Set.of();
            }else {
                resource = (Map<String,Object>) resourceAccess.get(client_ID);

                resourceRoles = (Collection<String>) resource.get("roles");

                return resourceRoles
                        //Get the role from JWT token and Add Prefix and return role collection.
                        .stream().map(role -> new SimpleGrantedAuthority("ROLE_"+role)).collect(Collectors.toSet());
            }

        }


    }
}
