package com.easyiot.bluetooth.device.api.capability;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.easyiot.base.capability.EasyiotNamespace;

import aQute.bnd.annotation.headers.ProvideCapability;
import aQute.bnd.annotation.headers.RequireCapability;

public interface BluetoothDeviceCapability {

	public static final String BLUETOOT_DEVICE = "bluetooth_device";

	@ProvideCapability(ns = EasyiotNamespace.NS, name = BLUETOOT_DEVICE, version = "1.0.0")
	@Retention(RetentionPolicy.CLASS)
	public @interface ProvideBluetoothDevice_v1_0_0 {

	}

	@RequireCapability(ns = EasyiotNamespace.NS, filter = "(&(" + EasyiotNamespace.NS + "=" + BLUETOOT_DEVICE
			+ ")${frange;${versionStr}})")
	@Retention(RetentionPolicy.CLASS)
	public @interface RequireBluetoothDevice {
		/**
		 * Version of the required bluetooth bundle
		 * 
		 * @return
		 */
		String versionStr() default "1.0.0";
	}

}
