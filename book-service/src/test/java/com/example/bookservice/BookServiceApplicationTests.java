package com.example.bookservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceApplicationTests {

	@Autowired
	private BookServiceApplication app;
	
	@Test
	public void contextLoads() {
		
		Assert.assertNotNull(app);
	}

}
