package com.qa.amazon.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {
	
	
	public static final String FILE_PATH =System.getProperty("user.dir")+"//src//resources//testdata"+"//AmazonTestData.xlsx";
	public static Workbook wb;
	public static Sheet sheet;
	
	public static Object[][] getTestData(String sheetName){
		
		Object data [][] = null; 
		
		try {
			FileInputStream fis = new FileInputStream(FILE_PATH);
			wb = WorkbookFactory.create(fis);
			sheet = wb.getSheet(sheetName.trim());
			
			data = new Object [sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
			
			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
					data[i][j] = sheet.getRow(i+1).getCell(j).getStringCellValue();
				}
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
		
	}
	

}
