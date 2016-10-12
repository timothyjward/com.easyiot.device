package com.easyiot.development.board1.device.provider.configuration;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "DevelopmentBoard1 device configuration")
public @interface DevelopmentBoard1Configuration {

	/**
	 * Id of the device instance
	 */
	@AttributeDefinition(name = "Instance ID", description = "Lora device instance ID", required = true)
	public String id() default "lora.device.1";
	
	// BELOW IS NEEDED FOR REGISTERING TO TTN
	@AttributeDefinition(name = "Subscription Channel", description = "Device subscription channel to read data. Follows mqtt syntax.")
	public String subscriptionChannel() default "70B3D57ED0000185/devices/0000000001020304/up";

	@AttributeDefinition(name = "Publish Channel", description = "Device publish channel to write data. Follows mqtt syntax.")
	public String publishChannel() default "70B3D57ED0000185/devices/0000000001020304/up";
	
	// BELOW IS NEEDED FOR REGISTERING TO AUSLORA 
	@AttributeDefinition(name = "Application ID", description = "Auslora application id that this device is connected to")
	public String applicationId() default "BE01000C";

	@AttributeDefinition(name = "Security Token", description = "Auslora application security token")
	public String securityToken() default "qCbvxPgM0cg0lKb80vkoug";
	
	@AttributeDefinition(name = "Device Identifier", description = "Device EUI identified by the manufacturer")
	public String deviceEUI() default "000DB531176F3557";

}
