package com.easyiot.lora.device.api.dto;

import org.osgi.dto.DTO;

import com.easyiot.lora.device.api.capability.LoraDeviceCapability.RequireLoraDevice;

@RequireLoraDevice(versionStr = "1.0.0")
public class SensorDataDTO extends DTO{
	public long lon;
	public long lat;
	public int temp;
	public MetaDataDTO metadata;
}
