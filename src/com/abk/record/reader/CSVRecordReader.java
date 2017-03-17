package com.abk.record.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.abk.analyzer.LabelHasher;
import com.abk.analyzer.TextModel;
import com.abk.record.RecordReader;

public class CSVRecordReader extends RecordReader {
	
	private File[] csvFiles;
	private char[] csvFileSeperators;
	
	public CSVRecordReader(char[] seperators,String[] paths) {
		if(paths!=null){
			csvFiles = new File[paths.length];
			for(int i =0;i<csvFiles.length;i++){
				csvFiles[i] = new File(paths[i]);
			}
		}
		if(seperators!=null){
			csvFileSeperators = seperators;
		}else{
			csvFileSeperators = new char[paths.length];
			for(int i =0;i<csvFileSeperators.length;i++){
				csvFileSeperators[i] = ',';
			}
		}
	}
	public CSVRecordReader(char seperator,String[] paths) {
		if(paths!=null){
			csvFiles = new File[paths.length];
			for(int i =0;i<csvFiles.length;i++){
				csvFiles[i] = new File(paths[i]);
			}
		}
		csvFileSeperators = new char[paths.length];
		for(int i =0;i<csvFileSeperators.length;i++){
			csvFileSeperators[i] = seperator;
		}
	}
	@Override
	public TextModel[] getModels() throws IOException {
		List<TextModel> models = new ArrayList<>();
		if(!isAsync()){
			for(int i =0;i<csvFiles.length;i++){
				BufferedReader reader = new BufferedReader(new FileReader(csvFiles[i]));
				String str = null;
				while((str = reader.readLine())!=null){
					String[] splitted = str.split("\\"+csvFileSeperators[i]);
					try{
						TextModel model = new TextModel(splitted[getTextIndex()],LabelHasher.getInstance().add(splitted[getLabelIndex()]));
						if(getRatingIndex()!=-1){
							model.setRating(Float.parseFloat(splitted[getRatingIndex()]));
						}
						models.add(model);
					}catch(ArrayIndexOutOfBoundsException ex){
						System.err.println("Reader can not parse the :"+str);
					}
				}
				reader.close();
			}
			TextModel[] arr = models.toArray(new TextModel[models.size()]);
			models = null;
			return arr;
		}else{
			
		}
		return null;
	}
}
