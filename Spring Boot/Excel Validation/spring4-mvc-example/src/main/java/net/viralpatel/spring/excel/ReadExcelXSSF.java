package net.viralpatel.spring.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelXSSF {
    
    public static String baseExcelName = "E://Temp//UploadFiles//Book3.xlsx";
    
  public  Map<String, BaseExcel> baseExcel = new HashMap<String, BaseExcel>();
    
    public List<String> baseExcelIdOne = new ArrayList<String>();
	
   public List<String> baseExcelIdTwo = new ArrayList<String>();
   
  public List<String> headers = new ArrayList<>();
   
   private String fileOne;
   private String fileTwo;
   
   public String getFileOne() {
    return fileOne;
}


public void setFileOne(String fileOne) {
    this.fileOne = fileOne;
}


public String getFileTwo() {
    return fileTwo;
}


public void setFileTwo(String fileTwo) {
    this.fileTwo = fileTwo;
}


ReadCellContent readCellContent = new ReadCellContent();
   
   int AssociateIdIndexNo=0;
   int WorkerIdIndexNo = 0;
   
   String idOne = "associate id";
   String idTwo = "worker id";
   
   String copy="";
   String excelThreeData = "";
   
    
    XSSFWorkbook workbook = null;
    XSSFRow row;
    XSSFCell cell;
    
    
    public ReadExcelXSSF(){
	
    }
    
    {
	try {
	    read();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    

    public void read() throws IOException {
    try {
	FileInputStream fis = new FileInputStream(new File(baseExcelName));
    workbook = new XSSFWorkbook(fis);
    XSSFSheet sheet = workbook.getSheetAt(0);
    

     
	for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
		row = sheet.getRow(i);
		for (int j = 0; j < row.getLastCellNum(); j++) {
		    cell = row.getCell(j);
		    if(cell!=null) {
		    excelThreeData = readCellContent.readCell(cell);}
		    if (excelThreeData.toLowerCase().equalsIgnoreCase(idOne)) {
			AssociateIdIndexNo = j;
		    }

		    else if (excelThreeData.toLowerCase().equalsIgnoreCase(idTwo)) {
			WorkerIdIndexNo = j;
		    }

		    if (AssociateIdIndexNo < WorkerIdIndexNo) {
			if (j == AssociateIdIndexNo && i > 0) {
			    copy = excelThreeData;
			    baseExcelIdOne.add(copy);
			} else if (j == WorkerIdIndexNo && i > 0) {
			    String key = copy;
			    baseExcelIdTwo.add(excelThreeData);
			    baseExcel.put(key, new BaseExcel(excelThreeData));
			    System.out.println("baseExcel" + baseExcel.put(key, new BaseExcel(excelThreeData)));

			}
		    }

		    else if (WorkerIdIndexNo < AssociateIdIndexNo) {
			if (j == WorkerIdIndexNo && i > 0) {
			    copy = excelThreeData;
			    baseExcelIdTwo.add(copy);
			} else if (j == AssociateIdIndexNo && i > 0) {
			    String key = copy;
			    baseExcelIdOne.add(excelThreeData);
			    baseExcel.put(key, new BaseExcel(excelThreeData));
			    System.out.println("baseExcel" + baseExcel.put(key, new BaseExcel(excelThreeData)));
			    System.out.println("base excel over");
			}

			}
		    }

		}
	    }
    
    finally

	{
	    if (workbook != null) {
		workbook.close();
	    }
	}
    }
    
   public void readHeaders(String fileOne) throws IOException{
       try {
       FileInputStream fis = new FileInputStream(new File(fileOne));
       workbook = new XSSFWorkbook(fis);
       XSSFSheet sheet = workbook.getSheetAt(0);
	    String readData = "";

	    row = sheet.getRow(0);

	    if (row != null) {
		for (int j = 0; j < row.getLastCellNum(); j++) {
		    cell = row.getCell(j);
			readData = readCellContent.readCell(cell);
			boolean wordMatched = false;
			if ((readData.toLowerCase().equalsIgnoreCase(idOne) || (readData.toLowerCase().equalsIgnoreCase("associate name")))
				|| (readData.toLowerCase().equalsIgnoreCase("worker"))
				|| (readData.toLowerCase().equalsIgnoreCase(idTwo))) {
			    wordMatched = true;

			}
			if ((wordMatched == true)) {
			    wordMatched = false;
			    continue;

			}
		    if (cell != null) {
			headers.add(readData);
			/*MyCell element = new MyCell();
			element.setContent(readCellContent.readCell(cell));
			System.out.println(element);
			headers.get(0).add(element);
			element = null;*/
		    } 
		}
	    }
       }
       finally {
	   if(workbook!= null) {
	       workbook.close();
	   }
       }
       
   }
    
    
    

}
