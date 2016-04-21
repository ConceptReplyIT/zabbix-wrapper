package it.reply.monitoringpillar.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import it.reply.monitoringpillar.adapter.zabbix.exception.IllegalArgumentZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ServerType;
import it.reply.monitoringpillar.config.PathHelper.ResourceType;
import it.reply.monitoringpillar.config.dsl.Metric;
import it.reply.monitoringpillar.config.dsl.MonitoringConfigurations;
import it.reply.monitoringpillar.config.dsl.MonitoringMappings;
import it.reply.monitoringpillar.config.dsl.MonitoringZones;
import it.reply.monitoringpillar.config.dsl.PortsMacro;
import it.reply.monitoringpillar.config.dsl.Server;
import it.reply.monitoringpillar.config.dsl.Zone;
import it.reply.monitoringpillar.domain.dsl.monitoring.InfoType;
import it.reply.monitoringpillar.domain.dsl.monitoring.MonitoringConstant;
import it.reply.monitoringpillar.domain.exception.IllegalArgumentMonitoringException;
import it.reply.monitoringpillar.domain.exception.MonitoringException;

@Startup
@Singleton
public class Configuration {

	public final static String PROJECT_BUILD_VERSION = "build.version";
	public final static String PROJECT_BUILD_REVISION = "build.revision";
	public final static String PROJECT_BUILD_TIMESTAMP = "build.timestamp";

	private static final Logger LOG = LogManager.getLogger(Configuration.class);

	// General monitoring properties
	private MonitoringConfigurations monitoringConfigurations;

	// Properties read from {CURRENT_ENVIRONMENT}/services-endpoints.properties
	private Properties serviceProperties;
	private Properties versionProperties;

	private MonitoringZones monitoringZones;

	private MonitoringMappings monitoringMappings;

	public final String varResourceProfilesBasePath = PathHelper
			.getResourcesPath(ResourceType.CONFIG_ENV_VARIABLE_PROPERTIES_PROFILES);

	public MonitoringConfigurations getMonitoringConfigurations() {
		return monitoringConfigurations;
	}

	public MonitoringZones getMonitoringZones() {
		return monitoringZones;
	}

	public MonitoringMappings getMonitoringMappings() {
		return monitoringMappings;
	}

	@PostConstruct
	private void init() {
		try {
			LOG.info("Init configuration");

			ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

			InputStream stream = getResourceAsStream(ConfigProperties.MONITORING_PROPERTY_FILE);
			if (stream == null)
				throw new RuntimeException(
						"Property file " + ConfigProperties.MONITORING_PROPERTY_FILE + " not found.");
			try {
				monitoringConfigurations = mapper.readValue(stream, MonitoringConfigurations.class);

				LOG.info("MonitoringConfiguration Properties loaded successfully!");
				// TODO: log all props
			} catch (Exception e) {
				throw new RuntimeException("Failed to read property file " + ConfigProperties.MONITORING_PROPERTY_FILE,
						e);
			}

			stream = getResourceAsStream(ConfigProperties.ZONE_PROPERTY_FILE);
			if (stream == null)
				throw new RuntimeException("Property file " + ConfigProperties.ZONE_PROPERTY_FILE + " not found.");
			try {
				monitoringZones = mapper.readValue(stream, MonitoringZones.class);
				// TODO: log all props
				LOG.info("MonitoringZone Properties loaded successfully!");
			} catch (Exception e) {
				throw new RuntimeException("Failed to read property file " + ConfigProperties.ZONE_PROPERTY_FILE, e);
			}

			stream = getResourceAsStream(ConfigProperties.MAPPING_PROPERTY_FILE);
			if (stream == null)
				throw new RuntimeException("Property file " + ConfigProperties.MAPPING_PROPERTY_FILE + " not found.");
			try {
				monitoringMappings = mapper.readValue(stream, MonitoringMappings.class);
				// TODO: log all props
				LOG.info("MonitoringMapping Properties loaded successfully!");
			} catch (Exception e) {
				throw new RuntimeException("Failed to read property file " + ConfigProperties.MAPPING_PROPERTY_FILE, e);
			}

			loadVersionProperties();

		} catch (Exception ex) {
			throw new Error("CANNOT LOAD ENVIRONMENT CONFIGURATION !", ex);
		}
	}

	private void loadVersionProperties() throws IOException {
		try {
			Properties props = new Properties();

			InputStream in = getResourceAsStream("version.properties");
			props.load(in);
			versionProperties = new Properties(props);

			in.close();

			LOG.info("Version properties loaded successfully !!");

			if (LOG.isDebugEnabled())
				for (Entry<Object, Object> item : props.entrySet()) {
					LOG.debug("[Version] - K: " + item.getKey() + ", V: " + item.getValue());
				}
		} catch (IOException e) {
			LOG.error("ERROR: Cannot load 'version.properties'.");
			throw new Error("Cannot load 'version.properties'");
		}
	}

