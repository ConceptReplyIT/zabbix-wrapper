Monitoring Pillar
=================

This monitoring pillar has been written in order to expose zabbix RESTful API and to work as adapter for different monitoring products

1 INSTALLATION
===============

1.1 REQUISITES
--------------
This project has been created with Maven 3.0.4 and Java EE 6.
Maven will take care of downloading the extra dependencies needed for the project and also compile and deploy the project itself.

1.2 INSTALLATION
--------------

### Compile the code

To compile the **monitoring-pillar** project you need to be in the same folder as the `pom.xml` file and type:
```
mvn clean install -DskipTests
```
This command compiles the code and skip the tests. If you want to compile the code running the tests too you can use:
```
mvn clean install
```

Or, if you are using and IDE like Eclipse:
* Run as -> Maven Build... ;
    * Set `Goals`: `clean install`;
    
### Deploy the project

To deploy the project on a **local** JBoss application server you need to use the profile `deploy-local`, for example adding `-P deploy-local` to the Maven command explained in the [Compile the code]() section.

**Note** that this will *only deploy* the web application, but *not* start the server. The server **must** be already running for the deployment to work!
You can run the server (even in *debug* mode) by other means (i.e. using the Eclipse server configurations).
The deployment will be automatically loaded inside a `standalone\data\content` temporary folder, in the application server installation directory.

From Eclipse: follow the [Compile the code]() section and add the `deploy-local` profile separated with a comma if another is already set (i.e. `profile1,deploy-local`).

##### Undeploy the project

It is also available a profile `undeploy-local` that during the **clean** phase will remove the web application from the local AS.
In this case the Maven command would be:
```
mvn clean -P undeploy-local
```

Otherwise, you could connect to the JBoss Management interface and undeploy it manually.

### Environment-dependent configurations

To build the project with included environment-dependent configurations, you need to add the following options to the Maven command explained in the [Compile the code]() section: `-P build-environment-dependent -Denvironment.name=<envName> -Denvironment.groupId=<environment.groupId>`.

Or from Eclipse:
* Run as -> Maven Build... ;
    * Set `Goals`: `clean install`;
    * then `Profile`: `build-environment-dependent` (remember to also add `deploy-local` to deploy to the local AS);
    * then `Add Parameter`: with name `environment.name`;
    * finally `Add Parameter`: with name `environment.groupId`.
    
NOTE that `environment.groupId` is used to dynamically link at compile-time a `monitoring-pillar-environments-config` project, containing the environent properties to inject. 

The **CLEAN** phase will unpack the environment-dependent configuration files from the `monitoring-pillar-environments-config` and the following phases will replace the properties in the `monitoring-pillar-biz` project.


Copyright and license
----------------------
*Copyright (c) 2014 Concept Reply (A business unit of Santer Reply S.p.A.)*

Licensed under the Apache License, Version 2.0 (the "License");<br/>
you may not use this file except in compliance with the License.<br/>
You may obtain a copy of the License at 

       http://www.apache.org/licenses/LICENSE-2.0
	   
Unless required by applicable law or agreed to in writing, software<br/>
distributed under the License is distributed on an "AS IS" BASIS,<br/>
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.<br/>
See the License for the specific language governing permissions and<br/>
limitations under the License.