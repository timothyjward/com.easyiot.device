package com.easyiot.LT100H.device.provider.converter;

import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import com.easyiot.LT100H.device.api.dto.LT100HMetaDataDTO;
import com.easyiot.LT100H.device.api.dto.LT100HSensorDataDTO;
import com.easyiot.auslora_websocket.protocol.api.dto.AusloraMetadataDTO;

import osgi.enroute.dto.api.DTOs;

public class AusloraDataConverter {
	@SuppressWarnings("unused")
	private DTOs dtoConverter;

	public LT100HSensorDataDTO convert(AusloraMetadataDTO metadata, DTOs dtoConverter) {
		this.dtoConverter = dtoConverter;
		return parseSensorData(metadata);

	}

	private LT100HSensorDataDTO parseSensorData(AusloraMetadataDTO metadata) {
		//Decode the sensor data
		metadata.data = new String(DatatypeConverter.parseHexBinary(metadata.data));
		// Parse data
		String[] values = metadata.data.split(",");
		LT100HSensorDataDTO returnVal = null;
		if (values.length == 7) {
			returnVal = new LT100HSensorDataDTO();
			returnVal.deviceType = values[1];
			returnVal.reportType = values[2];
			returnVal.gpsFixStatus = values[3];
			returnVal.longitude = values[4];
			returnVal.latitude = values[5];
			returnVal.batteryCapacity = values[6].substring(0, values[6].indexOf("*"));
		}
		returnVal.metadata = parseMetadata(metadata);

		return returnVal;
	}

	private LT100HMetaDataDTO parseMetadata(AusloraMetadataDTO metadata) {
		LT100HMetaDataDTO newMeta = new LT100HMetaDataDTO();
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
