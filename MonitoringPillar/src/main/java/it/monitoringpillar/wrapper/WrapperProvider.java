package it.monitoringpillar.wrapper;

import it.monitoringpillar.exception.MonitoringException;

//@Stateless
//@Named
public interface WrapperProvider<T> {

	public T getWrapperIaaSPaaS(String testbed, String adapterType, String serverType, String group)
			throws MonitoringException;

	public T getWrapperHostComb(String testbed, String adapterType, String serverType, Boolean thresholds,
			String serviceId) throws MonitoringException;

}
