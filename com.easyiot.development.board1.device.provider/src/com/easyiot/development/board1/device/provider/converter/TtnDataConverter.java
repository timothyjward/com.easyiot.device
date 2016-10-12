package com.easyiot.development.board1.device.provider.converter;

import java.util.Base64;
import java.util.stream.Collectors;

import com.easyiot.development.board1.device.api.dto.DevelopmentBoard1MetaDataDTO;
import com.easyiot.development.board1.device.api.dto.DevelopmentBoard1DeviceDataDTO;
import com.easyiot.ttn_mqtt.protocol.api.dto.TtnMetaDataDTO;

import osgi.enroute.dto.api.DTOs;

public class TtnDataConverter {
	private DTOs dtoConverter;

	public DevelopmentBoard1DeviceDataDTO convert(TtnMetaDataDTO metadata, DTOs dtoConverter) {
		this.dtoConverter = dtoConverter;
		return parseSensorData(metadata);
	}

	private DevelopmentBoard1DeviceDataDTO parseSensorData(TtnMetaDataDTO metadata) {
		DevelopmentBoard1DeviceDataDTO returnVal = new DevelopmentBoard1DeviceDataDTO();
		try {
			// Decode sensor data
			metadata.payload = new String(Base64.getDecoder().decode(metadata.payload));
			// Parse sensor data
			returnVal = dtoConverter.decoder(DevelopmentBoard1DeviceDataDTO.class).get(metadata.payload);
			returnVal.metadata = parseMetadata(metadata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnVal;
	}

	private DevelopmentBoard1MetaDataDTO parseMetadata(TtnMetaDataDTO metadata) {
		DevelopmentBoard1MetaDataDTO newMeta = new DevelopmentBoard1MetaDataDTO();
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
