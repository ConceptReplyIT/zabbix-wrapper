package it.monitoringpillar.adapter.nagios;

import it.monitoringpillar.adapter.MonitoringTarget;
import it.monitoringpillar.exception.MonitoringException;
import it.prisma.domain.dsl.monitoring.businesslayer.paas.request.Port;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.MonitPillarEventResponse;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.iaas.WrappedIaasHealthByTrigger;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.FilterTimeRequest;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaasGroups;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaasGroups4HostList;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.UpdateGroupName;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.HostGroupResponse;

import java.util.List;

import javax.ejb.Stateless;

//@Named
@Stateless
// @Local(MonitoringAdapter.class)
@IMonitAdaptNagios
public class MonitoringAdapterNagios implements MonitoringTarget {

	@Override
	public HostGroupResponse updateMonitoredHostGroup(String serverType, String groupName, UpdateGroupName newGroupName)
			throws MonitoringException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WrappedIaasHealthByTrigger getTriggerByGroup(String serverType, String hostgroup) throws MonitoringException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void creationMonitoredHostGroup(String hostGroupName) throws MonitoringException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMonitoredHost(String serverType, String hostvmuuids, String serviceId) throws MonitoringException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteMonitoredHostGroup(String hostGroupName) throws MonitoringException {
		// TODO Auto-generated method stub

	}

	@Override
	public MonitPillarEventResponse getOverallServerEvents(String serverType, String host_id,
			String service_category_id, String tag_service, FilterTimeRequest requestTime) throws MonitoringException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getDisablingItem(String server_type, String group, String host_id, String metric_id,
			String tag_service, String update) throws MonitoringException {
		// TODO Auto-generated method stub

	}

	@Override
	public WrappedIaasHealthByTrigger getTriggerByHost(String serverType, String group, String host)
			throws MonitoringException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonitoringWrappedResponsePaasGroups getGroupsInfoWrapped(String testbed) throws MonitoringException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonitoringWrappedResponsePaasGroups4HostList getHostsInfoWrapped(String testbed, String serverType)
			throws MonitoringException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getInfoWrapperGeneric(String serverType, String group, String host, String service_category,
			String tag_service, List<String> atomic_service_id, String metric, List<String> triggers_id,
			String history, FilterTimeRequest requestTime) throws MonitoringException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getDisablingHost(String server_type, String group, String host_id, String metric_id,
			String tag_service, String update) throws MonitoringException {
		// TODO Auto-generated method stub

	}

	@Override
	public void creationMonitoredHost(String serverType, String hostGroup, String hostName, String vmuuid, String vmip,
			String serviceCategory, String serviceTag, List<String> service, Boolean activeMode, List<Port> ports)
			throws MonitoringException {

	}

}