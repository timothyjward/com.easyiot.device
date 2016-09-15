package com.easyiot.color3led.device.api.capability;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.easyiot.base.capability.EasyiotNamespace;

import aQute.bnd.annotation.headers.ProvideCapability;
import aQute.bnd.annotation.headers.RequireCapability;

public interface Color3LedCapability {
	public static final String COLOR_3_LED_DEVICE = "color_3_led_device";

	@ProvideCapability(ns = EasyiotNamespace.NS, name = COLOR_3_LED_DEVICE, version = "1.0.0")
	@Retention(RetentionPolicy.CLASS)
	public @interface ProvideColor3LedDevice_v1_0_0 {

	}

	@RequireCapability(ns = EasyiotNamespace.NS, filter = "(&(" + EasyiotNamespace.NS + "=" + COLOR_3_LED_DEVICE
			+ ")${frange;${versionStr}})")
	@Retention(RetentionPolicy.CLASS)
	public @interface RequireColor3LedDevice {
		/**
		 * Version of the required 3 color led bundle
		 * 
		 * @return
		 */
		String versionStr() default "1.0.0";
	}
}
