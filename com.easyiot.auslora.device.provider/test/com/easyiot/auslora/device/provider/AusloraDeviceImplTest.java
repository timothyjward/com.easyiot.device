package com.easyiot.auslora.device.provider;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.easyiot.auslora.device.api.dto.AusloraDataDTO;
import com.easyiot.auslora.device.provider.configuration.AusloraSensorConfiguration;
import com.easyiot.websocket.protocol.api.WebsocketProtocol;
import com.easyiot.websocket.protocol.api.WsListener;

import osgi.enroute.dto.api.DTOs;

public class AusloraDeviceImplTest {
	private final static String RX_STRING = "{\"cmd\":\"rx\",\"EUI\":\"000DB5311773356B\",\"ts\":1475316944050,\"fcnt\":134,\"port\":2,\"freq\":916200000,\"rssi\":-67,\"snr\":13.2,\"dr\":\"SF9 BW125 4/5\",\"ack\":false,\"data\":\"242c322c322c312c4531343435362e343739392c53333735302e333831352c3130302a306421\"}";
	private final static String GW_STRING = "{\"cmd\":\"gw\",\"EUI\":\"000DB5311773356B\",\"ts\":1475316944050,\"fcnt\":134,\"port\":2,\"freq\":916200000,\"dr\":\"SF9 BW125 4/5\",\"ack\":false,\"gws\":[{\"rssi\":-67,\"snr\":13.2,\"ts\":1475316944050,\"gweui\":\"02-4B-08-FF-FF-0E-02-13.0\"}],\"data\":\"242c322c322c312c4531343435362e343739392c53333735302e333831352c3130302a306421\"}";

	@InjectMocks
	AusloraDeviceImpl unitUnderTest;

	@Mock
	WebsocketProtocol mockWebProtocol;

	@Mock
	DTOs dtoConverter = mock(DTOs.class, RETURNS_DEEP_STUBS);

	@Before
	public void initTest() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testParseGatewayData() throws Exception {
		AusloraDataDTO mockDataObj = new AusloraDataDTO();
		// Encrypted sensor data;
		mockDataObj.metadataDTO.data = "242c322c322c312c4531343435362e343739392c53333735302e333831352c3130302a306421";
		DTOs.Dec mockDtos = mock(DTOs.Dec.class);
		when(dtoConverter.decoder(AusloraDataDTO.class)).thenReturn(mockDtos);
		when(mockDtos.get(anyString())).thenReturn(mockDataObj);
		// start test
		AusloraSensorConfiguration config = getConfig();
		unitUnderTest.activate(config);
		// capture the listener and trigger it
		ArgumentCaptor<WsListener> listenerCaptor = ArgumentCaptor.forClass(WsListener.class);
		verify(mockWebProtocol).connect(anyString(), listenerCaptor.capture());
		listenerCaptor.getValue().processMessage(GW_STRING);

	}

	private AusloraSensorConfiguration getConfig() {
		return new AusloraSensorConfiguration() {

			@Override
			public Class<? extends Annotation> annotationType() {
				return AusloraSensorConfiguration.class;
			}

			@Override
			public String securityToken() {
				return "1";
			}

			@Override
			public String id() {
				return "1";
			}

			@Override
			public String applicationId() {
				return "appId";
			}
		};
	}

}
