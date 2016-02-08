package it.prisma.domain.dsl.monitoring;

public class MonitoringFeatureUtility {

	/**
	 * Enum for Adapter
	 * 
	 */
	public enum Adapter {

		ZABBIX("zabbix"), NAGIOS("nagios");

		private Adapter(String adapter) {
			this.adapter = adapter;
		}

		private String adapter;

		public String getAdapter() {
			return this.adapter;
		}

		public String toString() {
			return this.adapter;
		}
	}

	public enum MonitoringMetricsIAASNames {

		PRISMA_IAAS_SCRIPT("Prisma_IaaS"), HYPERVISOR_IAAS_SCRIPT("Hypervisor show");

		private MonitoringMetricsIAASNames(String zabbixMetricsIAASNames) {
			this.zabbixMetricsIAASNames = zabbixMetricsIAASNames;
		}

		private String zabbixMetricsIAASNames;

		public String getzabbixMetricsIAASNames() {
			return this.zabbixMetricsIAASNames;
		}

		public String toString() {
			return this.zabbixMetricsIAASNames;
		}
	}

	public enum MonitoringMetricsPAASNames {

		Ceilometer_CPULOAD_Aggregation("CeilometerCPULoadAggregation"), Ceilometer_RAM_Aggregation(
				"CeilometerRAMAggregation"), Ceilometer_DISK_Aggregation("CeilometerDISKLoadAggregation"),

		Ceilometer_CPU_LOAD("IaaSCeilometerCPUUTIL"), Ceilometer_CPU_Uptime("IaaSCeilometerCPU"), Ceilometer_STORAGE(
				"IaaSCeilometerDISKROOTSIZE"), Ceilometer_NETW_OUT("IaaSCeilometerNETWORKOUTGOINGBYTES"), Ceilometer_NETW_IN(
				"IaaSCeilometerNETWORKINCOMINGBYTES"), Ceilometer_RAM("IaaSCeilometerMEMORY"), Ceilometer_VCPUs(
				"IaaSCeilometerVCPUS"),

		// Watcher Metrics
		MySQLQueriesPerSecond("MySQLQueriesPerSecond"), MySQLSelectPerSecond("MySQLSelectPerSecond");

		private MonitoringMetricsPAASNames(String zabbixMetricsPAASNames) {
			this.zabbixMetricsPAASNames = zabbixMetricsPAASNames;
		}

		private String zabbixMetricsPAASNames;

		public String getzabbixMetricsPAASNames() {
			return this.zabbixMetricsPAASNames;
		}

		public String toString() {
			return this.zabbixMetricsPAASNames;
		}
	}

	public enum IaaSHostGroups {

		COMPUTE("Compute"), CONTROLLER("Controller"), NETWORK("Network"), IAAS("IaaS"), INFRASTRUCTURE_HEALTH(
				"InfrastructureHealth"), INFRASTRUCTURE_HEALTH_LOCALHOST("InfrastructureHealthLocalHost");

		private String groupName;

		private IaaSHostGroups(String groupName) {
			this.groupName = groupName;
		}

		public String getGroupName() {
			return groupName;
		}

		public static IaaSHostGroups lookupFromName(String groupName) {
			for (IaaSHostGroups v : values()) {
				if (groupName.equals(v.getGroupName())) {
					return v;
				}
			}
			throw new IllegalArgumentException("Cannot find " + groupName + " in HostGroups");
		}

	}

	public enum HostIaaS {

		PRISMA_IAAS("Prisma_IaaS"), PRISMA_HYPERVISOR("Prisma_Hypervisor");

		private String hostName;

		private HostIaaS(String hostName) {
			this.hostName = hostName;
		}

		public String getHostName() {
			return hostName;
		}

		public HostIaaS lookupFromName(String hostName) {
			for (HostIaaS v : values()) {
				if (hostName.equals(v.name())) {
					return v;
				}
			}
			throw new IllegalArgumentException("Cannot find " + hostName + " in HostName Enum");
		}
	}

	public enum HostPaaS {

		PRISMA_AGGREGATOR("Aggregator");

		private String hostName;

		private HostPaaS(String hostName) {
			this.hostName = hostName;
		}

		public String getHostName() {
			return hostName;
		}

		public HostPaaS lookupFromName(String hostName) {
			for (HostPaaS v : values()) {
				if (hostName.equals(v.name())) {
					return v;
				}
			}
			throw new IllegalArgumentException("Cannot find " + hostName + " in HostName Enum");
		}
	}

	public enum InfrastructureBareMetalMetric {

		NETWORK("network"), STORAGE("storage"), AVAILABLE_NODES("availableNodes"), TOTAL_NODES("totalNodes");

		private String metric;

		private InfrastructureBareMetalMetric(String metric) {
			this.metric = metric;
		}

		public String getMetric() {
			return metric;
		}

		public InfrastructureBareMetalMetric lookupFromName(String metric) {
			for (InfrastructureBareMetalMetric v : values()) {
				if (metric.equals(v.name())) {
					return v;
				}
			}
			throw new IllegalArgumentException("Cannot find " + metric + " in InfrastructureBareMetalMetric Enum");
		}
	}

	public enum InfrastructureBareMetalStatus {

		OK("ok", 0.66f), WARNING("warning", 0.33f), PROBLEM("problem", 0.0f);

		private String status;
		private float level;

		private InfrastructureBareMetalStatus(String status, float level) {
			this.status = status;
			this.level = level;
		}

		public String getStatus() {
			return status;
		}

		public float getLevel() {
			return level;
		}

		public InfrastructureBareMetalStatus lookupFromName(String status) {
			for (InfrastructureBareMetalStatus v : values()) {
				if (status.equals(v.name())) {
					return v;
				}
			}
			throw new IllegalArgumentException("Cannot find " + status + " in InfrastructureBareMetalStatus Enum");
		}
	}

}