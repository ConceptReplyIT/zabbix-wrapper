package it.reply.monitoringpillar.adapter.zabbix.handler;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import it.reply.monitoringpillar.adapter.MonitoringAdapteeZabbix;
import it.reply.monitoringpillar.adapter.zabbix.ZabbixConstant;
import it.reply.monitoringpillar.adapter.zabbix.clientbuilder.ZabbixAdapterClientSetter;
import it.reply.monitoringpillar.adapter.zabbix.exception.ZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ZabbixMethods;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixGroup;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixHostGroupResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostResponseV2_4;
import it.reply.monitoringpillar.domain.exception.MonitoringException;
import it.reply.monitoringpillar.domain.exception.NotFoundMonitoringException;

@Stateless
public class GroupCheck extends MonitoringAdapteeZabbix {

	@EJB
	private ZabbixAdapterClientSetter<?> zabAdapClientSetter;

	// Determines whether a group exists or not
	public boolean isGroupPresent(String zone, String serverType, String hostGroup, String host, String tag_service)
			throws MonitoringException {

		try {
			boolean groupFound = false;
			ArrayList<ZabbixHostGroupResponse> workgroups = zabAdapClientSetter.getHostGroupsService(zone, serverType,
					ZabbixMethods.HOSTGROUP.getzabbixMethod(), null, null, null);
			for (ZabbixHostGroupResponse group : workgroups) {
				if (group.getName().equalsIgnoreCase(hostGroup)) {
					groupFound = true;
					break;
				}
			}

			if (host != null && tag_service == null) {
				List<ZabbixMonitoredHostResponseV2_4> hosts = zabAdapClientSetter.getMonitoredHostsZABBIXV2_4(zone,
						serverType, ZabbixMethods.HOST.getzabbixMethod(), null, null, host, null);
				if (hosts.isEmpty())
					throw new MonitoringException("Wrong Parameters inserted or NO host avalaible for such parameters");

				for (ZabbixGroup group : hosts.get(0).getGroups()) {
					if (group.getName().equalsIgnoreCase(hostGroup)) {
						groupFound = true;
						break;
					}
				}
				if (!groupFound)
					throw new MonitoringException("Host Selected does not belong to selected group");
			} else if (tag_service != null) {
				List<ZabbixMonitoredHostResponseV2_4> hosts = zabAdapClientSetter.getMonitoredHostsZABBIXV2_4(zone,
						serverType, ZabbixMethods.HOST.getzabbixMethod(), null, null, null, tag_service);
				if (hosts.isEmpty())
					throw new MonitoringException(
							"Wrong Parameters inserted or any host avalaible for such parameters");

				if (!hosts.get(0).getGroups().get(0).getName().equalsIgnoreCase(hostGroup))
					throw new MonitoringException("Host Selected does not belong to selected group");
			}

			if (groupFound)
				return true;

			else
				throw new NotFoundMonitoringException(ZabbixConstant.WRONGGROUPNAME + serverType + ": " + hostGroup);

		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

}