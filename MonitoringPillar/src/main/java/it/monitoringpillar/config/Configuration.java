package it.monitoringpillar.config;

import it.monitoringpillar.adapter.zabbix.exception.IllegalArgumentZabbixException;
import it.monitoringpillar.config.PathHelper.ResourceType;
import it.prisma.domain.dsl.monitoring.InfoType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Startup
@Singleton
public class Configuration {

	private static final Logger LOG = LogManager.getLogger(Configuration.class);

	// General monitoring properties
	private Properties monitoringProperties;

	// Properties read from {CURRENT_ENVIRONMENT}/services-endpoints.properties
	private Properties serviceProperties;

	private Properties macroProperties;

	private Properties zoneProperties;

	public final String varResourceProfilesBasePath = PathHelper
			.getResourcesPath(ResourceType.CONFIG_ENV_VARIABLE_PROPERTIES_PROFILES);

	@PostConstruct
	private void init() {
		try {
			LOG.info("Init configuration");

			// Forward proxy system property
			if (System.getenv("java.net.useSystemProxies") != null) {
				System.setProperty("java.net.useSystemProxies", System.getenv("java.net.useSystemProxies"));
			}

			loadMonitoringPropertiesFile();

			// Load properties for hostname
			loadServicesEndpointsProperties();

		} catch (Exception ex) {
			throw new Error("CANNOT LOAD ENVIRONMENT CONFIGURATION !", ex);
		}
	}

	/**
	 * Load properties and set current environment
	 */
	private void loadMonitoringPropertiesFile() {

		// FOR TEST
		String hostname = null;
		try {
			hostname = InetAddress.getLocalHost().getCanonicalHostName();
			LOG.info("Find host: " + InetAddress.getLocalHost().getCanonicalHostName());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}// END of TEST

		try (InputStream in = Configuration.class.getClassLoader().getResourceAsStream(
				ConfigProperties.MONITORING_PROPERTY_FILE)) {

			monitoringProperties = new Properties();
			monitoringProperties.load(in);

			LOG.info("Monitoring Properties loaded successfully!");

			for (Entry<Object, Object> item : monitoringProperties.entrySet()) {
				LOG.info("[Monitoring Endpoints] - K: " + item.getKey() + ", V: " + item.getValue());
			}

			if (getForcedEnvironment().isEmpty()) {
				// Try to get Environment name from server's hostname
				String hostName = InetAddress.getLocalHost().getCanonicalHostName();
				LOG.info("Find host: " + hostName);

				String[] envs = monitoringProperties.get(ConfigProperties.ENVIRONMENTS).toString().split(",");
				for (String env : envs) {
					if (hostName.toLowerCase().contains(env)) {
						ConfigProperties.CURRENT_ENVIRONMENT = env;
						break;
					}
				}
				if (ConfigProperties.CURRENT_ENVIRONMENT == null || ConfigProperties.CURRENT_ENVIRONMENT.isEmpty()) {
					LOG.debug("Environment for host " + hostName + " not found. Using localhost");
					ConfigProperties.CURRENT_ENVIRONMENT = "localhost";
				}
			} else {
				LOG.debug("Forcing environment");
				ConfigProperties.CURRENT_ENVIRONMENT = getForcedEnvironment();
			}
			LOG.info("Current Environment is: " + ConfigProperties.CURRENT_ENVIRONMENT);
			LOG.info("Proxy is set to: " + ConfigProperties.PROXY_CONF);
		} catch (Exception e) {
			LOG.error("Cannot load " + ConfigProperties.MONITORING_PROPERTY_FILE + e.getMessage());

			LOG.debug("Find host: " + hostname);

			throw new Error("Cannot load " + ConfigProperties.MONITORING_PROPERTY_FILE, e);
		}
	}

