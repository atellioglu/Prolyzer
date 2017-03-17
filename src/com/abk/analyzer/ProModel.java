package com.abk.analyzer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.abk.exception.LayerIndexException;
import com.abk.exception.NoLayerException;
import com.abk.record.RecordWriter;
import com.abk.util.StringUtils;

public class ProModel {
	private Layer[] layers;
	private RecordWriter writer = null;
	private Map<String, Map<Integer,Float>> calculations = null;
	private ExecutorService layerExecutors;
	private Set<String> insignificantKeywords;
	private String[] insignificantKeywordArr = null;
	private PreProcessor preProcessor;
	private ProModel(){	
		calculations = new ConcurrentHashMap<>();
	}
	public Evaluation classify(String text){
		String mText = text;
		if(preProcessor!=null){
			mText = preProcessor.process(text);
		}
		Map<Integer, Float> resultMap = new HashMap<>();
		Evaluation eval = new Evaluation();
		for(int i =0;i<layers.length;i++){
			Layer layer = layers[i];
			String[] splitted = mText.split(" ");
			for(int j=0;j<splitted.length;j++){
				StringBuffer buffer = new StringBuffer();
				int counter = 0;
				for(int k=j;k< splitted.length && k<layer.getWordGram()+j;k++){
					buffer.append(splitted[k]);
					buffer.append(" ");
					counter++;
				}
				if(counter==layer.getWordGram()){
					String str = StringUtils.trimEnd(buffer.toString());
					Map<Integer,Float> res = result(str);
					if(res!=null){
						for(Map.Entry<Integer, Float> e : res.entrySet()){
							if(resultMap.containsKey(e.getKey())){
								float kk =resultMap.get(e.getKey());
								resultMap.put(e.getKey(), kk+e.getValue());
							}else{
								resultMap.put(e.getKey(),e.getValue());
							}
						}	
					}
				}
			}
		}
		float maxRating = -1;
		int maxLabelIndex = -1;
		float totalRating = 0;
		for(Map.Entry<Integer, Float> e : resultMap.entrySet()){
			if(e.getValue()>maxRating){
				maxRating = e.getValue();
				maxLabelIndex = e.getKey();
			}
			totalRating += e.getValue();
		}
		eval.setText(text);
		eval.setLabel(LabelHasher.getInstance().get(maxLabelIndex));
		eval.setPercent(maxRating/totalRating);
		return eval;
	}
	public void train(TextModel[] textModel){
		if(layers==null){
			throw new NoLayerException();
		}
		if(preProcessor!=null){
			for(int i =0;i<textModel.length;i++){
				textModel[i].setText(preProcessor.process(textModel[i].getText()));
			}
		}
		layerExecutors = Executors.newFixedThreadPool(layers.length);
		for(int i =0;i<layers.length;i++){
			LayerTrainer trainer = new LayerTrainer(this,layers[i], textModel);
			layerExecutors.submit(trainer);
		}
		layerExecutors.shutdown();
		try{
			layerExecutors.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
			if(writer!=null){
				writer.insert(textModel);
				writer.saveInsignificantWords(insignificantKeywordArr);	
			}
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}
	public void setPreProcessor(PreProcessor preProcessor){
		this.preProcessor = preProcessor;
	}
	private Map<Integer, Float> result(String str){
		if(calculations.containsKey(str)){
			return calculations.get(str);
		}else{
			return null;
		}
	}
	private float calculated(String str,float rating,int labelIndex){
		if(calculations.containsKey(str)){
			Map<Integer,Float> labelMap = calculations.get(str);
			if(labelMap.containsKey(labelIndex)){
				float currentRating =labelMap.get(labelIndex);
				labelMap.put(labelIndex, currentRating+rating);
			}else{
				labelMap.put(labelIndex, rating);
			}
		}else{
			Map<Integer,Float> dummy = new ConcurrentHashMap<>();
			dummy.put(labelIndex, rating);
			calculations.put(str, dummy);	
		}
		return calculations.get(str).get(labelIndex);
	}
	public static class Builder{
		private HashMap<Integer, Layer> layerMap;
		private ProModel model;
		public Builder(){
			layerMap = new HashMap<>();
			model = new ProModel();
		}
		public Builder layer(int index,Layer layer){
			if(index<0){
				throw new LayerIndexException("Layer index %d is invalid",index);
			}
			layerMap.put(index, layer);
			return this;
		}
		public Builder writer(RecordWriter writer){
			model.writer = writer;
			return this;
		}
		public Builder insignificantKeywords(String... keys){
			if(model.insignificantKeywords==null)
				model.insignificantKeywords = new HashSet<>();
			for(int i=0;i<keys.length;i++){
				model.insignificantKeywords.add(keys[i]);
			}
			model.insignificantKeywordArr = keys;
			return this;
		}
		public Builder setPreProcessor(PreProcessor preProcessor){
			model.preProcessor = preProcessor;
			return this;
		}
		private void setLayers(){
			model.layers = new Layer[layerMap.size()];
			for (Map.Entry<Integer, Layer> entry : layerMap.entrySet()) {
			    int index = entry.getKey();
			    Layer layer = entry.getValue();
			    if(index<0 || index>model.layers.length){
			    	throw new LayerIndexException(index);
			    }
			    model.layers[index] = layer;
			}
		}
		public ProModel build(){
			setLayers();
			return model;
		}
	}
	
	private class LayerTrainer implements Runnable{
		private Layer layer;
		private TextModel[] models;
		private ProModel model;
		public LayerTrainer(ProModel model,Layer layer, TextModel[] models) {
			super();
			this.layer = layer;
			this.models = models;
			this.model = model;
		}
		
		@Override
		public void run() {
			for(int i =0;i<models.length;i++){
				models[i].setText(StringUtils.trimStartAndEnd(StringUtils.removeAllWhiteSpacesExceptOne(models[i].getText())));
				String[] splitted = models[i].getText().split(" ");
				for(int j=0;j<splitted.length;j++){
					StringBuffer buffer = new StringBuffer();
					int counter = 0;
					for(int k=j;k< splitted.length && k<layer.getWordGram()+j;k++){
						buffer.append(splitted[k]);
						buffer.append(" ");
						counter++;
					}
					if(counter==layer.getWordGram()){
						String str = StringUtils.trimEnd(buffer.toString());
						float result = model.calculated(str, layer.getMultipyler(), models[i].getLabel());
						System.out.println(str + " "+result);
					}
					
				}
				try{
					Thread.sleep(15);
				}catch(InterruptedException ex){
					ex.printStackTrace();
				}
				
			}
			
		}
		
	}
}
