package com.easyiot.auslora.device.api.dto;

import org.osgi.dto.DTO;

import com.easyiot.auslora.device.api.capability.AusloraDeviceCapability.RequireAusloraDevice;
import com.easyiot.auslora.device.api.dto.sensor.SensorDataDTO;

/**
 * data parsed for consumption by the application. represents cumulative
 * information including MetadataData passed by the underlying protocol and the
 * actual sensor information parsed according to the sensor datasheet.
 * 
 * @author daghan
 *
 */
@RequireAusloraDevice
public class AusloraDataDTO extends DTO {
	// Set up the initial values;
	public SensorDataDTO sensorData = new SensorDataDTO();
	public AusloraMetadataDTO metadataDTO = new AusloraMetadataDTO();
}
