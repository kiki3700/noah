package com.noah.app.configTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {
		private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
		@Test
		public void loggerTest() {
			logger.trace("trace test");
			logger.debug("debug test");
			logger.info("info test");
			logger.info("warn test");
			logger.error("error test");
		}

}
