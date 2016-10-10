package com.easyiot.auslora.device.provider;

import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.metatype.annotations.Designate;

import com.easyiot.auslora.device.api.capability.AusloraDeviceCapability.ProvideAusloraDevice_v1_0_0;
import com.easyiot.auslora.device.api.dto.SensorDataDTO;
import com.easyiot.auslora.device.provider.configuration.AusloraSensorConfiguration;
import com.easyiot.auslora.device.provider.converter.AusloraDataConverter;
import com.easyiot.auslora.device.provider.converter.TtnDataConverter;
import com.easyiot.auslora_websocket.protocol.api.AusloraWebsocketListener;
import com.easyiot.auslora_websocket.protocol.api.AusloraWebsocketProtocol;
import com.easyiot.base.api.Device;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttMessageListener;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttProtocol;

import osgi.enroute.dto.api.DTOs;

@ProvideAusloraDevice_v1_0_0
@Component(name = "com.easyiot.auslora.device")
@Designate(ocd = AusloraSensorConfiguration.class, factory = true)
public class AusloraDeviceImpl implements Device {
	private AusloraSensorConfiguration deviceConfiguration;
	private AtomicReference<SensorDataDTO> lastKnownData = new AtomicReference<SensorDataDTO>(new SensorDataDTO());
	private AusloraDataConverter myAusloraDataConverter = new AusloraDataConverter();
	private TtnDataConverter myTtnDataConverter = new TtnDataConverter();

	@Reference
	private DTOs dtoConverter;

	private AusloraWebsocketListener callback = (metadata) -> {
		SensorDataDTO sensorData = myAusloraDataConverter.convert(metadata, dtoConverter);
		lastKnownData.set(sensorData);
	};

	// bind ausloraProtocol
	@Reference(name = "auslorawsProtocolReference", cardinality = ReferenceCardinality.OPTIONAL, policy = ReferencePolicy.DYNAMIC, unbind = "unSetAusloraWebSocket")
	public void setAusloraWebSocket(AusloraWebsocketProtocol auslorawsClient) {
		String deviceChannel = String.format("app?id=%s&token=%s", deviceConfiguration.applicationId(),
				deviceConfiguration.securityToken());
		auslorawsClient.connect(deviceChannel, deviceConfiguration.deviceEUI(), callback);
	}

	// unbind auslora client
	public void unSetAusloraWebSocket(AusloraWebsocketProtocol auslorawsClient) {
		String deviceChannel = String.format("app?id=%s&token=%s", deviceConfiguration.applicationId(),
				deviceConfiguration.securityToken());
		auslorawsClient.disconnect(deviceChannel, deviceConfiguration.deviceEUI(), callback);
	}

	// lambda that is subscribed to MQTT
	private TtnMqttMessageListener subsMethod = (metadata) -> {
		SensorDataDTO newData = myTtnDataConverter.convert(metadata, dtoConverter);
		lastKnownData.set(newData);
	};

	// Bind mqttClient
	@Reference(name = "mqttProtocolReference", cardinality = ReferenceCardinality.OPTIONAL, policy = ReferencePolicy.DYNAMIC, unbind = "unMqttClient")
	public void setMqttClient(TtnMqttProtocol ttnMqttClient) {
		ttnMqttClient.subscribe(deviceConfiguration.subscriptionChannel(), subsMethod);
	}

	// Unbind mqttclient
	public void unMqttClient(TtnMqttProtocol ttnMqttClient) {
		ttnMqttClient.unsubscribe(deviceConfiguration.subscriptionChannel());
	}

	@Activate
	public void activate(AusloraSensorConfiguration deviceConfiguration) {
		this.deviceConfiguration = deviceConfiguration;
	}

	@GetMethod
	public SensorDataDTO getData() {
		return lastKnownData.get();
	}

	@Override
	public String getId() {
		return deviceConfiguration.id();
	}

}
