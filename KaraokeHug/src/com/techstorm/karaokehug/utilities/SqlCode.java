package com.techstorm.karaokehug.utilities;

public class SqlCode {

	public static String encode(String str) {
		return str.replace("'", "''");
	}
	
}
// select * from song where s = 'I''m' 