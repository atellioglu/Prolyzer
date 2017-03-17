package com.abk.record;

import com.abk.analyzer.TextModel;

public abstract class RecordWriter {
	private boolean async = false;
	public abstract void insert(TextModel... textModel);
	public abstract void saveInsignificantWords(String... words);
	public boolean isAsync(){
		return async;
	}
	public void setAsync(boolean async){
		this.async = async;
	}
}
