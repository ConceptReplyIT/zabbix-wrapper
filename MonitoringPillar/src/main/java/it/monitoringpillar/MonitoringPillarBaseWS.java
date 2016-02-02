package it.monitoringpillar;

import it.monitoringpillar.config.Configuration;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class MonitoringPillarBaseWS {

    @Inject
    private Configuration config;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getInfo() {
	return Response.ok(config.getPillarLabel()).build();
    }

}