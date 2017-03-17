package com.abk.record.writer;

import com.abk.analyzer.TextModel;

public class MySQLRecordWriter extends SQLRecordWriter {

	
	public MySQLRecordWriter() {
		super();
	}

	public MySQLRecordWriter(String connection, String userName, String pass) {
		super(connection, userName, pass);
	}

	@Override
	public void insert(TextModel... textModel) {
		
	}

	@Override
	public void saveInsignificantWords(String... words) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(TextModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(TextModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(TextModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertInsignificantWords(String... words) {
	}


	

}
