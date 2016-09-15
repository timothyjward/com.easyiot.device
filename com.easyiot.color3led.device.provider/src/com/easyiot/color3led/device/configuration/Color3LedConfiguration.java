package com.easyiot.color3led.device.configuration;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.easyiot.gpio.protocol.api.PinNumberEnum;

@ObjectClassDefinition(name = "3 color led device configuration")
public @interface Color3LedConfiguration {
	/**
	 * Id of the device instance
	 */
	@AttributeDefinition(name = "Instance ID", description = "3 color led device ", required = true)
	public String id() default "three.color.led";
	
	@AttributeDefinition(name = "Blue pin", description = "Output pin connected to the blue led.")
	PinNumberEnum bluePin() default PinNumberEnum.pin23;
	
	@AttributeDefinition(name = "Green pin", description = "Output pin connected to the green led.")
	PinNumberEnum greenPin() default PinNumberEnum.pin24;
	
	@AttributeDefinition(name = "Red pin", description = "Output pin connected to the red led.")
	PinNumberEnum redPin() default PinNumberEnum.pin25;

}