	private InputStream getResourceAsStream(String path) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
	}

	/**
	 * Verifies that the given zone exists in the property files.
	 * 
	 * @param zoneName
	 * @return true if the zone exists, false otherwise.
	 */
	public boolean isZoneExisting(String zoneName) {
		return monitoringZones.getZone(zoneName) != null ? true : false;
	}

	public List<String> getZoneNames() {
		List<String> zoneNames = new ArrayList<>();
		for (Zone zone : monitoringZones.getZones()) {
			zoneNames.add(zone.getName());
		}
		return zoneNames;
	}

	public boolean isServerExisting(String zoneName, String serverType) {
		return monitoringZones.getZone(zoneName).getServer(serverType) != null ? true : false;
	}

	public String[] getServerNames(String zoneName, String serverType) {
		List<String> serverNames = new ArrayList<>();
		for (Zone zone : monitoringZones.getZones()) {
			for (Server server : zone.getServers()) {
				serverNames.add(server.getType());
			}
		}
		return (String[]) serverNames.toArray();
	}

	public boolean isProxyArchitectureExisting(String zoneName) {
		return monitoringConfigurations.getOptions().isProxyArchitecture() != false ? true : false;
	}

	public boolean isIaaSCeilometerMetric(String zone, String serverType, String metricName) {
		boolean metricFound = false;
		if (monitoringConfigurations.getOptions().isIaasMonitoring()) {
			List<String> metrics = getCeilometerMetrics();
			for (String metric : metrics) {
				if (metric.equalsIgnoreCase(metricName))
					metricFound = true;
			}
			if (!metricFound)
				throw new IllegalArgumentMonitoringException("Wrong Ceilometer metric name inserted: " + metricName
						+ "or not existing into proerty file: " + metrics.toString());
		}
		return metricFound;
	}

	public List<String> getCeilometerMetrics() {
		List<String> metricNames = new ArrayList<>();
		for (Metric metric : monitoringMappings.getServiceMonitoring().getIaasCategoryService("IaaS")
				.getIaasAtomicService("IaaS").getMetrics()) {
			metricNames.add(metric.getName());
		}
		return metricNames;
	}

	public String getExistingPaasPORT(String zone, String serverType, String atomicService, String portName) {
		boolean metricFound = false;

		List<String> ports = getPORTperAtomicService(atomicService, portName);
		for (PortsMacro port : monitoringMappings.getServiceMonitoring()
				.getPaasCategoryService(MonitoringConstant.ATOMIC_SERVICE_PAAS)
				.getPaasAtomicService(MonitoringConstant.ATOMIC_SERVICE_PAAS).getPortsMacros()) {
			if (port.getName().equalsIgnoreCase(portName)) {
				metricFound = true;
				return port.getName();
			}
		}
		if (!metricFound)
			throw new IllegalArgumentMonitoringException("Wrong PORT MACRO  inserted: " + portName
					+ "or not existing into proerty file: " + ports.toString());
		return null;
	}

	public List<String> getPORTperAtomicService(String atomicService, String portName) {
		List<String> portNames = new ArrayList<>();
		boolean portNameFound = true;
		for (PortsMacro port : monitoringMappings.getServiceMonitoring().getPaasCategoryService(atomicService)
				.getPaasAtomicService(atomicService).getPortsMacros()) {
			portNames.add(port.getName());
			for (String portname : portNames) {
				if (portname.equalsIgnoreCase(portName)) {
					portNameFound = true;
					break;
				}
			}
			if (!portNameFound)
				throw new MonitoringException("Unable to find portName passed as parameter");
		}
		return portNames;
	}

	public String getSvcEndpointProperty(String name) {
		return serviceProperties.getProperty(name);
	}

	public String getZabbixIaaSURL(String zone) {
		return monitoringZones.getZone(zone).getServer(InfoType.INFRASTRUCTURE.getInfoType()).getUrl();
	}

	public String getZabbixMetricsURL(String zone) {
		return monitoringZones.getZone(zone).getServer(InfoType.SERVICE.getInfoType()).getUrl();
	}

	public String getZabbixWatcherURL(String zone) {
		return monitoringZones.getZone(zone).getServer(InfoType.WATCHER.getInfoType()).getUrl();
	}

	public String getZabbixServerURL(InfoType serverType) throws IllegalArgumentZabbixException {
		try {
			return getZabbixServerURL(serverType);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentZabbixException(e.getMessage(), e);
		}
	}

	public String getZabbixServerURL(String zone, String server) throws IllegalArgumentZabbixException {

		if (server.equalsIgnoreCase(InfoType.INFRASTRUCTURE.getInfoType()))
			return getZabbixIaaSURL(zone);
		else if (server.equalsIgnoreCase(InfoType.SERVICE.getInfoType()))
			return getZabbixMetricsURL(zone);
		else if (server.equalsIgnoreCase(InfoType.PAAS.getInfoType()))
			return getZabbixMetricsURL(zone);
		else if (server.equalsIgnoreCase(InfoType.WATCHER.getInfoType()))
			return getZabbixWatcherURL(zone);
		else
			throw new IllegalArgumentZabbixException("Cannot find " + server + " into [InfoType] Enum");
	}

	/**
	 * Returns the proxy name depending on the given zone and server type. <br/>
	 * <br/>
	 * In case the <b>Distributed Architecture</b> is enabled, the proxy name
	 * will be generated replacing the placeholder in the nameTemplate prorperty
	 * with the given workgroup.
	 * 
	 * @param zone
	 * @param serverType
	 * @param workgroup
	 * @return
	 */
	public String getProxyName(String zone, String serverType, String workgroupId) {
		String proxyName = null;
		if (getMonitoringZones().getZone(zone).getDistributedArchitecture() != null
				&& getMonitoringZones().getZone(zone).getDistributedArchitecture() == true)
			try {
				proxyName = String.format(
						getMonitoringZones().getZone(zone).getServer(serverType).getProxy().getNameTemplate(),
						workgroupId);
			} catch (Exception e) {
				throw new IllegalArgumentException("Proxy template name generation failed: ", e);
			}
		else
			proxyName = getMonitoringZones().getZone(zone).getServer(serverType).getProxy().getName();

		if (proxyName == null || proxyName.length() == 0)
			throw new IllegalArgumentException("Proxy name cannot be empty");

		return proxyName;
	}

	/**
	 * @see Configuration#getProxyName(String, ServerType, String)
	 * @param zone
	 * @param serverType
	 * @param workgroup
	 * @return
	 */
	public String getProxyNames(String zone, String serverType, String workgroup) {
		return getProxyName(zone, serverType, workgroup);
	}

	public static String getWorkgroupId(String hostgroup) {
		return hostgroup.replace(MonitoringConstant.WG_PREFIX, "");
	}

	public String getVersionProperty(String name) {
		return versionProperties.getProperty(name);
	}

	public static class ConfigProperties {

		/**
		 * Monitoring property file
		 */
		public final static String MONITORING_PROPERTY_FILE = "monitoring.yml";
		public final static String ZONE_PROPERTY_FILE = "zones.yml";
		public final static String MAPPING_PROPERTY_FILE = "mapping.yml";
		public final static String ZONES = "zones";

		/**
		 * Monitoring Environment property file
		 */
		public final static String MONITORING_ENVIRONMENT_FILE = "services-endpoints.properties";

		/**
		 * Monitoring mapping Strings
		 */
		public final static String ENVIRONMENTS = "environments";
		public final static String FORCE_ENVIRONMENT = "force_environment";
		public final static String WEB_SERVICE_DEBUG = "web_service_debug";
		public final static String PROXY_CONF = "proxy_configuration";
		// public final static String DISTRIB_PROXIES_ARCH =
		// "distributed_proxies_architecture";
		public final static String DISTRIB_ARCH = "distributed_architecture_implemented";
		public final static String DEFAULT_ARCH = "default_architecture_implemented";

		// The current environment
		public static String CURRENT_ENVIRONMENT;

		/**
		 * services-endpoints.properties Strings
		 */
		public final static String LABEL = "label";
		public final static String ZABBIX_IAAS_URL = "server_zabbix_iaas_URL";
		public final static String ZABBIX_PROXY_IAAS_NAME = "proxy_zabbix_iaas_name";
		public final static String ZABBIX_METRICS_URL = "server_zabbix_metrics_URL";
		public final static String ZABBIX_PROXY_METRICS_NAME = "proxy_zabbix_metrics_name";
		public final static String ZABBIX_WATCHER_URL = "server_zabbix_watcher_URL";
		public final static String ZABBIX_PROXY_WATCHER_NAME = "proxy_zabbix_watcher_name";
		public final static String ZABBIX_RPC_VERSION = "server_zabbix_rpc_version";
		public final static String ZABBIX_USERNAME = "usernameZabbixServer";
		public final static String ZABBIX_PASSWORD = "pswdZabbixServer";

		/**
		 * Ceilometer Metrics
		 */
		public final static String CEILOMETER_SCRIPT = "ceilometer_script";
		public final static String TEMPLATE_IAAS_CEILOMETER = "template_iaas_ceilometer";
		public final static String CEILOMETER_CPU_LOAD = "cpu_load";
		public final static String CEILOMETER_CPU_UPTIME = "cpu_uptime";
		public final static String CEILOMETER_VCPUs = "vcpus";
		public final static String CEILOMETER_RAM = "ram";
		public final static String CEILOMETER_DISK_EPHEM = "disk_ephemeral";
		public final static String CEILOMETER_DISK_SIZE = "disk_size";
		public final static String CEILOMETER_NETIN = "netIN";
		public final static String CEILOMETER_NETOUT = "netOUT";
		// Ceilometer Aggregated Metrics
		public final static String AGGREGATED_RAM = "aggrRAM";
		public final static String AGGREGATED_CPULOAD = "aggrCPULoad";
		public final static String AGGREGATED_DISK = "aggrDISK";

	}

}
