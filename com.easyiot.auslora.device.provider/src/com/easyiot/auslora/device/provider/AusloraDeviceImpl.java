package com.easyiot.auslora.device.provider;

import java.util.concurrent.atomic.AtomicReference;

import javax.xml.bind.DatatypeConverter;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import com.easyiot.auslora.device.api.capability.AusloraDeviceCapability.ProvideAusloraDevice_v1_0_0;
import com.easyiot.auslora.device.api.dto.AusloraDataDTO;
import com.easyiot.auslora.device.api.dto.AusloraMetadataDTO;
import com.easyiot.auslora.device.api.dto.sensor.SensorDataDTO;
import com.easyiot.auslora.device.provider.configuration.AusloraSensorConfiguration;
import com.easyiot.base.api.Device;
import com.easyiot.websocket.protocol.api.WebsocketProtocol;
import com.easyiot.websocket.protocol.api.WsListener;

import osgi.enroute.dto.api.DTOs;

@ProvideAusloraDevice_v1_0_0
@Component(name = "com.easyiot.auslora.device")
@Designate(ocd = AusloraSensorConfiguration.class, factory = true)
public class AusloraDeviceImpl implements Device {
	private AusloraSensorConfiguration deviceConfiguration;
	private AtomicReference<AusloraDataDTO> lastKnownData = new AtomicReference<AusloraDataDTO>(new AusloraDataDTO());

	@Reference(name = "wsProtocolReference")
	WebsocketProtocol wsClient;

	@Reference
	private DTOs dtoConverter;

	private WsListener callback = (str) -> {
		try {
			// Only use the GW data messages
			if (str.contains("\"cmd\":\"gw\"")) {
				AusloraMetadataDTO newMetaData = dtoConverter.decoder(AusloraMetadataDTO.class).get(str);
				// Decode payload
				SensorDataDTO sensorData = parseSensorData(newMetaData.data);
				AusloraDataDTO newData = new AusloraDataDTO();
				newData.metadataDTO = newMetaData;
				// If the sensor data is corrupted use the last known value
				newData.sensorData = sensorData == null ? lastKnownData.get().sensorData : sensorData;
				lastKnownData.set(newData);
			}
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

	// Parses the daat according to the sensor data structure
	private SensorDataDTO parseSensorData(String data) {
		String[] values = new String(DatatypeConverter.parseHexBinary(data)).split(",");
		SensorDataDTO returnVal = null;
		if (values.length == 7) {
			returnVal = new SensorDataDTO();
			returnVal.deviceType = values[1];
			returnVal.reportType = values[2];
			returnVal.gpsFixStatus = values[3];
			returnVal.longitude = values[4];
			returnVal.latitude = values[5];
			returnVal.batteryCapacity = values[6].substring(0, values[6].indexOf("*"));
		}
		return returnVal;
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
