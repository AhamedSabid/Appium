package com.rvsappiumautomation.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.rvsappiumautomation.utils.AutomationConstants;

public class ExcelDataHandler {
	//public static String ExcelFilePath = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "ExcelFile_Path");

	/**
	 * Function to read data from a particular cell from an excel sheet
	 * 
	 * @author rahul.raman
	 * @param FileName
	 * @param SheetName
	 * @param rowNumber
	 * @param columnNumber
	 * @return
	 * @throws IOException
	 */
	public static String readExceldata(String FileName, String SheetName, int rowNumber, int columnNumber)
			throws IOException {

		String cellValue = null;
		String ExcelFilePath = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "ExcelFile_Path");

		try {
			File file = new File(System.getProperty("user.dir") + ExcelFilePath + FileName + ".xlsx");
			FileInputStream inputStream = new FileInputStream(file);
			Workbook excelHandlingBook = new XSSFWorkbook(inputStream);

			Sheet sheetDetails = excelHandlingBook.getSheet(SheetName);
			Row rowDetails = sheetDetails.getRow(rowNumber);

			if (rowDetails == null) {
				System.out.println("No data present in the Row, please check the cell coordinates are proper?");
				return "";
			}

			else {
				Cell cellDetails = rowDetails.getCell(columnNumber);
				if (cellDetails == null) {
					System.out.println("No data present in the cell, please check the cell coordinates are proper?");
					return "";
				}

				cellValue = rowDetails.getCell(columnNumber).getStringCellValue();
				System.out.println("Data in the required cell is = " + cellValue);
				inputStream.close();

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return cellValue;
	}

	/**
	 * Function to write data to a particular cell in excel
	 * 
	 * @author rahul.raman
	 * @param FileName
	 * @param SheetName
	 * @param rowNumber
	 * @param columnNumber
	 * @return
	 * @throws IOException
	 */
	public static void writeExceldata(String FileName, String SheetName, int rowNumber, int columnNumber, String Value)
			throws IOException {

		String ExcelFilePath = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "ExcelFile_Path");
		try {
			File file = new File(System.getProperty("user.dir") + ExcelFilePath + FileName + ".xlsx");
			FileInputStream inputStream = new FileInputStream(file);
			Workbook excelHandlingBook = new XSSFWorkbook(inputStream);

			Sheet sheetDetails = excelHandlingBook.getSheet(SheetName);

			Row rowDetails = sheetDetails.getRow(rowNumber);
			if (rowDetails == null) {
				rowDetails = sheetDetails.createRow(rowNumber);
			}

			Cell cellDetails = rowDetails.createCell(columnNumber);
			cellDetails.setCellType(cellDetails.CELL_TYPE_STRING);
			cellDetails.setCellValue(Value);
			FileOutputStream outputStream = new FileOutputStream(file);
			excelHandlingBook.write(outputStream);
			outputStream.close();
			System.out.println("Successfully wrote the details in to Excel");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Function to search for a data in excel
	 * 
	 * @author rahul.raman
	 * @param FileName
	 * @param SheetName
	 * @param rowNumber
	 * @param columnNumber
	 * @return
	 * @throws IOException
	 */
	public static boolean VerifyThePresenceOfDataInExcel(String FileName, String SheetName, String Value)
			throws IOException {
		String ExcelFilePath = PropertiesDataHandler.getProperty(AutomationConstants.CONFIG_FILE, "ExcelFile_Path");
		boolean isPresent = false;
		try {

			File file = new File(System.getProperty("user.dir") + ExcelFilePath + FileName + ".xlsx");
			FileInputStream inputStream = new FileInputStream(file);
			Workbook excelHandlingBook = new XSSFWorkbook(inputStream);

			Sheet sheetDetails = excelHandlingBook.getSheet(SheetName);
			int TotalRow = sheetDetails.getLastRowNum();

			if (TotalRow != 0) {

				for (int i = 0; i <= TotalRow; i++) {
					Row rowDetails = sheetDetails.getRow(i);
					int TotalColumn = rowDetails.getLastCellNum();
					for (int j = 0; j <= TotalColumn; j++) {
						Cell cellDetails = rowDetails.getCell(j);
						if (cellDetails != null) {
							String cellValue = rowDetails.getCell(j).getStringCellValue();
							if (cellValue.contains(Value)) {
								System.out.println("Searched value " + Value + " is present in the Excel");
								isPresent = true;
								i = i + 1;
								j = j + 1;

								System.out.println("Row number is:" + i + "  and Column number is:" + j);
								return isPresent;
							}
						}
					}
				}
			}
		} catch (Throwable e) {
			System.out.println(e);
		}
		return isPresent;
	}
}
