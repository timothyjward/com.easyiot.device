package com.easyiot.LT100H.device.api.dto;

public interface LT100HSensorDataConstants {
	// device type constants
	public static final String DEVICE_TYPE_LT_100 = "2";

	// Report constants
	public static final String PERIODIC_REPORT = "2";
	public static final String MOTION_MODE_STATIC_REPORT = "4";
	public static final String MOTION_MODE_MOVING_REPORT = "5";
	public static final String MOTION_MODE_STATIC_TO_MOVING_REPORT = "6";
	public static final String MOTION_MODE_MOVING_TO_STATIC_REPORT = "7";
	public static final String HELP_REPORT = "I";
	public static final String LOW_BATTERY_ALARM_REPORT = "J";
	public static final String POWER_ON_REPORT_TEMPERATURE = "c";
	public static final String POWER_OFF_REPORT_BATTERY_LOW = "h";
	public static final String POWER_OFF_REPORT_TEMPERATURE = "i";

	// Report constants
	public static final String NOT_FIXED = "1";
	public static final String TWO_D_FIXED = "2";
	public static final String THREE_D_FIXED = "3";
}
