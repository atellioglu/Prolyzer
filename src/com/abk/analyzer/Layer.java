package com.abk.analyzer;

public class Layer {
	
	private int wordGram = 1; //shows the count of words to calculated 
	private float multipyler = 1.0f;// multiplication of layer result. 
	
	private Layer(){
		
	}
	
	public int getWordGram() {
		return wordGram;
	}

	public float getMultipyler() {
		return multipyler;
	}
	
	public static class Builder{
		
		private Layer layer;
		public Builder(){
			layer = new Layer();
		}
		public Builder multiplyer(float mult){
			layer.multipyler = mult;
			return this;
		}
		public Builder gram(int gram){
			layer.wordGram = gram;
			return this;
		}
		public Layer build(){
			return layer;
		}
	}
}
