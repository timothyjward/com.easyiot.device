package com.easyiot.auslora.device.api.dto;

import org.osgi.dto.DTO;

import com.easyiot.auslora.device.api.capability.AusloraDeviceCapability.RequireAusloraDevice;

@RequireAusloraDevice(versionStr="1.0.0")
public class SensorDataDTO extends DTO {

	// Should be one of device type constants
	public String deviceType = SensorDataConstants.DEVICE_TYPE_LT_100;

	// Should be one of Report constants
	public String reportType = SensorDataConstants.PERIODIC_REPORT;

	// Should be one of gps fix satus constants
	public String gpsFixStatus = SensorDataConstants.NOT_FIXED;

	// in (E or W)dddmm.mmmm format
	public String longitude = "E14456.4799";

	// in (N or S)ddmm.mmmm
	public String latitude = "S3750.3815";

	// Percent capacity
	public String batteryCapacity = "1";
	
	// Metadata Dto
	public MetaDataDTO metadata;

}
