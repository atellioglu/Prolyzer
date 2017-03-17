package com.abk;

import java.io.IOException;
import java.util.Scanner;

import com.abk.analyzer.Evaluation;
import com.abk.analyzer.LabelHasher;
import com.abk.analyzer.Layer;
import com.abk.analyzer.PreProcessor;
import com.abk.analyzer.ProModel;
import com.abk.analyzer.TextModel;
import com.abk.record.RecordReader;
import com.abk.record.reader.CSVRecordReader;
import com.abk.util.StringUtils;

public class MainClass {

	public static void main(String[] args) throws IOException {
		RecordReader reader = new CSVRecordReader('|', new String[]{"/Users/abdullahtellioglu/Documents/java/TwitterCollector/tweets.csv"});
		reader.setLabelIndex(1);
		reader.setTextIndex(0);
		TextModel[] models = reader.getModels();
		
		ProModel model = new ProModel.Builder()
				.layer(0, new Layer.Builder().gram(1).build())
				.layer(1, new Layer.Builder().gram(2).build())
				.layer(2, new Layer.Builder().gram(3).build())
				.setPreProcessor(new PreProcessor() {
					@Override
					public String process(String pure) {
						return StringUtils.removeNonAlphaCharacters(StringUtils.replaceTurkishCharacters(StringUtils.lowerCase(pure)));
					}
				})
				.build();
		model.train(models);
		System.out.println(LabelHasher.getInstance().toString());
		Scanner scanner = new Scanner(System.in);
		while(true){
			String str = scanner.nextLine();
			Evaluation e = model.classify(str);
			System.out.println(e.toString());
		}
	}

}
