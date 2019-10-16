package com.adtomiclabs.commons.config;

import io.swagger.jaxrs.listing.SwaggerSerializers;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

@Provider
@Produces({"application/json", "application/xml", "application/yaml"})
public class CustomSwaggerSerializer extends SwaggerSerializers {
}
