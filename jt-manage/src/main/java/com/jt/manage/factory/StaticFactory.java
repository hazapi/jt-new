package com.jt.manage.factory;

import java.util.Calendar;

import com.jt.manage.mapper.UserMapper;
public class StaticFactory {
	//静态方法
	public static Calendar getCalendar(){
		
		return Calendar.getInstance();
	}
}
