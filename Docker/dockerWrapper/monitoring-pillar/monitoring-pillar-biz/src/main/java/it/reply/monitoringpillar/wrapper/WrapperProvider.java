package it.reply.monitoringpillar.wrapper;

import javax.enterprise.inject.Model;

import it.reply.monitoringpillar.domain.exception.MonitoringException;

//@Stateless
//@Named
@Model
public interface WrapperProvider<T> {

	/**
	 * It manages and returns the most generic wrapper
	 * 
	 * @param testbed
	 * @param adapterType
	 * @param serverType
	 * @param group
	 * @return the most generic wrapped response for generic requests
	 * @throws MonitoringException
	 */
	public T getWrapperIaaSPaaS(String adapterType, String zone, String serverType, String group, String host)
			throws MonitoringException;

	/**
	 * It manages and returns a specific host related wrapper
	 */

	/**
	 * 
	 * @param adapterType
	 * @param serverType
	 * @param group
	 * @param host
	 * @param thresholds
	 * @param metrics
	 * @param serviceId
	 * @return T
	 * @throws MonitoringException
	 */
	public T getWrapperHostComb(String adapterType, String zone, String serverType, String group, String host,
			Boolean thresholds, Boolean metrics, String serviceId) throws MonitoringException;

}
