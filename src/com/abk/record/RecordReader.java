package com.abk.record;

import java.io.IOException;

import com.abk.analyzer.TextModel;

public abstract class RecordReader {
	private int labelIndex;
	private int ratingIndex = -1;
	private int textIndex;
	
	public int getTextIndex() {
		return textIndex;
	}

	public void setTextIndex(int textIndex) {
		this.textIndex = textIndex;
	}
	private boolean async;
	public abstract TextModel[] getModels() throws IOException;
	
	public boolean isAsync(){
		return async;
	}
	public void setAsync(boolean async){
		this.async = async;
	}
	public int getLabelIndex() {
		return labelIndex;
	}
	public void setLabelIndex(int labelIndex) {
		this.labelIndex = labelIndex;
	}
	
	public int getRatingIndex() {
		return ratingIndex;
	}

	public void setRatingIndex(int ratingIndex) {
		this.ratingIndex = ratingIndex;
	}
	
	
}
