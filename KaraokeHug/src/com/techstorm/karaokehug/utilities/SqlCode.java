package com.techstorm.karaokehug.utilities;

public class SqlCode {

	public static String encode(String str) {
		return str.replace("'", "''");
	}
	
}
