package it.monitoringpillar.adapter.zabbix.handler;

import it.monitoringpillar.adapter.zabbix.clientbuilder.ZabbixAdapterClientSetter;
import it.monitoringpillar.adapter.zabbix.exception.NotFoundZabbixException;
import it.monitoringpillar.adapter.zabbix.exception.ZabbixException;
import it.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ZabbixMethods;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostResponseV2_4;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostsResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HostIDByName implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String ERROR_Msg = "Wrong host, group, tag inserted or not existing in monitoring platform";

	@Inject
	private ZabbixAdapterClientSetter<?> zabAdapSetter;

	public String getHostID(String serverType, String hostgroupName, String vmuuid,
			ArrayList<ZabbixMonitoredHostsResponse> hosts) throws ZabbixException {

		if (hosts == null || vmuuid == null) {
			ArrayList<ZabbixMonitoredHostsResponse> hostinfo = new ArrayList<ZabbixMonitoredHostsResponse>();

			// isThereAnyHostWithoutInventory(serverType);

			hostinfo = (ArrayList<ZabbixMonitoredHostsResponse>) zabAdapSetter.getHostsService(serverType,
					ZabbixMethods.HOST.getzabbixMethod(), null, null, null, null);
			for (int i = 0; i < hostinfo.size(); i++) {
				String hostIdfromPillar = hostinfo.get(i).getHostid();
				String hostname = hostinfo.get(i).getName();
				if (vmuuid.equalsIgnoreCase(hostname)) {
					return hostIdfromPillar;
				}
			}
			throw new NotFoundZabbixException(ERROR_Msg);
		} else {
			for (int i = 0; i < hosts.size(); i++) {
				String hostIdfromPillar = hosts.get(i).getHostid();
				String hostname = hosts.get(i).getName();
				if (vmuuid.equalsIgnoreCase(hostname)) {
					return hostIdfromPillar;
				}
			}
			throw new NotFoundZabbixException(ERROR_Msg);
		}
	}

	// private void isThereAnyHostWithoutInventory(String serverType) throws
	// ZabbixException {
	//
	// ArrayList<ZabbixMonitoredHostsResponse> hosts =
	// zabAdapSetter.getHostsWithInventoryService(serverType,
	// ZabbixMethods.HOST.getzabbixMethod());
	//
	// for (ZabbixMonitoredHostsResponse host : hosts) {
	// zabAdapSetter.updateHostTagInventory(serverType,
	// ZabbixMethods.HOSTUPDATE.getzabbixMethod());
	// host.getHostid();
	// }
	// }

	public String getHostIDV2_4(String serverType, String hostgroupName, String vmuuid, String tag_service,
			List<ZabbixMonitoredHostResponseV2_4> hosts) throws ZabbixException {

		if (hosts == null || vmuuid == null) {
			ArrayList<ZabbixMonitoredHostsResponse> hostinfo = new ArrayList<ZabbixMonitoredHostsResponse>();

			hostinfo = (ArrayList<ZabbixMonitoredHostsResponse>) zabAdapSetter.getHostsService(serverType,
					ZabbixMethods.HOST.getzabbixMethod(), null, null, null, null);

			for (int i = 0; i < hostinfo.size(); i++) {
				String hostIdfromPillar = hostinfo.get(i).getHostid();
				String hostname = hostinfo.get(i).getName();
				if (vmuuid.equalsIgnoreCase(hostname)) {
					return hostIdfromPillar;
				}
			}
			throw new NotFoundZabbixException(ERROR_Msg);
		} else {
			for (int i = 0; i < hosts.size(); i++) {
				String hostIdfromPillar = hosts.get(i).getHostid();
				String hostname = hosts.get(i).getName();
				if (vmuuid.equalsIgnoreCase(hostname)) {
					return hostIdfromPillar;
				}
			}
			throw new NotFoundZabbixException(ERROR_Msg);
		}
	}

}