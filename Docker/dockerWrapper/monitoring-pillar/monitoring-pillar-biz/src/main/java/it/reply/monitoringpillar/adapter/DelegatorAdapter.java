package it.reply.monitoringpillar.adapter;

import it.reply.monitoringpillar.adapter.nagios.IMonitAdaptNagios;
import it.reply.monitoringpillar.adapter.nagios.MonitoringAdapteeNagios;
import it.reply.monitoringpillar.adapter.zabbix.IMonitAdaptZabbix;
import it.reply.monitoringpillar.domain.dsl.monitoring.Adapter;
import it.reply.monitoringpillar.domain.exception.IllegalArgumentMonitoringException;
import it.reply.monitoringpillar.domain.exception.NotFoundMonitoringException;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DelegatorAdapter {

    @Inject
    @IMonitAdaptZabbix
    MonitoringTarget monitAdaptZabbix;

    @Inject
    @IMonitAdaptNagios
    MonitoringTarget monitAdaptNagios;

    @Inject
    MonitoringAdapteeZabbix adapteeZabbix;

    @Inject
    MonitoringAdapteeNagios adapteeNagios;

    public MonitoringTarget getAdapter(String adapter) throws IllegalArgumentMonitoringException,
	    NotFoundMonitoringException {
	try {
	    Adapter a = Adapter.findByName(adapter);
	    switch (a) {
	    case NAGIOS:
		return monitAdaptNagios;
	    case ZABBIX:
		return monitAdaptZabbix;
	    default:
		throw new IllegalArgumentMonitoringException("Cannot find implemetation for [" + adapter + "] into "
			+ Adapter.class.getCanonicalName());
	    }
	} catch (IllegalArgumentException e) {
	    throw new NotFoundMonitoringException("Cannot find [" + adapter + "] adapter", e);
	}
    }
    // public MonitoringAdapteeZabbix getAdapteeZabbix(String adapter) throws
    // NotFoundMonitoringException {
    //
    // if (adapter.equals(Adapter.ZABBIX.toString().toLowerCase())) {
    // return adapteeZabbix;
    // } else {
    // throw new
    // NotFoundMonitoringException("Just Zabbix Adapter Allowed for this method");
    // }
    // }
    //
    // public MonitoringAdapteeNagios getAdapteeNagios(String adapter) throws
    // NotFoundMonitoringException {
    // if (adapter.equals(Adapter.NAGIOS.toString().toLowerCase())) {
    // return adapteeNagios;
    // } else {
    // throw new
    // NotFoundMonitoringException("Just Nagios Adapter Allowed for this method");
    // }
    // }

}