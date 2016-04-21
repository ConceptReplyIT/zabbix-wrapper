package it.reply.monitoringpillar.wrapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import it.reply.monitoringpillar.adapter.zabbix.clientbuilder.ZabbixAdapterClientSetter;
import it.reply.monitoringpillar.adapter.zabbix.exception.NotFoundZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.exception.ZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ZabbixMethods;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.GroupWrapped4MetricList;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedMetric;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaasGroups4MetricList;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.PaasMachineWrapped4MetricList;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixHostGroupResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixItemResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostResponseV2_4;

@Stateless
public class MetricsWrapperBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ZabbixAdapterClientSetter<?> zabAdapSetter;

	/**
	 * 
	 * @param testbedType
	 * @param url
	 * @param token
	 * @param groupName
	 * @param vmuuid
	 * @param serviceCategory
	 * @param tag_service
	 * @param atomic_service_id
	 * @param metrics_id
	 * @param triggers_id
	 * @param history
	 * @param server
	 * @param requestTime
	 * @param groupInfo
	 * @param groupId
	 * @param hostInfo
	 * @param hostId
	 * @param items
	 * @param templates
	 * @return MonitoringWrappedResponsePaasGroups4MetricList
	 * @throws ZabbixException
	 * @throws Exception
	 * @throws IllegalArgumentException
	 */
	public MonitoringWrappedResponsePaasGroups4MetricList getMetricsWrapped(String zone, String serverType,
			String groupName, List<ZabbixHostGroupResponse> groupList, ZabbixHostGroupResponse group,
			List<ZabbixMonitoredHostResponseV2_4> hosts) throws ZabbixException {

		MonitoringWrappedResponsePaasGroups4MetricList wrappedPaas = new MonitoringWrappedResponsePaasGroups4MetricList();

		wrappedPaas.setGroups(getGroupInfo(zone, serverType, groupName, groupList, group, hosts));
		return wrappedPaas;
	}

	/**
	 * 
	 * @param adapterType
	 * @param testbedType
	 * @param vmuuid
	 * @param paasKey
	 * @return ArrayList<WorkGroup>
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public List<GroupWrapped4MetricList> getGroupInfo(String zone, String serverType, String groupName,
			List<ZabbixHostGroupResponse> groupList, ZabbixHostGroupResponse hostGroup,
			List<ZabbixMonitoredHostResponseV2_4> hosts) throws ZabbixException {

		String hostGroupIdtoAdapter = null;
		List<ZabbixItemResponse> zabbixTemplateResponse = new ArrayList<>();
		List<ZabbixItemResponse> metrics4Host = new ArrayList<>();
		List<ZabbixItemResponse> trigger4Host = new ArrayList<>();
		// Prepare the Array for groups
		List<GroupWrapped4MetricList> groupsresult = new ArrayList<>();
		boolean workgroupFound = false;

		// For each group into array set the name and ask collect the machines
		GroupWrapped4MetricList group = new GroupWrapped4MetricList();
		for (ZabbixHostGroupResponse hostgroup : groupList) {
			group.setGroupName(hostGroup.getName());
			for (ZabbixMonitoredHostResponseV2_4 host : hosts) {
				group.getPaasMachines()
						.addAll(getMachinesList(zone, serverType, hostGroupIdtoAdapter, groupName, hosts, host));
			}
		}
		groupsresult.add(group);

		if (groupsresult.isEmpty()) {
			throw new NotFoundZabbixException(
					"Wrong resource Group Name inserted or not existing into monitoring platform");
		}
		return groupsresult;
	}

	/**
	 * Retrieves Machines depending on passed parameters
	 * 
	 * @param adapterType
	 * @param testbedType
	 * @param hostGroupIdtoAdapter
	 * @param searchByGroup
	 * @return ArrayList<PaasMachinesList>
	 * @throws Exception
	 */
	public List<PaasMachineWrapped4MetricList> getMachinesList(String zone, String serverType,
			String hostGroupIdtoAdapter, String hostgroupName, List<ZabbixMonitoredHostResponseV2_4> hostInfo,
			ZabbixMonitoredHostResponseV2_4 host) throws ZabbixException {

		List<PaasMachineWrapped4MetricList> machines = new ArrayList<>();
		PaasMachineWrapped4MetricList paasMachine = new PaasMachineWrapped4MetricList();
		paasMachine.setMetrics(new ArrayList<MonitoringWrappedMetric>());

		String vmip = null;
		List<ZabbixItemResponse> zabbixinterfaceResponse = new ArrayList<>();

		String hostName = host.getName();
		String hostId = host.getHostid();

		// API useful for getting IP and Host's networkInfo
		zabbixinterfaceResponse = zabAdapSetter.getZabbixFeatureMultiService(zone, serverType, hostId, null,
				ZabbixMethods.INTERFACE.getzabbixMethod());

		for (ZabbixItemResponse zabbixresult : zabbixinterfaceResponse) {
			vmip = zabbixresult.getIp();
		}
		paasMachine.setMachineName(hostName);
		paasMachine.setIp(vmip);

		if (host.getAvailable().equalsIgnoreCase("1") || host.getAvailable().equalsIgnoreCase("0"))
			paasMachine.setEnabled(true);
		else
			paasMachine.setEnabled(false);

		for (ZabbixItemResponse item : host.getItems()) {
			paasMachine.getMetrics().addAll(
					getMetricList(serverType, hostGroupIdtoAdapter, hostgroupName, hostInfo, host, paasMachine, item));
		}
		machines.add(paasMachine);

		if (machines.isEmpty()) {
			// if(paasMachine==null){
			throw new NotFoundZabbixException("Host ID is not present in zabbix server");
		}
		// return paasMachine;
		return machines;
	}

	/**
	 * It sets the metrics
	 * 
	 * @param serverType
	 * @param hostGroupIdtoAdapter
	 * @param hostgroupName
	 * @param hosts
	 * @param host
	 * @param paasMachine
	 * @param item
	 * @return List<MonitoringWrappedMetric>
	 * @throws ZabbixException
	 */
	public List<MonitoringWrappedMetric> getMetricList(String serverType, String hostGroupIdtoAdapter,
			String hostgroupName, List<ZabbixMonitoredHostResponseV2_4> hosts, ZabbixMonitoredHostResponseV2_4 host,
			PaasMachineWrapped4MetricList paasMachine, ZabbixItemResponse item) throws ZabbixException {

		MonitoringWrappedMetric metricWrapped = new MonitoringWrappedMetric();
		List<MonitoringWrappedMetric> metrics = new ArrayList<>();
		metricWrapped.setMetricValue(item.getLastvalue());
		metricWrapped.setMetricName(item.getName());
		metricWrapped.setMetricTime(item.getLastclock());

		metrics.add(metricWrapped);
		return metrics;
	}
}