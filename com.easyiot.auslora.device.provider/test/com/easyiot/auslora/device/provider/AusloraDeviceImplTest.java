package com.easyiot.auslora.device.provider;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.easyiot.auslora.device.api.dto.SensorDataDTO;
import com.easyiot.auslora_websocket.protocol.api.AusloraWebsocketProtocol;

import osgi.enroute.dto.api.DTOs;

public class AusloraDeviceImplTest {

	@InjectMocks
	AusloraDeviceImpl unitUnderTest;

	@Mock
	AusloraWebsocketProtocol mockWebProtocol;

	@Mock
	DTOs dtoConverter = mock(DTOs.class, RETURNS_DEEP_STUBS);

	@Before
	public void initTest() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testParseGatewayData() throws Exception {
		SensorDataDTO mockDataObj = new SensorDataDTO();
		// Encrypted sensor data;
		mockDataObj.batteryCapacity = "100";

		assertTrue(true);
	}

}
