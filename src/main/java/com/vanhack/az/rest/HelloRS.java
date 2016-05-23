package com.vanhack.az.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Simple healthcheck controller.
 * 
 * @author geiser
 *
 */
@Path("hello")
public class HelloRS {

	/**
	 * Just say "hello" within a 200 OK response.
	 * 
	 * @return Response with "hello" string
	 */
	@GET
	public Response sayHello() {

		return Response.ok("hello").build();
	}

}
