package it.prisma.utils;

import it.prisma.domain.dsl.monitoring.businesslayer.paas.request.HostMonitoringDeleteRequest;
import it.prisma.domain.dsl.monitoring.businesslayer.paas.request.HostMonitoringRequest;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.iaas.HostAffected;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.iaas.IaasGroupOfMachine;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.iaas.MonitoringWrappedResponseIaas;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.iaas.TriggerShot;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.iaas.WrappedIaasHealthByTrigger;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.Group;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaas;
import it.prisma.utils.web.ws.rest.apiclients.prisma.PrismaMonitoringAPIClient;
import it.prisma.utils.web.ws.rest.apiclients.prisma.bizlayer.PrismaBizAPIClient;
import it.prisma.utils.web.ws.rest.apiencoding.MappingException;
import it.prisma.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.prisma.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.prisma.utils.web.ws.rest.restclient.exceptions.RestClientException;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.NotFoundException;

//TODO: Gestire la request secondo refactoring
public class Test {

	private static PrismaMonitoringAPIClient client;
	private static PrismaBizAPIClient bizClient;

	public static void main(String[] args) throws NoMappingModelFoundException {

		// http://90.147.102.24
		// client = new
		// PrismaMonitoringAPIClient("http://90.147.102.24:8080/monitoring/adapters/");
		client = new PrismaMonitoringAPIClient("http://localhost:8082/monitoring/adapters/");

		try {
			HostMonitoringRequest request = new HostMonitoringRequest();
			request.setHostGroup("Workgroup-1");
			request.setUuid("hostTEST");
			request.setIp("192.168.0.0");
			request.setServiceCategory("DBaaS");
			List<String> services = new ArrayList<String>();
			services.add("apache");
			request.setAtomicServices(services);
			request.setServiceId("790");
			client.addHostToMonitoring("zabbix", "Workgroup-1", "hostTEST", "uuid", request);

			System.out.println("OK: host created");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			HostMonitoringDeleteRequest request = new HostMonitoringDeleteRequest();
			request.setUuid("hostTEST");
			request.setGroup("Workgroup-1");
			client.deleteHostFromMonitoring("zabbix", "Workgroup-TEST", "hostTEST", request);
			System.out.println("host deleted");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			client.createGroup("zabbix", "Workgroup-TEST");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			client.deleteGroup("zabbix", "Workgroup-TEST");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TEST Trigger By GROUP
		try {
			WrappedIaasHealthByTrigger wrappedIaasHealthByTrigger = client.getShotTriggerByHostGroup("zabbix",
					"InfrastructureHealth");
			System.out.println("WrappedIaasHealthByTrigger:");
			for (HostAffected hostAffected : wrappedIaasHealthByTrigger.getHostAffecteds()) {
				System.out.println(hostAffected.getHostName());
				for (TriggerShot triggerShot : hostAffected.getTriggerShots()) {
					System.out.println("  " + triggerShot.getDescription());
					System.out.println("  " + triggerShot.getTime());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		// TEST ServiceTag by ID
		try {
			MonitoringWrappedResponsePaas monitoringWrappedResponsePaas = client.getMonitoringServiceByTag("zabbix",
					"workgroup-1", "458");
			for (Group cat : monitoringWrappedResponsePaas.getGroups()) {
				System.out.println(cat.getGroupName());

			}
		} catch (RestClientException | NoMappingModelFoundException | MappingException | ServerErrorResponseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// TEST GET GROUP INFO
		try {
			MonitoringWrappedResponseIaas monitoringWrappedResponseIaas = client.getMachineByHostGroup("zabbix",
					"Cloudify_Mangement_Mach");
			for (IaasGroupOfMachine cat : monitoringWrappedResponseIaas.getIaasMachineGroups()) {
				System.out.println(cat.getIaasMachines());

			}
		} catch (RestClientException | NotFoundException | MappingException | ServerErrorResponseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// TEST GET HOSTS
		try {
			MonitoringWrappedResponseIaas monitoringWrappedResponseIaas = client.getItemsByHostGroupAndHost("zabbix",
					"Cloudify_Mangement_Mach", "pr01mail01.infn.ponsmartcities-prisma.it");
			for (IaasGroupOfMachine cat : monitoringWrappedResponseIaas.getIaasMachineGroups()) {
				System.out.println(cat.getIaasMachines().get(0));

			}
		} catch (RestClientException | NotFoundException | MappingException | ServerErrorResponseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}