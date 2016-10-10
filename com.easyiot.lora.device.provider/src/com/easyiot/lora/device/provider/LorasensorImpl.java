package com.easyiot.lora.device.provider;

import java.util.concurrent.atomic.AtomicReference;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.metatype.annotations.Designate;

import com.easyiot.auslora_websocket.protocol.api.AusloraWebsocketListener;
import com.easyiot.auslora_websocket.protocol.api.AusloraWebsocketProtocol;
import com.easyiot.base.api.Device;
import com.easyiot.lora.device.api.capability.LoraDeviceCapability.ProvideLoraDevice_v1_0_0;
import com.easyiot.lora.device.api.dto.SensorDataDTO;
import com.easyiot.lora.device.provider.configuration.LoraSensorConfiguration;
import com.easyiot.lora.device.provider.converter.AusloraDataConverter;
import com.easyiot.lora.device.provider.converter.TtnDataConverter;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttMessageListener;
import com.easyiot.ttn_mqtt.protocol.api.TtnMqttProtocol;

import osgi.enroute.dto.api.DTOs;

@ProvideLoraDevice_v1_0_0
@Component(name = "com.easyiot.device.lora.device", configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = LoraSensorConfiguration.class, factory = true)
public class LorasensorImpl implements Device {
	private static final String NOT_AVAILABLE = "N/A";
	private AtomicReference<SensorDataDTO> lastKnownData = new AtomicReference<SensorDataDTO>(new SensorDataDTO());
	@Reference
	private DTOs dtoConverter;
	private LoraSensorConfiguration deviceConfiguration;
	private AusloraDataConverter myAusloraDataConverter = new AusloraDataConverter();
	private TtnDataConverter myTtnDataConverter = new TtnDataConverter();

	private SensorDataDTO newData = new SensorDataDTO();
	// lambda that is subscribed to MQTT
	private TtnMqttMessageListener subsMethod = (metadata) -> {
		SensorDataDTO newData = myTtnDataConverter.convert(metadata, dtoConverter);
		lastKnownData.set(newData);
	};

	private AusloraWebsocketListener callback = (metadata) -> {
		SensorDataDTO sensorData = myAusloraDataConverter.convert(metadata, dtoConverter);
		lastKnownData.set(sensorData);
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

	@Activate
	public void activate(LoraSensorConfiguration conf) {
		this.deviceConfiguration = conf;
	}

	@GetMethod
	public SensorDataDTO getData() {
		createRandomData();
		return lastKnownData.get();
	}

	// Creates a random data if device configuration defines either of
	// subscription or publish channel as "N/A"
	private void createRandomData() {
		if (NOT_AVAILABLE.equals(deviceConfiguration.subscriptionChannel())
				|| NOT_AVAILABLE.equals(deviceConfiguration.publishChannel())) {
			newData.temp = (int) (120 * Math.random() - 20);
			newData.lat += (.01 * (Math.random() - 0.5));
			newData.lon += (.01 * (Math.random() - 0.5));
			lastKnownData.set(newData);
		}
	}

	@Override
	public String getId() {
		return deviceConfiguration.id();
	}

}
