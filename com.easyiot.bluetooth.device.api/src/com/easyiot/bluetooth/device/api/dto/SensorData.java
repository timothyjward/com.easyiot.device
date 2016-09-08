package com.easyiot.bluetooth.device.api.dto;

import org.osgi.dto.DTO;

import com.easyiot.bluetooth.device.api.capability.BluetoothDeviceCapability.ProvideBluetoothDevice_v1_0_0;

/**
 * Input data to be send to sensor
 * 
 * @author daghan
 *
 */
@ProvideBluetoothDevice_v1_0_0
public class SensorData extends DTO {
	public String message = "default message";
}
