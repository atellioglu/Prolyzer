package com.abk.analyzer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LabelHasher {
	private static LabelHasher singleton = null;
	private Map<String, Integer> hash;
	private int id = 0;
	public static LabelHasher getInstance(){
		if(singleton ==null){
			singleton = new LabelHasher();
		}
		return singleton;
	}
	private LabelHasher(){
		hash = new ConcurrentHashMap<>();
	}
	public int add(String label){
		if(get(label)==-1){
			hash.put(label,id++);	
		}
		return hash.get(label);
	}
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for(Map.Entry<String, Integer> m : hash.entrySet()){
			buffer.append("[");
			buffer.append(m.getKey() + " "+m.getValue());
			buffer.append("]");
			buffer.append(" - ");
		}
		
		return buffer.toString();
	}
	public int get(String label){
		if(hash.containsKey(label)){
			return hash.get(label);
		}else{
			return -1;
		}
	}
	public String get(int id){
		for(Map.Entry<String, Integer> m : hash.entrySet()){
			if(m.getValue()==id){
				return m.getKey();
			}
		}
		return null;
	}
}
