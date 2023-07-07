package com.rvsappiumautomation.data;

import com.rvsappiumautomation.utils.AutomationConstants;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rvsappiumautomation.data.PropertiesDataHandler;

public class CSVDataHandler {

	public static ArrayList<String> readCSVdata(int columnIndex) throws IOException, CsvValidationException {
		ArrayList<String> datas = new ArrayList<String>();
		String csvFile_Path= PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "csvFile_Path");
		CSVReader reader = new CSVReader(new FileReader(csvFile_Path));
		String [] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			datas.add(nextLine[columnIndex - 1].trim());
		}
		return datas;
		
	}
	

}
