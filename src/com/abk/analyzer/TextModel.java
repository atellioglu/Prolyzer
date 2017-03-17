package com.abk.analyzer;

public class TextModel {
	private String text;
	private float rating;
	private int label;
	public TextModel(String text,int label){
		this.text = text;
		this.label = label;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public synchronized float getRating() {
		return rating;
	}
	public synchronized void setRating(float rating) {
		this.rating = rating;
	}
	public synchronized int getLabel() {
		return label;
	}
	public void setLabel(int label) {
		this.label = label;
	}
	@Override
	public String toString() {
		return "TextModel [text=" + text + ", rating=" + rating + ", label=" + label + "]";
	}
	
	
}
