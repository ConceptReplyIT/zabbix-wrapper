package it.monitoringpillar;

import javax.interceptor.AroundConstruct;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingInterceptor {

    private static final Logger LOG = LogManager.getLogger();

    @AroundConstruct
    private void init(InvocationContext ic) throws Exception {
	LOG.info("Entering constructor: " + ic.getClass().getName());
	try {
	    ic.proceed();
	} finally {
	    LOG.info("Exiting constructor: " + ic.getClass().getName());
	}
    }

    @AroundInvoke
    public Object logMethod(InvocationContext ic) throws Exception {
	LOG.info("Entering method: " + ic.getMethod().toGenericString());
	try {
	    return ic.proceed();
	} finally {
	    LOG.info("Exiting method: " + ic.getMethod().toGenericString());
	}
    }
}
