package com.noah.app.quant.ojAlgo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ojalgo.machine.Hardware;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HardwareSetting {
	
	@Test
	public void hardwareTeest() {
		Hardware hardware = Hardware.makeSimple("amd", 4L, 1);
		org.ojalgo.OjAlgoUtils.ENVIRONMENT=	hardware.virtualise();
	}
}
