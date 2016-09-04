package com.easyiot.lora.device.api.dto;

import org.osgi.dto.DTO;

import com.easyiot.lora.device.api.capability.LoraDeviceCapability.RequireLoraDevice;

@RequireLoraDevice(versionStr="1.0.0")
public class MetaDataDTO extends DTO {
	public Double frequency;
	public String datarate;
	public String codingrate;
	public int gateway_timestamp;
	public String gateway_time;
	public int channel;
	public String server_time;
	public int rssi;
	public int lsnr;
	public int rfchain;
	public int crc;
	public String modulation;
	public String gateway_eui;
	public double altitude;
	public double longitude = 144.9633200;
	public double latitude = -37.8140000;
}
