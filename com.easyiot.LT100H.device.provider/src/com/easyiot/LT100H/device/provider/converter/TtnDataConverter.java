package com.easyiot.LT100H.device.provider.converter;

import java.util.Base64;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import com.easyiot.LT100H.device.api.dto.LT100HMetaDataDTO;
import com.easyiot.LT100H.device.api.dto.LT100HSensorDataDTO;
import com.easyiot.ttn_mqtt.protocol.api.dto.TtnMetaDataDTO;

import osgi.enroute.dto.api.DTOs;

public class TtnDataConverter {
	@SuppressWarnings("unused")
	private DTOs dtoConverter;

	public LT100HSensorDataDTO convert(TtnMetaDataDTO metadata, DTOs dtoConverter) {
		this.dtoConverter = dtoConverter;
		return parseSensorData(metadata);
	}

	private LT100HSensorDataDTO parseSensorData(TtnMetaDataDTO metadata) {
		// Decode sensor data
		metadata.payload = new String(DatatypeConverter.parseHexBinary(metadata.payload));
		// Parse data
		String[] values = metadata.payload.split(",");
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

	private LT100HMetaDataDTO parseMetadata(TtnMetaDataDTO metadata) {
		LT100HMetaDataDTO newMeta = new LT100HMetaDataDTO();
		// TODO newMeta.averageReceptionPeriod
		// TODO newMeta.calculatedAltitude
		// TODO newMeta.calculatedLatitude
		newMeta.gateway_eui = metadata.gwts.stream().map((mData) -> mData.gateway_eui).collect(Collectors.joining(","));
		newMeta.isRoaming = false;
		newMeta.networkName = "TTN";
		newMeta.timeStamp = metadata.gwts.get(0).gateway_timestamp;
		return newMeta;
	}

}
