package com.easyiot.auslora.device.api.dto;

import org.osgi.dto.DTO;

import com.easyiot.auslora.device.api.capability.AusloraDeviceCapability.RequireAusloraDevice;

@RequireAusloraDevice
public class AusloraDataDTO extends DTO {
	public String cmd = "gw";
	public String EUI = "000DB531176F3557";
	public long ts = 1474695794926L;
	public long fcnt = 44;
	public int port = 2;
	public int freq = 916400000;
	public int rssi = -56;
	public double snr = 11.8;
	public String dr = "SF10 BW125 4/5";
	public boolean ack = false;
	public String data = "2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e2e";
}
