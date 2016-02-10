package it.monitoringpillar.wrapper.zabbix;

import it.monitoringpillar.adapter.DelegatorAdapter;
import it.monitoringpillar.config.Configuration;
import it.monitoringpillar.exception.MonitoringException;
import it.monitoringpillar.exception.NotImplementedMonitoringException;
import it.monitoringpillar.wrapper.WrapperProvider;
import it.prisma.domain.dsl.monitoring.InfoType;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
// @Local(WrapperProviderZabbix.class)
public class WrapperProviderZabbix<T> implements WrapperProvider<T>, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	DelegatorAdapter determineAdapt;

	@EJB
	Configuration config;

	@SuppressWarnings({ "unchecked" })
	@Override
	public T getWrapperIaaSPaaS(String adapterType, String serverType, String group, String host)
			throws MonitoringException {

		return (T) determineAdapt.getAdapter(adapterType).getInfoWrapperGeneric(serverType, group, host, null, null,
				null, null, null, null, null);
	}

	@Override
	public T getWrapperHostComb(String adapterType, String serverType, String group, Boolean thresholds,
			String serviceId) throws MonitoringException {
		// Get all hosts info belonging to a group
		if ((thresholds == null || !thresholds) && serviceId == null) {
			return (T) determineAdapt.getAdapter(adapterType).getHostsInfoWrapped(serverType, group);
		} else if (thresholds != null && thresholds && serviceId == null) {
			return (T) determineAdapt.getAdapter(adapterType).getTriggerByGroup(serverType, group);

		} else if ((thresholds == null || !thresholds) && serviceId != null) {
			if (serverType.equalsIgnoreCase(InfoType.INFRASTRUCTURE.getInfoType())
					&& config.isDistributedArchitectureImplemented()) {
				throw new NotImplementedMonitoringException(
						"Error: Not Allowed for Infrastructure type, just Paas Platform type");
			} else
				return (T) determineAdapt.getAdapter(adapterType).getInfoWrapperGeneric(serverType, group, null, null,
						serviceId, null, null, null, null, null);
		} else {
			throw new NotImplementedMonitoringException("Error: functionality not implemented");
		}
	}
}