	/**
	 * Load properties and Check for Zones
	 */
	public boolean loadMonitoringPropertiesFileAndZoneProperties(String zone) {

		boolean propertyZoneFound = false;
		try (InputStream in = Configuration.class.getClassLoader().getResourceAsStream(
				ConfigProperties.MONITORING_PROPERTY_FILE)) {

			monitoringProperties = new Properties();
			monitoringProperties.load(in);

			for (Entry<Object, Object> item : monitoringProperties.entrySet()) {

				if (!getForcedEnvironment().isEmpty() && zone.equalsIgnoreCase(item.getValue().toString())
						&& zone.equalsIgnoreCase(getForcedEnvironment()))
					propertyZoneFound = true;
				else if (getForcedEnvironment().isEmpty() && zone.equalsIgnoreCase(item.getValue().toString()))
					propertyZoneFound = true;
			}
			if (!getForcedEnvironment().isEmpty() && !propertyZoneFound) {
				throw new Error(
						"ForceEnvironment Property does not match with one of the Zones listed in monitoring.properties file: "
								+ "either check if there is any mistype error or add one or both parameters by paying attention they match with each other");
			}

		} catch (Exception e) {
			throw new Error("Cannot load " + ConfigProperties.MONITORING_PROPERTY_FILE, e);
		}
		LOG.info("Found Zone passed: " + propertyZoneFound);
		return propertyZoneFound;
	}

	/**
	 * Load properties and Check for Ceilometer Metrics
	 */
	public boolean loadMonitoringPropertiesFileAndCheckCeilometerProperties(String ceilometerMetric) {

		boolean propertyCeilometerMetricFound = false;
		try (InputStream in = Configuration.class.getClassLoader().getResourceAsStream(
				ConfigProperties.MONITORING_PROPERTY_FILE)) {

			monitoringProperties = new Properties();
			monitoringProperties.load(in);

			for (Entry<Object, Object> item : monitoringProperties.entrySet()) {
				if (ceilometerMetric.equalsIgnoreCase(item.getValue().toString()))
					propertyCeilometerMetricFound = true;
			}

		} catch (Exception e) {
			throw new Error("Cannot load " + ConfigProperties.MONITORING_PROPERTY_FILE, e);
		}
		LOG.info("Found Ceilometer Metric passed: " + propertyCeilometerMetricFound);
		return propertyCeilometerMetricFound;
	}

	private void loadServicesEndpointsProperties() {

		String path = varResourceProfilesBasePath + ConfigProperties.CURRENT_ENVIRONMENT + File.separator
				+ ConfigProperties.MONITORING_ENVIRONMENT_FILE;
		LOG.info("Reading properties from [{}]", path);

		try (InputStream in = new FileInputStream(path)) {

			serviceProperties = new Properties();
			serviceProperties.load(in);

			for (Entry<Object, Object> item : serviceProperties.entrySet()) {
				LOG.info("[Services Endpoints] - K: " + item.getKey() + ", V: " + item.getValue());
			}

		} catch (IOException e) {
			LOG.error("ERROR: Cannot load [" + ConfigProperties.MONITORING_ENVIRONMENT_FILE + "], " + e.getMessage());
			throw new Error("Cannot load " + ConfigProperties.MONITORING_ENVIRONMENT_FILE);
		}
	}

	private String readMacrosProperties(String macroName) {

		try (InputStream in = Configuration.class.getClassLoader().getResourceAsStream(
				ConfigProperties.MONITORING_PROPERTY_FILE)) {

			macroProperties = new Properties();
			macroProperties.load(in);

			for (Entry<Object, Object> item : macroProperties.entrySet()) {
				if (macroName.equals(item.getKey())) {
					return item.getValue().toString();
				}
			}
			throw new Error("No MACRO Contained into Property file or does not match with the listed one");

		} catch (IOException e) {
			LOG.error("ERROR: Cannot load [" + ConfigProperties.MONITORING_ENVIRONMENT_FILE + "], " + e.getMessage());
			throw new Error("Cannot load " + ConfigProperties.MONITORING_ENVIRONMENT_FILE);
		}
	}

	public boolean isMACROexisting(String macroName) {
		String macroNameFound = readMacrosProperties(macroName);
		if (!macroNameFound.equals(null)) {
			return true;
		} else
			return false;
	}

	public String getSvcEndpointProperty(String name) {
		return serviceProperties.getProperty(name);
	}

	public String getZabbixIaaSURL() {
		return serviceProperties.getProperty(ConfigProperties.ZABBIX_IAAS_URL);
	}

	public String getZabbixMetricsURL() {
		return serviceProperties.getProperty(ConfigProperties.ZABBIX_METRICS_URL);
	}

	public String getZabbixWatcherURL() {
		return serviceProperties.getProperty(ConfigProperties.ZABBIX_WATCHER_URL);
	}

