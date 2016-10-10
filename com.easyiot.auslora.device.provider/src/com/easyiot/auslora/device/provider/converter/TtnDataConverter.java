package com.easyiot.auslora.device.provider.converter;

import java.util.stream.Collectors;

import com.easyiot.auslora.device.api.dto.MetaDataDTO;
import com.easyiot.auslora.device.api.dto.SensorDataDTO;
import com.easyiot.ttn_mqtt.protocol.api.dto.TtnMetaDataDTO;

import osgi.enroute.dto.api.DTOs;

public class TtnDataConverter {
	@SuppressWarnings("unused")
	private DTOs dtoConverter;

	public SensorDataDTO convert(TtnMetaDataDTO metadata, DTOs dtoConverter) {
		this.dtoConverter = dtoConverter;
		return parseSensorData(metadata);
	}

	private SensorDataDTO parseSensorData(TtnMetaDataDTO metadata) {
		String[] values = metadata.payload.split(",");
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
		returnVal.metadata = parseMetadata(metadata);
		
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
