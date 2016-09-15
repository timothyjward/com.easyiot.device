package com.easyiot.color3led.device.api.dto;

import org.osgi.dto.DTO;

import com.easyiot.color3led.device.api.capability.Color3LedCapability.RequireColor3LedDevice;

@RequireColor3LedDevice
public class ColorDto extends DTO {
	public static String LOW_VALUE = "LOW";
	public static String HIGH_VALUE = "HIGH";

	public String blueValue = LOW_VALUE;
	public String greenValue = LOW_VALUE;
	public String redValue = LOW_VALUE;
}
