package com.easyiot.lora.device.provider.configuration;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Lora enabled Device Configuration")
public @interface LoraSensorConfiguration {

	/**
	 * Id of the device instance
	 */
	@AttributeDefinition(name = "Instance ID", description = "MQTT protocol instance ID", required = true)
	public String id() default "lora.device.1";
	
	@AttributeDefinition(name = "Device Name", description = "Provides the name for this device")
	public String name() default "Lora Device";

	@AttributeDefinition(name = "Subscription Channel", description = "Device subscription channel to read data. Follows mqtt syntax.")
	public String subscriptionChannel() default "70B3D57ED0000185/devices/0000000001020304/up";

	@AttributeDefinition(name = "Publish Channel", description = "Device publish channel to write data. Follows mqtt syntax.")
	public String publishChannel() default "70B3D57ED0000185/devices/0000000001020304/up";

}
