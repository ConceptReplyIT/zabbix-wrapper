package it.reply.monitoringpillar;

import java.net.InetAddress;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import it.reply.monitoringpillar.config.Configuration;
import it.reply.monitoringpillar.domain.dsl.monitoring.Info;

@Path("/")
public class MonitoringPillarBaseWS {

	@Inject
	private Configuration config;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Info getHome() {
		return getInfo();
	}

	@GET
	@Path("/info")
	@Produces(MediaType.APPLICATION_JSON)
	public Info getInfo() {
		Info info = new Info();
		info.setProjectVersion(config.getVersionProperty(Configuration.PROJECT_BUILD_VERSION));
		info.setProjectRevision(config.getVersionProperty(Configuration.PROJECT_BUILD_REVISION));
		info.setProjectTimestamp(config.getVersionProperty(Configuration.PROJECT_BUILD_TIMESTAMP));

		String hostname;
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			hostname = "NOT AVAILABLE";
		}
		info.setServerHostname(hostname);

		String re = config.getMonitoringConfigurations().getEnvironment().getName();
		re = (re != null && re.length() > 0 ? re : "NOT AVAILABLE");
		info.setRuntimeEnvironment(re);

		return info;
	}

	@GET
	@Path("/misc/debug")
	@Produces(MediaType.APPLICATION_JSON)
	public Info getDebugInfo() {
		return getInfo();
	}

}