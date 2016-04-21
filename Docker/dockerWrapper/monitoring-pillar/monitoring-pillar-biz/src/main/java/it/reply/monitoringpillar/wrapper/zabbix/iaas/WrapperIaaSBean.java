package it.reply.monitoringpillar.wrapper.zabbix.iaas;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.reply.monitoringpillar.adapter.zabbix.clientbuilder.ZabbixAdapterClientSetter;
import it.reply.monitoringpillar.adapter.zabbix.exception.ZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.handler.MetricsParserHelper;
import it.reply.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ZabbixMethods;
import it.reply.monitoringpillar.domain.dsl.monitoring.MonitoringFeatureUtility.MonitoringMetricsIAASNames;
import it.reply.monitoringpillar.domain.dsl.monitoring.businesslayer.iaas.hypervisor.HypervisorGroup;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.iaas.IaaSMetric;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.iaas.IaasGroupOfMachine;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.iaas.IaasMachine;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.iaas.IaasThresholdsList;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.iaas.MonitoringWrappedResponseIaas;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.iaas.PrismaIaasScript;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixHostGroupResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixItemResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostsResponse;
import it.reply.monitoringpillar.utility.TimestampMonitoring;
import it.reply.utils.json.JsonUtility;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

@Stateless
public class WrapperIaaSBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String hostName;
	private String groupName;
	private String hostGroupIdtoAdapter;
	private String vmip;
	private String templateId;
	private String hostIdtoAdapter = null;
	private String metricName;
	private String metricID;
	private String triggerExpression;
	private String triggerValue;
	private String connectedHost;

	@Inject
	private ZabbixAdapterClientSetter<?> zabAdapClientSetter;

	/**
	 * 
	 * @param adapterType
	 * @param testbedType
	 * @param iaasType
	 * @return MonitoringWrappedResponseIaasV2
	 * @throws IOException
	 * @throws ZabbixAPIErrorException
	 * @throws ServerErrorResponseException
	 * @throws RestClientException
	 * @throws Exception
	 */
	public MonitoringWrappedResponseIaas getWrappedIaas(String zone, String serverType, String iaasTypes,
			String iaashosts, List<ZabbixHostGroupResponse> groups, String groupId,
			List<ZabbixMonitoredHostsResponse> hosts, String hostId) throws ZabbixException {
		MonitoringWrappedResponseIaas wrappedIaas = new MonitoringWrappedResponseIaas();
		// wrappedIaas.setTestbed(testbedType);
		wrappedIaas.setIaasMachineGroups(
				getGroupInfo(zone, serverType, iaasTypes, iaashosts, groups, groupId, hosts, hostId));
		return wrappedIaas;
	}

	/**
	 * It gives info about groups in the platform
	 * 
	 */
	/**
	 * 
	 * @param testbedType
	 * @param url
	 * @param token
	 * @param iaasGroupNames
	 * @param iaashostNames
	 * @param groups
	 * @param groupId
	 * @param hosts
	 * @param hostId
	 * @return
	 * @throws Exception
	 * @throws IllegalArgumentException
	 * @throws ZabbixAPIErrorException
	 * @throws ServerErrorResponseException
	 * @throws RestClientException
	 * @throws IOException
	 */
	private ArrayList<IaasGroupOfMachine> getGroupInfo(String zone, String serverType, String iaasGroupNames,
			String iaashostNames, List<ZabbixHostGroupResponse> groups, String groupId,
			List<ZabbixMonitoredHostsResponse> hosts, String hostId) throws ZabbixException {

		ArrayList<IaasGroupOfMachine> groupsList = new ArrayList<>();

		IaasGroupOfMachine hostgroupList = new IaasGroupOfMachine();

		for (ZabbixHostGroupResponse groupInfo : groups) {

			hostgroupList.setIaasMachinesList(new ArrayList<IaasMachine>());

			groupName = groupInfo.getName();

			/*******************************
			 * HOST GROUP SPECIFIED
			 ******************************/

			if (iaasGroupNames != null && iaashostNames == null) {
				String hostIdtoAdapter = null;

				hostGroupIdtoAdapter = groupInfo.getGroupid();
				hostgroupList.setIaasGroupName(groupName);
				hostgroupList.setIaasMachinesList(
						getMachinesList(zone, serverType, hostGroupIdtoAdapter, hostIdtoAdapter, hosts));
				groupsList.add(hostgroupList);
			}

			/*************************************************
			 * ASK FOR SPECIFIC HOST INTO A GROUP
			 ************************************************/

			else if (iaashostNames != null) {
				hostGroupIdtoAdapter = groupInfo.getGroupid();
				hostgroupList.setIaasGroupName(groupInfo.getName());

				hostIdtoAdapter = hosts.get(0).getHostid();

				hostgroupList.getIaasMachines()
						.addAll((getMachinesList(zone, serverType, hostGroupIdtoAdapter, hostIdtoAdapter, hosts)));

				groupsList.add(hostgroupList);
			}

			else if (!(groupName.equals("Hypervisors") || groupName.equals("Linux servers")
					|| groupName.equals("Virtual machines") || groupName.equals("Discovered hosts")
					|| groupName.equals("Zabbix servers") || groupName.equals("Templates")) && iaasGroupNames == null) {
				hostGroupIdtoAdapter = groupInfo.getGroupid();

				hostgroupList.setIaasGroupName(groupName);
				hostgroupList.setIaasMachinesList(
						getMachinesList(zone, serverType, hostGroupIdtoAdapter, hostIdtoAdapter, hosts));
				groupsList.add(hostgroupList);
			}
		}
		return groupsList;
	}

	/**
	 * 
	 * @param adapterType
	 * @param testbedType
	 * @param hostGroupIdtoAdapter
	 * @return ArrayList<IaasMachinesList>
	 * @throws ZabbixAPIErrorException
	 * @throws ServerErrorResponseException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws Exception
	 */
	private ArrayList<IaasMachine> getMachinesList(String zone, String serverType, String hostGroupIdtoAdapter,
			String hostIdtoAdapter, List<ZabbixMonitoredHostsResponse> hostInfo) throws ZabbixException {

		ArrayList<IaasMachine> machinesList = new ArrayList<>();

		if (!(hostIdtoAdapter == null)) {
			machinesList = new ArrayList<IaasMachine>();

			for (ZabbixMonitoredHostsResponse hostResult : hostInfo) {
				IaasMachine machines = new IaasMachine();
				String hostIdtoAdapterCurrent = hostResult.getHostid();
				if (hostIdtoAdapterCurrent.equals(hostIdtoAdapter)) {
					String monitoredHost = hostResult.getAvailable();
					if (monitoredHost.equals("1")) {
						connectedHost = "MONITORED";
					} else {
						connectedHost = "No communication between the agent and the Server";
					}

					ArrayList<ZabbixItemResponse> zabbixinterfaceResponse = (ArrayList<ZabbixItemResponse>) zabAdapClientSetter
							.getZabbixFeatureMultiService(zone, serverType, hostIdtoAdapter, null,
									ZabbixMethods.INTERFACE.getzabbixMethod());

					vmip = zabbixinterfaceResponse.get(0).getIp();

					// Set Hosts' carachteristics
					hostName = hostResult.getName();
					machines.setMachineName(hostName);
					machines.setIp(vmip);
					machines.setConnection(connectedHost);

					machines.setMetrics(getIaasMetrics(zone, serverType, hostIdtoAdapter));

					machinesList.add(machines);
				}
			}
		}

		// no HostID
		else if (hostIdtoAdapter == null) {
			machinesList = new ArrayList<IaasMachine>();

			for (ZabbixMonitoredHostsResponse hostResult : hostInfo) {
				IaasMachine machines = new IaasMachine();
				hostName = hostResult.getName();
				if (hostName != "Zabbix server") {
					hostIdtoAdapter = hostResult.getHostid();
					String monitoredHost = hostResult.getAvailable();
					if (monitoredHost.equals("1")) {
						connectedHost = "MONITORED";
					} else {
						connectedHost = "No communication between the agent and the Server";
					}

					// if (IaaS==true)
					ArrayList<ZabbixItemResponse> zabbixinterfaceResponse = (ArrayList<ZabbixItemResponse>) zabAdapClientSetter
							.getZabbixFeatureMultiService(zone, serverType, hostIdtoAdapter, null,
									ZabbixMethods.INTERFACE.getzabbixMethod());

					vmip = zabbixinterfaceResponse.get(0).getIp();

				}
				// Set Hosts' carachteristics
				machines.setMachineName(hostName);
				machines.setIp(vmip);
				machines.setConnection(connectedHost);
				machines.setMetrics(getIaasMetrics(zone, serverType, hostIdtoAdapter));
				machinesList.add(machines);
			}
		}
		return machinesList;
	}

	/**
	 * For a certain host and a certain service category gets metrics associated
	 * to it
	 * 
	 * @param adapterType
	 * @param testbedType
	 * @param hostidtoAdapter
	 * @return List<IaasMetricsList>
	 * @throws ZabbixAPIErrorException
	 * @throws ServerErrorResponseException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 * @throws Exception
	 */
	private ArrayList<IaaSMetric> getIaasMetrics(String zone, String serverType, String hostidtoAdapter)
			throws ZabbixException {
		ArrayList<IaaSMetric> metrics = new ArrayList<>();

		ArrayList<ZabbixItemResponse> metrics4Host = (ArrayList<ZabbixItemResponse>) zabAdapClientSetter
				.getItemsService(zone, serverType, hostidtoAdapter, templateId, null, null,
						ZabbixMethods.METRIC.getzabbixMethod(), null);

		// Loop into all Metrics
		for (ZabbixItemResponse metrictype : metrics4Host) {
			IaaSMetric iaasMetrics = new IaaSMetric();
			metricName = metrictype.getName();
			metricID = metrictype.getItemid();
			// metricKey = metrictype.getKey();
			String metricKeyDebug = groupName + "." + hostName + "." + metricName;

			String metricTime = TimestampMonitoring.decodUnixTime2Date(Long.parseLong(metrictype.getLastclock()));

			String metricValueType = metrictype.getValueType();

			Object metricValue = MetricsParserHelper.getMetricIaaSParsedValue(metricValueType,
					metrictype.getLastvalue());

			// if Metrics are values coming from External Scritps manage this
			if (metricName.equals(MonitoringMetricsIAASNames.PRISMA_IAAS_SCRIPT.getzabbixMetricsIAASNames())) {
				try {
					PrismaIaasScript scriptIaas = (PrismaIaasScript) JsonUtility.deserializeJson(metricValue.toString(),
							PrismaIaasScript.class);
					metricValue = scriptIaas;

					if (scriptIaas.getNetwork().equals("OK") && scriptIaas.getStorage().equals("OK")
							&& scriptIaas.getAvailableNodes() > 0) {
						iaasMetrics.setMetricStatus("OK");
					} else
						iaasMetrics.setMetricStatus("PROBLEM");
				} catch (Exception e) {
					throw new ZabbixException(e);
				}

			}

			else if (metricName.equals(MonitoringMetricsIAASNames.HYPERVISOR_IAAS_SCRIPT.getzabbixMetricsIAASNames())) {
				try {
					HypervisorGroup hypervisorScript = (HypervisorGroup) JsonUtility
							.deserializeJson(metricValue.toString(), HypervisorGroup.class);
					metricValue = hypervisorScript;
				} catch (Exception e) {
					throw new ZabbixException(e);
				}
			}
			iaasMetrics.setMetricName(metricName);
			iaasMetrics.setMetricValue(metricValue);
			iaasMetrics.setMetricTime(metricTime);

			if (!(metricName == MonitoringMetricsIAASNames.PRISMA_IAAS_SCRIPT.getzabbixMetricsIAASNames())) {

				if (!(metricValue.equals("0") || metricValue == null)) {
					iaasMetrics.setMetricStatus("OK");
				} else
					iaasMetrics.setMetricStatus("PROBLEM at: " + metricKeyDebug);
			}
			iaasMetrics.setIaasThresholds(getThreshold(zone, serverType, metricID));
			iaasMetrics.setMetricKey(metricKeyDebug);
			metrics.add(iaasMetrics);
		}
		return metrics;
	}

	/**
	 * Get Triggers for each metric
	 * 
	 * @param adapterType
	 * @param testbedType
	 * @param metricID
	 * @return ArrayList<IaasThresholdsList>
	 * @throws IllegalArgumentException
	 * @throws RestClientException
	 * @throws ZabbixAPIErrorException
	 * @throws ServerErrorResponseException
	 * @throws NoMappingModelFoundException
	 * @throws @throws
	 *             Exception
	 */
	private List<IaasThresholdsList> getThreshold(String zone, String serverType, String metricID)
			throws ZabbixException {

		ArrayList<ZabbixItemResponse> trigger4Host = (ArrayList<ZabbixItemResponse>) zabAdapClientSetter
				.getTriggerService(zone, serverType, hostIdtoAdapter, metricID,
						ZabbixMethods.TRIGGER.getzabbixMethod());
		ArrayList<IaasThresholdsList> thresholds = new ArrayList<IaasThresholdsList>();
		for (ZabbixItemResponse triggerList : trigger4Host) {
			IaasThresholdsList iaasThresholds = new IaasThresholdsList();

			triggerExpression = triggerList.getExpression();
			triggerValue = triggerList.getValue();
			String triggerKeyDebug = groupName + "." + hostName + "." + metricName + "." + triggerExpression;

			iaasThresholds.setTriggerKey(triggerKeyDebug);

			iaasThresholds.setTriggerExpression(triggerExpression);
			iaasThresholds.setTriggerValue(triggerValue);
			if (triggerValue.equals("0")) {
				iaasThresholds.setTriggerStatus("OK");
			} else {
				iaasThresholds.setTriggerStatus("PROBLEM at: " + triggerKeyDebug);
			}
			thresholds.add(iaasThresholds);
		}
		return thresholds;
	}
}
