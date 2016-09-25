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

	@AttributeDefinition(name = "Application ID", description = "Auslora application id that this device is connected to", required = true)
	public String applicationId() default "BE01000C";

	@AttributeDefinition(name = "Security Token", description = "Auslora application security token", required = true)
	public String securityToken() default "qCbvxPgM0cg0lKb80vkoug";

}
