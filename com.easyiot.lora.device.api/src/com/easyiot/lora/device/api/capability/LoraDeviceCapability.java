package com.easyiot.lora.device.api.capability;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.easyiot.base.capability.EasyiotNamespace;

import aQute.bnd.annotation.headers.ProvideCapability;
import aQute.bnd.annotation.headers.RequireCapability;

public interface LoraDeviceCapability {
	public static final String LORA_DEVICE = "lora_device";

	@ProvideCapability(ns = EasyiotNamespace.NS, name = LORA_DEVICE, version = "1.0.0")
	@Retention(RetentionPolicy.CLASS)
	public @interface ProvideLoraDevice_v1_0_0 {

	}

	@RequireCapability(ns = EasyiotNamespace.NS, filter = "(&(" + EasyiotNamespace.NS + "=" + LORA_DEVICE
			+ ")${frange;${versionStr}})")
	@Retention(RetentionPolicy.CLASS)
	public @interface RequireLoraDevice {
		/**
		 * Version of the required security bundle
		 * 
		 * @return
		 */
		String versionStr() default "1.0.0";
	}

}