	public String getZabbixServerURL(String server) throws IllegalArgumentZabbixException {
		try {
			return getZabbixServerURL(InfoType.lookupFromName(server));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentZabbixException(e.getMessage(), e);
		}
	}

	public String getZabbixServerURL(InfoType server) throws IllegalArgumentZabbixException {
		switch (server) {
		case INFRASTRUCTURE:
			return getZabbixIaaSURL();
		case SERVICE:
			return getZabbixMetricsURL();
		case PAAS:
			return getZabbixMetricsURL();
		case WATCHER:
			return getZabbixWatcherURL();
		default:
			throw new IllegalArgumentZabbixException("Cannot find " + server + " into [InfoType] Enum");
		}
	}

	public String getZabbixProxyName(InfoType server) throws IllegalArgumentZabbixException {
		switch (server) {
		case INFRASTRUCTURE:
			return serviceProperties.getProperty(ConfigProperties.ZABBIX_PROXY_IAAS_NAME);
		case SERVICE:
			return serviceProperties.getProperty(ConfigProperties.ZABBIX_PROXY_METRICS_NAME);
		case PAAS:
			return serviceProperties.getProperty(ConfigProperties.ZABBIX_PROXY_METRICS_NAME);
		case WATCHER:
			return serviceProperties.getProperty(ConfigProperties.ZABBIX_PROXY_WATCHER_NAME);
		default:
			throw new IllegalArgumentZabbixException("Cannot find " + server + " into [InfoType] Enum");
		}
	}

	public String getZabbixRPCVersion() {
		return serviceProperties.getProperty(ConfigProperties.ZABBIX_RPC_VERSION);
	}

	public String getMonitoringEnvironment() {
		return serviceProperties.getProperty(ConfigProperties.CURRENT_ENVIRONMENT);
	}

	public String getUsernameZabbixServer() {
		return serviceProperties.getProperty(ConfigProperties.ZABBIX_USERNAME);
	}

	public String getPswdZabbixServer() {
		return serviceProperties.getProperty(ConfigProperties.ZABBIX_PASSWORD);
	}

	public String getPillarLabel() {
		return serviceProperties.getProperty(ConfigProperties.LABEL);
	}

	public boolean isWebServiceDebugEnabled() {
		return Boolean.valueOf(monitoringProperties.getProperty(ConfigProperties.WEB_SERVICE_DEBUG));
	}

	public boolean isProxyEnabled() {
		return Boolean.valueOf(monitoringProperties.getProperty(ConfigProperties.PROXY_CONF));
	}

	public boolean isDistributedArchitecuterImplementened() {
		return Boolean.valueOf(monitoringProperties.getProperty(ConfigProperties.DISTRIB_ARCH));
	}

	public boolean isCeilometerScriptUsed() {
		return Boolean.valueOf(monitoringProperties.getProperty(ConfigProperties.CEILOMETER_SCRIPT));
	}

	public String TemplateIaaSCeilometerNameinZabbixMetrics() {
		return monitoringProperties.getProperty(ConfigProperties.TEMPLATE_IAAS_CEILOMETER);
	}

	public String getForcedEnvironment() {

		return monitoringProperties.getProperty(ConfigProperties.FORCE_ENVIRONMENT);

	}

	private String getZoneName(String zone) {

		try (InputStream in = Configuration.class.getClassLoader().getResourceAsStream(
				ConfigProperties.MONITORING_PROPERTY_FILE)) {

			zoneProperties = new Properties();
			zoneProperties.load(in);

			for (Entry<Object, Object> item : macroProperties.entrySet()) {
				if (zone.equals(item.getKey())) {
					return item.getValue().toString();
				}
			}
			throw new Error("No ZONE Contained into Property file or does not match with the listed one");

		} catch (IOException e) {
			LOG.error("ERROR: Cannot load [" + ConfigProperties.MONITORING_ENVIRONMENT_FILE + "], " + e.getMessage());
			throw new Error("Cannot load " + ConfigProperties.MONITORING_ENVIRONMENT_FILE);
		}
	}

	public static class ConfigProperties {

		/**
		 * Monitoring property file
		 */
		public final static String MONITORING_PROPERTY_FILE = "monitoring.properties";

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
		public final static String DISTRIB_ARCH = "distributed_architecture_implemented";

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
