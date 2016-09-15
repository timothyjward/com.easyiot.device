package com.easyiot.color3led.device.provider;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.easyiot.base.api.Device;
import com.easyiot.color3led.device.api.capability.Color3LedCapability.ProvideColor3LedDevice_v1_0_0;
import com.easyiot.color3led.device.api.dto.ColorDto;
import com.easyiot.color3led.device.configuration.Color3LedConfiguration;
import com.easyiot.gpio.protocol.api.GpioProtocol;
import com.easyiot.gpio.protocol.api.InputOutputEnum;
import com.easyiot.gpio.protocol.api.PinLevelEnum;

/**
 * 3 color LED device implementation
 * 
 * @author daghan
 *
 */
@ProvideColor3LedDevice_v1_0_0
@Component(name = "com.easyiot.color3led.device")
public class Color2LedDevice implements Device {
	private Color3LedConfiguration configuration;

	@Reference(name = "gpioProtocolReference")
	GpioProtocol protocol;

	@Activate
	public void activate(Color3LedConfiguration configuration) {
		this.configuration = configuration;
		setupGpio();
	}

	@Override
	public String getId() {
		return configuration.id();
	}

	private void setupGpio() {
		protocol.configurePin(configuration.bluePin(), InputOutputEnum.output, PinLevelEnum.low);
		protocol.configurePin(configuration.greenPin(), InputOutputEnum.output, PinLevelEnum.low);
		protocol.configurePin(configuration.redPin(), InputOutputEnum.output, PinLevelEnum.low);
	}

	@GetMethod
	public ColorDto getPinValues() {
		ColorDto returnVal = new ColorDto();
		returnVal.blueValue = protocol.readPinValue(configuration.bluePin()) ? ColorDto.HIGH_VALUE : ColorDto.LOW_VALUE;
		returnVal.redValue = protocol.readPinValue(configuration.redPin()) ? ColorDto.HIGH_VALUE : ColorDto.LOW_VALUE;
		returnVal.greenValue = protocol.readPinValue(configuration.greenPin()) ? ColorDto.HIGH_VALUE
				: ColorDto.LOW_VALUE;
		return returnVal;
	}

	@PostMethod
	public void setPinValues(ColorDto value) {
		protocol.writePinValue(configuration.bluePin(),
				value.blueValue.equalsIgnoreCase(ColorDto.HIGH_VALUE) ? true : false);
		protocol.writePinValue(configuration.greenPin(),
				value.greenValue.equalsIgnoreCase(ColorDto.HIGH_VALUE) ? true : false);
		protocol.writePinValue(configuration.redPin(),
				value.redValue.equalsIgnoreCase(ColorDto.HIGH_VALUE) ? true : false);
	}

}
