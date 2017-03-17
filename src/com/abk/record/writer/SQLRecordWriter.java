package com.abk.record.writer;

import com.abk.analyzer.TextModel;
import com.abk.record.RecordWriter;

public abstract class SQLRecordWriter extends RecordWriter {
	private String connection,userName,pass;
	private String dbName;
	public SQLRecordWriter(String connection,String userName,String pass){
		this.connection = connection;
		this.userName = userName;
		this.pass = pass;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public SQLRecordWriter(){
		
	}
	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	public void use(String dbName){
		this.dbName = dbName;
	}
	public abstract void insert(TextModel model);
	public abstract void update(TextModel model);
	public abstract void delete(TextModel model);
	public abstract void insertInsignificantWords(String... words);
}
