package com.easyiot.bluetooth.device.provider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import com.easyiot.base.api.Device;
import com.easyiot.bluetooth.device.api.dto.SensorData;
import com.easyiot.bluetooth.device.configuration.BluetoothDeviceConfiguration;
import com.easyiot.bluetooth.protocol.api.BluetoothProtocol;

/**
 * A sample implementation of a Bluetooth device.
 */

@Designate(ocd = BluetoothDeviceConfiguration.class, factory = true)
@Component(name = "com.easyiot.bluetooth.device", configurationPolicy = ConfigurationPolicy.REQUIRE)
public class BluetoothDeviceImpl implements Device {
	private BluetoothDeviceConfiguration deviceConfiguration;

	@Reference(name = "bluetoothProtocolReference")
	BluetoothProtocol bluetoothPotocol;

	@PostMethod
	public String sendDataThroughSPP(SensorData msg) {
		bluetoothPotocol.sendDataThroughSPP(deviceConfiguration.sppServiceNumber(), msg.message);
		return "Success";
	}

	@Activate
	public void activate(BluetoothDeviceConfiguration config) {
		this.deviceConfiguration = config;
	}

	@Override
	public String getId() {
		return deviceConfiguration.id();
	}

}
