package it.prisma.domain.dsl.monitoring;


import java.util.ArrayList;
import java.util.List;


/**
 * 
 * This Enum represents the possible values of {type} in the Web Service Path
 *
 */
public enum InfoType {

	INFRASTRUCTURE("infrastructure"), SERVICE("service"), WATCHER("watcher"), PAAS("paas"), ALL("all");

	private String infoType;

	private InfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getInfoType() {
		return this.infoType;
	}

	public String toString() {
		return this.infoType;
	}

	public static List<String> getAllType() {
		List<String> types = new ArrayList<String>();
		for (InfoType a : values()) {
			types.add(a.getInfoType());
		}
		return types;
	}

	public static InfoType lookupFromName(String types) throws IllegalArgumentException {
		for (InfoType s : values()) {
			if (types.equals(s.getInfoType())) {
				return s;
			}
		}
		throw new IllegalArgumentException("Cannot find [" + types + "] into InfoType enum");
	}
}
