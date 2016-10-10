package com.easyiot.lora.device.provider.converter;

import java.util.stream.Collectors;

import com.easyiot.auslora_websocket.protocol.api.dto.AusloraMetadataDTO;
import com.easyiot.lora.device.api.dto.MetaDataDTO;
import com.easyiot.lora.device.api.dto.SensorDataDTO;

import osgi.enroute.dto.api.DTOs;

public class AusloraDataConverter {
	private DTOs dtoConverter;

	public SensorDataDTO convert(AusloraMetadataDTO metadata, DTOs dtoConverter) {
		this.dtoConverter = dtoConverter;
		return parseSensorData(metadata);

	}

	private SensorDataDTO parseSensorData(AusloraMetadataDTO metadata) {
		SensorDataDTO returnVal = new SensorDataDTO();
		try {
			// Parse the sensor data
			returnVal = dtoConverter.decoder(SensorDataDTO.class).get(metadata.data);
			returnVal.metadata = parseMetadata(metadata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnVal;
	}

	private MetaDataDTO parseMetadata(AusloraMetadataDTO metadata) {
		MetaDataDTO newMeta = new MetaDataDTO();
		// TODO newMeta.averageReceptionPeriod
		// TODO newMeta.calculatedAltitude
		// TODO newMeta.calculatedLatitude
		newMeta.gateway_eui = metadata.gwts.stream().map((gwt) -> gwt.gweui).collect(Collectors.joining(","));
		newMeta.isRoaming = false;
		newMeta.networkName = "Auslora";
		newMeta.timeStamp = metadata.ts;
		return newMeta;
	}

}
