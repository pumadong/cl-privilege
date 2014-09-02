package com.cl.privilege.api.impl.test;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cl.privilege.api.impl.PrivilegeBaseApiServiceImpl;
import com.cl.privilege.model.User;



public class PrivilegeBaseApiServiceImplTest {
	
	private static PrivilegeBaseApiServiceImpl service;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-test.xml");  
		service = (PrivilegeBaseApiServiceImpl)ctx.getBean("privilegeBaseApiServiceImpl");
		ctx.close();
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getUserByUsername() {
		User user = service.getUserByUsername("root");
		if(user == null)
		{
			System.out.println("user is null");
		} else {
			System.out.println("user is not null");
		}
	}
}
