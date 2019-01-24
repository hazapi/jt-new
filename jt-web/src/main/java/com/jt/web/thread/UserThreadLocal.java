package com.jt.web.thread;
//实现用户信息共享

import com.jt.common.po.User;

public class UserThreadLocal {
	
	private static ThreadLocal<User> threadLocal = 
			new ThreadLocal<>();
	
	public static void set(User user) {
		
		threadLocal.set(user);
	}
	
	public static User get() {
		
		return threadLocal.get();
	}
	
	//防止内存泄漏
	public static void remove() {
		
		threadLocal.remove();
	}
	
	
	
	
	
	
	
	
}
