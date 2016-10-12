package com.easyiot.development.board1.device.api.dto;

import org.osgi.dto.DTO;

import com.easyiot.development.board1.device.capability.DevelopmentBoard1DeviceCapability.RequireDevelopmentBoard1Device;
import com.easyiot.development.board1.device.api.dto.DevelopmentBoard1MetaDataDTO;

@RequireDevelopmentBoard1Device(versionStr = "1.0.0")
public class DevelopmentBoard1DeviceDataDTO extends DTO{
	public long lon;
	public long lat;
	public int temp;
	public DevelopmentBoard1MetaDataDTO metadata;
}
