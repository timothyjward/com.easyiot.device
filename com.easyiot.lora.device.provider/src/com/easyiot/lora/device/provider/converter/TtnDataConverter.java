package com.easyiot.lora.device.provider.converter;

import java.util.Base64;
import java.util.stream.Collectors;

import com.easyiot.lora.device.api.dto.MetaDataDTO;
import com.easyiot.lora.device.api.dto.SensorDataDTO;
import com.easyiot.ttn_mqtt.protocol.api.dto.TtnMetaDataDTO;

import osgi.enroute.dto.api.DTOs;

public class TtnDataConverter {
	private DTOs dtoConverter;

	public SensorDataDTO convert(TtnMetaDataDTO metadata, DTOs dtoConverter) {
		this.dtoConverter = dtoConverter;
		return parseSensorData(metadata);
	}

	private SensorDataDTO parseSensorData(TtnMetaDataDTO metadata) {
		SensorDataDTO returnVal = new SensorDataDTO();
		try {
			// Decode sensor data
			metadata.payload = new String(Base64.getDecoder().decode(metadata.payload));
			// Parse sensor data
			returnVal = dtoConverter.decoder(SensorDataDTO.class).get(metadata.payload);
			returnVal.metadata = parseMetadata(metadata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnVal;
	}

	private MetaDataDTO parseMetadata(TtnMetaDataDTO metadata) {
		MetaDataDTO newMeta = new MetaDataDTO();
		// TODO newMeta.averageReceptionPeriod
		// TODO newMeta.calculatedAltitude
		// TODO newMeta.calculatedLatitude
		newMeta.gateway_eui = metadata.gwts.stream().map((mData) -> mData.gateway_eui)
				.collect(Collectors.joining(","));
		newMeta.isRoaming = false;
		newMeta.networkName = "TTN";
		newMeta.timeStamp = metadata.gwts.get(0).gateway_timestamp;
		return newMeta;
	}

}
