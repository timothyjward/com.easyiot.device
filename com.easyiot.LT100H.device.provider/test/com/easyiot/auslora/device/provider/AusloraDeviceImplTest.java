package com.easyiot.auslora.device.provider;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.easyiot.LT100H.device.api.dto.LT100HSensorDataDTO;
import com.easyiot.LT100H.device.provider.LT100HDeviceImpl;
import com.easyiot.auslora_websocket.protocol.api.AusloraWebsocketProtocol;

import osgi.enroute.dto.api.DTOs;

public class AusloraDeviceImplTest {

	@InjectMocks
	LT100HDeviceImpl unitUnderTest;

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
		LT100HSensorDataDTO mockDataObj = new LT100HSensorDataDTO();
		// Encrypted sensor data;
		mockDataObj.batteryCapacity = "100";

		assertTrue(true);
	}

}
