package com.demo.app;

import java.awt.Font;
import java.io.File; 
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;

public class ExcelExport {
		
	 private static String[] columns = {"OriginCity","DestinationCity","DepartureTime"};
	 
		public void Excel() 
		    { 
			AllCities Allcities = null;
		        // Blank workbook 
		        XSSFWorkbook workbook = new XSSFWorkbook(); 
		  
		        // Create a blank sheet 
		        XSSFSheet sheet = workbook.createSheet("Route Details"); 
		  
		        // This data needs to be written (Object[]) 
		        /*Map<String, Object[]> data = new TreeMap<String, Object[]>(); 
		        data.put("1", new Object[]{ "ID", "NAME", "LASTNAME" }); 
		        data.put("2", new Object[]{ 1, "Pankaj", "Kumar" }); 
		        data.put("3", new Object[]{ 2, "Prakashni", "Yadav" }); 
		        data.put("4", new Object[]{ 3, "Ayan", "Mondal" }); 
		        data.put("5", new Object[]{ 4, "Virat", "kohli" });*/ 
		        try {
					File file = new ClassPathResource("city.json").getFile();
					Allcities = new ObjectMapper().readValue(file, AllCities.class);
					List<City> listofcity = Allcities.AllTheCities;
					
					XSSFFont headerFont = workbook.createFont();
			        headerFont.setBold(true);
			        headerFont.setFontHeightInPoints((short) 14);
			        headerFont.setColor(IndexedColors.RED.getIndex());

			        // Create a CellStyle with the font
			        CellStyle headerCellStyle = workbook.createCellStyle();
			        headerCellStyle.setFont(headerFont);

					Row headerRow = sheet.createRow(0);

			        // Create cells
			        for(int i = 0; i < columns.length; i++) {
			            Cell cell = headerRow.createCell(i);
			            cell.setCellValue(columns[i]);
			            cell.setCellStyle(headerCellStyle);
			        }
		        // Iterate over data and write to sheet 
		        int rownum = 1; 
		        for (City data : listofcity) { 
		            // this creates a new row in the sheet 
		            Row row = sheet.createRow(rownum++); 
		            row.createCell(0)
                    .setCellValue(data.getOriginCity());

		            row.createCell(1)
                    .setCellValue(data.getDestinationCity());
		            
		            row.createCell(2)
                    .setCellValue(data.getDepartureTime());
 
		        }  
		        
		        for(int i = 0; i < columns.length; i++) {
		            sheet.autoSizeColumn(i);
		        }
		        
		            // this Writes the workbook gfgcontribute 
		            FileOutputStream out = new FileOutputStream(new File("E:\\saurabh.xlsx")); 
		            workbook.write(out); 
		            out.close();
		            workbook.close();
		            System.out.println("gfgcontribute.xlsx written successfully on disk."); 
		        } 
		        catch (Exception e) { 
		            e.printStackTrace(); 
		        } 
		    } 

}
