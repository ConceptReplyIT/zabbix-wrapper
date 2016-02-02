package it.monitoringpillar.config;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigPillar {

	// public static void writeToLogPillarFile(String message, String date) {
	//
	// Properties prop = new Properties();
	// OutputStream output = null;
	//
	// try {
	//
	// output = new FileOutputStream("logPillar.properties");
	//
	// // set the properties value
	// prop.setProperty("message", message);
	// prop.setProperty("time", date);
	//
	// // save properties to project root folder
	// prop.store(output, null);
	//
	// } catch (IOException io) {
	// io.printStackTrace();
	// } finally {
	// if (output != null) {
	// try {
	// output.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// }
	// }
	// }
	public static void writeToLogPillarFile(String message, String date) throws IOException {
		Logger log = LogManager.getLogger(ConfigPillar.class.getName());

		log.info(message, date);
	}
}
