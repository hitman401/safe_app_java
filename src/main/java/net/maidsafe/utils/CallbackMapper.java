package net.maidsafe.utils;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Callback;

public class CallbackMapper {

	private final List<Callback> cbs = new ArrayList<Callback>();
	private static CallbackMapper mapper; 
	
	private CallbackMapper() {
		
	}
	
	public static CallbackMapper getInstance() {
		if (mapper == null) {
			mapper = new CallbackMapper();
		}
		return mapper;
	}
	
	public void add(Callback cb) {
		cbs.add(cb);
	}
	
	public void remove(Callback cb) {
		cbs.remove(cb);
	}
	
}
