package com.easyiot.auslora.device.provider;

import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import com.easyiot.auslora.device.api.capability.AusloraDeviceCapability.ProvideAusloraDevice_v1_0_0;
import com.easyiot.auslora.device.api.dto.AusloraDataDTO;
import com.easyiot.auslora.device.provider.configuration.AusloraSensorConfiguration;
import com.easyiot.base.api.Device;
import com.easyiot.websocket.protocol.api.WebsocketProtocol;
import com.easyiot.websocket.protocol.api.WsListener;

@ProvideAusloraDevice_v1_0_0
@Component(name = "com.easyiot.auslora.device")
@Designate(ocd = AusloraSensorConfiguration.class, factory = true)
public class AusloraDeviceImpl implements Device {
	private AusloraSensorConfiguration deviceConfiguration;
	private AtomicReference<AusloraDataDTO> lastKnownData = new AtomicReference<AusloraDataDTO>(new AusloraDataDTO());

	@Reference(name = "wsProtocolReference")
	WebsocketProtocol wsClient;

	private WsListener callback = (str) -> {
		try {
			// SensorDataDTO newData =
			// dtoConverter.decoder(SensorDataDTO.class).get(str);
			// // Decode payload
			// newData.payload = new
			// String(Base64.getDecoder().decode(newData.payload));
			// lastKnownData.set(newData);
			System.out.println("Message received from auslora: " + str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	@Activate
	public void activate(AusloraSensorConfiguration deviceConfiguration) {
		this.deviceConfiguration = deviceConfiguration;
		String deviceChannel = String.format("app?id=%s&token=%s", deviceConfiguration.applicationId(),
				deviceConfiguration.securityToken());
		wsClient.connect(deviceChannel, callback);
	}

	@GetMethod
	public AusloraDataDTO getData() {
		return lastKnownData.get();
	}

	@Override
	public String getId() {
		return deviceConfiguration.id();
	}

}
