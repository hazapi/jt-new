package com.jt.test.factory;

import java.util.Calendar;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestFacotry {
	
	/**
	 * class 
	 * id      对象
	 * 
	 * <bean id="canlendar1" class="canlendar">
	 * <bean id="canlendar2" class="canlendar">
	 * <bean id="canlendar3" class="canlendar">
	 */
	@Test
	public void test01(){
		ApplicationContext context =
				new ClassPathXmlApplicationContext("/spring/factory.xml");
		Calendar calendar1 = (Calendar) context.getBean("calendar1");
		System.out.println(calendar1.getTime());
		
		Calendar calendar2 = (Calendar) context.getBean("calendar2");
		System.out.println(calendar2.getTime());
		
		Calendar calendar3 = (Calendar) context.getBean("calendar3");
		System.out.println(calendar3.getTime());
	}
	
	
	
	
	
	
	
	
}
