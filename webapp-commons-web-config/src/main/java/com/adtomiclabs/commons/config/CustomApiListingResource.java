package com.adtomiclabs.commons.config;

import io.swagger.jaxrs.listing.ApiListingResource;

import javax.ws.rs.Path;

@Path("/swagger/swagger.{type:json|yaml}")
public class CustomApiListingResource extends ApiListingResource {

}
