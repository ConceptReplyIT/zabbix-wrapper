package it.reply.monitoringpillar.utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.DateFromParamRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.DateToParamRequest;

public class TimestampMonitoring {

    public static String decodUnixTime2Date(long timestamp) throws IllegalArgumentException {

	SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	java.util.Date timeNew = new Date();
	timeNew = new java.util.Date((long) timestamp * 1000);
	return sdfDate.format(timeNew);
    }

    public static long encodeDate2UnitTime(String time) throws IllegalArgumentException {
	// public static void main(String [] args) {

	// Date date = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse(time);
	// return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);

	DateFormat dfm = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	// String time = "2015-03-21 12:00:00";
	dfm.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));

	long unixtime = 0;

	try {
	    unixtime = dfm.parse(time).getTime();
	    unixtime = unixtime / 1000;
	    // System.out.println(unixtime);
	    // System.out.println(TimeZone.getTimeZone("UTC"));
	} catch (ParseException e) {
	    e.printStackTrace();
	    throw new IllegalArgumentException("Impossible to parser the time");
	}
	return unixtime;
    }

    public static Long getDateFromFormatter(DateFromParamRequest dateFrom)
    // String hostuuid, FilterTimeRequest filterTimeRequest,
    // ZabbixItemResponse historyResult, ZabbixItemResponse trendResult,
    // Map<List<String>, List<Float>> usefulTimesValuesMap)
	    throws IllegalArgumentException {
	// FilterTimeRequest dateFrom = new FilterTimeRequest();

	Long dateFromFormatted = TimestampMonitoring.encodeDate2UnitTime(

	dateFrom.getDay() + "-" + dateFrom.getMonth() + "-" + dateFrom.getYear() + " " +

	dateFrom.getTime().getHh() + ":" + dateFrom.getTime().getMm() + ":" + dateFrom.getTime().getSs()

	);
	// boolean sinceStart = dateFrom.getStartTime();
	return dateFromFormatted;

    }

    public static Long getDateToFormatter(DateToParamRequest dateToParamRequest)
    // String hostuuid, FilterTimeRequest filterTimeRequest,
    // ZabbixItemResponse historyResult, ZabbixItemResponse trendResult,
    // Map<List<String>, List<Float>> usefulTimesValuesMap)
	    throws IllegalArgumentException {

	Long dateToFormatted = TimestampMonitoring.encodeDate2UnitTime(

	dateToParamRequest.getDay() + "-" + dateToParamRequest.getMonth() + "-" + dateToParamRequest.getYear() + " " +

	dateToParamRequest.getTime().getHh() + ":" + dateToParamRequest.getTime().getMm() + ":"
		+ dateToParamRequest.getTime().getSs());

	// boolean uptoNow = dateToParamRequest.getUntilStopTime();
	// boolean untilStopTime = dateToParamRequest.getUntilStopTime();
	return dateToFormatted;
    }
}