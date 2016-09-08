package com.easyiot.bluetooth.device.configuration;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Bluetooth enabled device configuration")
public @interface BluetoothDeviceConfiguration {

	/**
	 * Id of the device instance
	 */
	@AttributeDefinition(name = "Instance ID", description = "Bluetooth device instance ID", required = true)
	public String id() default "bluetooth.device.1";
	
	@AttributeDefinition(name = "SPP Service Id", description = "Gives the SPP service number for this device.", required = true)
	String sppServiceNumber() default "";

}
