package com.easyiot.auslora.device.api.capability;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.easyiot.base.capability.EasyiotNamespace;

import aQute.bnd.annotation.headers.ProvideCapability;
import aQute.bnd.annotation.headers.RequireCapability;

public interface AusloraDeviceCapability {

	public static final String AUSLORA_DEVICE = "auslora_device";

	@ProvideCapability(ns = EasyiotNamespace.NS, name = AUSLORA_DEVICE, version = "1.0.0")
	@Retention(RetentionPolicy.CLASS)
	public @interface ProvideAusloraDevice_v1_0_0 {

	}

	@RequireCapability(ns = EasyiotNamespace.NS, filter = "(&(" + EasyiotNamespace.NS + "=" + AUSLORA_DEVICE
			+ ")${frange;${versionStr}})")
	@Retention(RetentionPolicy.CLASS)
	public @interface RequireAusloraDevice {
		/**
		 * Version of the required auslora bundle
		 * 
		 * @return
		 */
		String versionStr() default "1.0.0";
	}

}
