#!/bin/sh

#IPADDR=$(ip a s | sed -ne '/127.0.0.1/!{s/^[ \t]*inet[ \t]*\([0-9.]\+\)\/.*$/\1/p}')

CLUSTER_MESSAGING_PASSWORD="pwd"
$JBOSS_HOME/bin/standalone.sh -c $JBOSS_CONF_FILE -Djboss.bind.address=0.0.0.0 -Djboss.bind.address.management=0.0.0.0