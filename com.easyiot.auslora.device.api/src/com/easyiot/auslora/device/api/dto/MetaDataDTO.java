package com.easyiot.auslora.device.api.dto;

import org.osgi.dto.DTO;

public class MetaDataDTO extends DTO {
	// comma separated string
	public String gateway_eui;
	public double calculatedAltitude = 0;
	public double calculatedLongitude = 144.9633200;
	public double calculatedLatitude = -37.8140000;
	// name of the network sent the last data
	public String networkName = "";
	// has the network change since the last data reception
	public boolean isRoaming = false;
	public long timeStamp = 0L;
	public double averageReceptionPeriod = 0.0;
}
