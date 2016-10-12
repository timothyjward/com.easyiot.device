package com.easyiot.development.board1.device.provider.converter;

import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import com.easyiot.auslora_websocket.protocol.api.dto.AusloraMetadataDTO;
import com.easyiot.development.board1.device.api.dto.DevelopmentBoard1MetaDataDTO;
import com.easyiot.development.board1.device.api.dto.DevelopmentBoard1DeviceDataDTO;

import osgi.enroute.dto.api.DTOs;

public class AusloraDataConverter {
	private DTOs dtoConverter;

	public DevelopmentBoard1DeviceDataDTO convert(AusloraMetadataDTO metadata, DTOs dtoConverter) {
		this.dtoConverter = dtoConverter;
		return parseSensorData(metadata);

	}

	private DevelopmentBoard1DeviceDataDTO parseSensorData(AusloraMetadataDTO metadata) {
		DevelopmentBoard1DeviceDataDTO returnVal = new DevelopmentBoard1DeviceDataDTO();
		try {
			//Decode the sensor data
			metadata.data = new String(DatatypeConverter.parseHexBinary(metadata.data));
			// Parse the sensor data
			returnVal = dtoConverter.decoder(DevelopmentBoard1DeviceDataDTO.class).get(metadata.data);
			returnVal.metadata = parseMetadata(metadata);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnVal;
	}

	private DevelopmentBoard1MetaDataDTO parseMetadata(AusloraMetadataDTO metadata) {
		DevelopmentBoard1MetaDataDTO newMeta = new DevelopmentBoard1MetaDataDTO();
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
