package com.easyiot.LT100H.device.provider;

import static org.osgi.service.component.annotations.ReferenceCardinality.OPTIONAL;
import static org.osgi.service.component.annotations.ReferencePolicyOption.GREEDY;

import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.metatype.annotations.Designate;

import com.easyiot.LT100H.device.api.capability.LT100HDeviceCapability.ProvideLT100HDevice_v1_0_0;
import com.easyiot.LT100H.device.api.dto.LT100HSensorDataDTO;
import com.easyiot.LT100H.device.provider.configuration.LT100HDeviceConfiguration;
import com.easyiot.LT100H.device.provider.converter.AusloraDataConverter;
import com.easyiot.LT100H.device.provider.converter.TtnDataConverter;
import com.easyiot.auslora_websocket.protocol.api.AusloraWebsocketListener;
import com.easyiot.auslora_websocket.protocol.api.AusloraWebsocketProtocol;
import com.easyiot.base.api.Device;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttMessageListener;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttProtocol;

import osgi.enroute.dto.api.DTOs;

@ProvideLT100HDevice_v1_0_0
@Component(name = "com.easyiot.LT100H.device")
@Designate(ocd = LT100HDeviceConfiguration.class, factory = true)
public class LT100HDeviceImpl implements Device {
	private LT100HDeviceConfiguration deviceConfiguration;
	private AtomicReference<LT100HSensorDataDTO> lastKnownData = new AtomicReference<LT100HSensorDataDTO>(new LT100HSensorDataDTO());
	private AusloraDataConverter myAusloraDataConverter = new AusloraDataConverter();
	private TtnDataConverter myTtnDataConverter = new TtnDataConverter();

	@Reference
	private DTOs dtoConverter;
	
	@Reference(name = "auslorawsProtocolReference", cardinality = OPTIONAL, policyOption = GREEDY)
	private AusloraWebsocketProtocol auslorawsClient;
	
	@Reference(name = "mqttProtocolReference", cardinality = OPTIONAL, policyOption = GREEDY)
	private TtnMqttProtocol ttnMqttClient;
	
	private AusloraWebsocketListener callback = (metadata) -> {
		LT100HSensorDataDTO sensorData = myAusloraDataConverter.convert(metadata, dtoConverter);
		lastKnownData.set(sensorData);
	};

	// lambda that is subscribed to MQTT
	private TtnMqttMessageListener subsMethod = (metadata) -> {
		LT100HSensorDataDTO newData = myTtnDataConverter.convert(metadata, dtoConverter);
		lastKnownData.set(newData);
	};

	@Activate
	public void activate(LT100HDeviceConfiguration deviceConfiguration) {
		this.deviceConfiguration = deviceConfiguration;
		
		if(ttnMqttClient != null) {
			ttnMqttClient.subscribe(deviceConfiguration.subscriptionChannel(), subsMethod);
		}
		
		if(auslorawsClient != null) {
			String deviceChannel = String.format("app?id=%s&token=%s", deviceConfiguration.applicationId(),
					deviceConfiguration.securityToken());
			auslorawsClient.connect(deviceChannel, deviceConfiguration.deviceEUI(), callback);
		}
	}
	
	@Deactivate
	public void deactivate() {
		
		if(ttnMqttClient != null) {
			ttnMqttClient.unsubscribe(deviceConfiguration.subscriptionChannel());
		}
		
		if(auslorawsClient != null) {
			String deviceChannel = String.format("app?id=%s&token=%s", deviceConfiguration.applicationId(),
					deviceConfiguration.securityToken());
			auslorawsClient.disconnect(deviceChannel, deviceConfiguration.deviceEUI(), callback);
		}
	}

	@GetMethod
	public LT100HSensorDataDTO getData() {
		return lastKnownData.get();
	}

	@Override
	public String getId() {
		return deviceConfiguration.id();
	}

}
