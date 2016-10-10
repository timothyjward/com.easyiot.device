package com.easyiot.auslora.device.provider.configuration;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "AusLora enabled device configuration")
public @interface AusloraSensorConfiguration {

	/**
	 * Id of the device instance
	 */
	@AttributeDefinition(name = "Instance ID", description = "Auslora device instance ID", required = true)
	public String id() default "auslora.device.1";

	// BELOW IS NEEDED FOR REGISTERING TO AUSLORA 
	@AttributeDefinition(name = "Application ID", description = "Auslora application id that this device is connected to", required = true)
	public String applicationId() default "BE01000C";

	@AttributeDefinition(name = "Security Token", description = "Auslora application security token", required = true)
	public String securityToken() default "qCbvxPgM0cg0lKb80vkoug";

	@AttributeDefinition(name = "Device Identifier", description = "Device EUI identified by the manufacturer")
	public String deviceEUI() default "000DB531176F3557";

	// BELOW IS NEEDED FOR REGISTERING TO TTN
	@AttributeDefinition(name = "Subscription Channel", description = "Device subscription channel to read data. Follows mqtt syntax.")
	public String subscriptionChannel() default "70B3D57ED0000185/devices/0000000001020304/up";
	
	@AttributeDefinition(name = "Publish Channel", description = "Device publish channel to write data. Follows mqtt syntax.")
	public String publishChannel() default "70B3D57ED0000185/devices/0000000001020304/up";

}
