package com.abk.analyzer;

public class Evaluation {
	private float percent;
	private String label;
	private String text;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public float getPercent() {
		return percent;
	}
	public void setPercent(float percent) {
		this.percent = percent;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Evaluation(){
		
	}
	public Evaluation(float percent, String label) {
		super();
		this.percent = percent;
		this.label = label;
	}
	public Evaluation(float percent, String label, String text) {
		super();
		this.percent = percent;
		this.label = label;
		this.text = text;
	}
	@Override
	public String toString() {
		return "Evaluation [percent=" + percent + ", label=" + label + ", text=" + text + "]";
	}
	
	
	
	
}
