package it.reply.monitoringpillar.wrapper.zabbix;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.GroupsWrappedName;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaasGroups;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixHostGroupResponse;

@Stateless
public class ZabbixGroupBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 
     * @param testbedType
     * @param groups
     * @return
     */
    public MonitoringWrappedResponsePaasGroups getWrappedGroup(List<ZabbixHostGroupResponse> groups) {

	MonitoringWrappedResponsePaasGroups wrappedPaasGroup = new MonitoringWrappedResponsePaasGroups();

	// wrappedPaasGroup.setEnvironment(testbedType);

	List<GroupsWrappedName> groupResult = new ArrayList<>();

	for (ZabbixHostGroupResponse group : groups) {
	    GroupsWrappedName groupname = new GroupsWrappedName();
	    groupname.setGroupName(group.getName());
	    groupResult.add(groupname);

	}

	wrappedPaasGroup.setGroups(groupResult);

	return wrappedPaasGroup;
    }
}