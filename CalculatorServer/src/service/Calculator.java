package service;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/api")
public class Calculator {
	@GET
	@Path("/add/{arg1}/{arg2}")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response add(
			@PathParam("arg1") double arg1,
			@PathParam("arg2") double arg2 ) 
	{
		double r =  arg1 + arg2;

	    String json = jsonize(r);
	    return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	@GET
	public Response a()
			
	{
	    return Response.ok().build();
	}
	@GET
	@Path("/mul/{arg1}/{arg2}")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response mul(
			@PathParam("arg1") double arg1,
			@PathParam("arg2") double arg2 ) 
	{
		double r =  arg1 * arg2;

	    String json = jsonize(r);
	    return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	@GET
	@Path("/sub/{arg1}/{arg2}")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response sub(
			@PathParam("arg1") double arg1,
			@PathParam("arg2") double arg2 ) 
	{
		double r =  arg1 - arg2;

	    String json = jsonize(r);
	    return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	@GET
	@Path("/div/{arg1}/{arg2}")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response div(
			@PathParam("arg1") double arg1,
			@PathParam("arg2") double arg2 ) 
	{
		double r =  arg1 / arg2;

	    String json = jsonize(r);
	    return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	@GET
	@Path("/sqrt/{arg1}")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response sqrt(
			@PathParam("arg1") double arg1) 
	{
		double r =  Math.sqrt(arg1);

	    String json = jsonize(r);
	    return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	@GET
	@Path("/neg/{arg1}")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response neg(
			@PathParam("arg1") double arg1) 
	{
		double r =  -arg1;

	    String json = jsonize(r);
	    return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	
	private String jsonize(double r) {
		String json ="{\"Result\" : { \"value\" : \"" + r + "\"}}";
		return json;
	}
	
}
