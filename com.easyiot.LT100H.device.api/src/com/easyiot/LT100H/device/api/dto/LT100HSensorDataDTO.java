package com.easyiot.LT100H.device.api.dto;

import org.osgi.dto.DTO;

import com.easyiot.LT100H.device.api.capability.LT100HDeviceCapability.RequireLT100HDevice;
import com.easyiot.LT100H.device.api.dto.LT100HMetaDataDTO;
import com.easyiot.LT100H.device.api.dto.LT100HSensorDataConstants;

@RequireLT100HDevice(versionStr="1.0.0")
public class LT100HSensorDataDTO extends DTO {

	// Should be one of device type constants
	public String deviceType = LT100HSensorDataConstants.DEVICE_TYPE_LT_100;

	// Should be one of Report constants
	public String reportType = LT100HSensorDataConstants.PERIODIC_REPORT;

	// Should be one of gps fix satus constants
	public String gpsFixStatus = LT100HSensorDataConstants.NOT_FIXED;

	// in (E or W)dddmm.mmmm format
	public String longitude = "E14456.4799";

	// in (N or S)ddmm.mmmm
	public String latitude = "S3750.3815";

	// Percent capacity
	public String batteryCapacity = "1";
	
	// Metadata Dto
	public LT100HMetaDataDTO metadata;

}
