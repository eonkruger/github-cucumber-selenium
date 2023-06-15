package com.m2at.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {
	
	File file;
	String filePath;
	FileInputStream fis;
	Properties properties;

	public LoadProperties(String filePath) {
		this.filePath = filePath;
	}
	
	public Properties loadProperty() throws IOException {
		try {
			// File object specifies the path
			file = new File(filePath);
			
			// Establish connection with the file
			fis = new FileInputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			// Load properties
			properties = new Properties();
			properties.load(fis);
		} catch (IOException e) {
			throw new IOException();
		}
		
		return properties;
	}
}
