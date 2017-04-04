package com.cimpoint.mes.common;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.cimpoint.mes.common.entities.EUnit;
import com.cimpoint.mes.common.entities.EWorkCenter;
import com.cimpoint.mes.common.entities.EWorkOrderItem;
import com.cimpoint.mes.common.entities.MESTrxInfo;

public class TestUtils {

	public static String arrayToCommaSlicedString(String[] array) {
		String str = "";
		for (String roleName : array) {
			str += roleName + ",";
		}
		if (str.length() > 0 && str.lastIndexOf(",") != -1) {
			str = str.substring(0, str.length() - 1);
		}

		return str;
	}

	public static MESTrxInfo newTrxInfo() {
		MESTrxInfo trxInfo = new MESTrxInfo();
		trxInfo.setTime(new Date());
		trxInfo.setTimeZoneId(getTimeZone());
		return trxInfo;
	}

	public static MESTrxInfo newTrxInfo(String comment) {
		MESTrxInfo trxInfo = new MESTrxInfo();
		trxInfo.setComment(comment);
		trxInfo.setTime(new Date());
		trxInfo.setTimeZoneId(getTimeZone());
		return trxInfo;
	}
	
	public static Set<EWorkCenter> newHashSet(EWorkCenter... workCenters) {
		Set<EWorkCenter> set = new HashSet<EWorkCenter>();
		if (workCenters !=null) {
			for (EWorkCenter wc: workCenters) {
				set.add(wc);
			}
		}
		return set;
	}
		
	public static Set<EWorkOrderItem> newHashSet(EWorkOrderItem... workOrderItems) {
		Set<EWorkOrderItem> set = new HashSet<EWorkOrderItem>();
		if (workOrderItems !=null) {
			for (EWorkOrderItem wc: workOrderItems) {
				set.add(wc);
			}
		}
		return set;
	}

	public static Set<EUnit> newHashSet(EUnit... units) {
		Set<EUnit> set = new HashSet<EUnit>();
		if (units !=null) {
			for (EUnit unit: units) {
				set.add(unit);
			}
		}
		return set;
	}
	
	private static String getTimeZone() {
		final Date now = new Date();
		//final String timezone = DateTimeFormat.getFormat("z").format(now); 
		final String timezone = "GMT-07:00"; //TODO fix me
		return timezone;
	}
}
