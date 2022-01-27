package com.noah.app.java.optional;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OptionalTest {
	@Test
	public void elseTest(){
		String nullName = null;
		String name = Optional.ofNullable(nullName).orElse("sh");
		assertEquals("sh",name);
	}
}
