package net.viralpatel.spring.excel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelPOIHelper extends ReadExcelXSSF {

    String idOne = "associate id";
    String idTwo = "worker id";
    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    
    public ExcelPOIHelper() {

    }
    public Map<Integer, List<MyCell>> readExcel(String fileOne, String fileTwo,
	    String UPLOADED_FOLDER2, String selectedHeaderExcelOne, String selectedHeaderExcelTwo, String projectId, String reportType) throws IOException {
	Map<Integer, List<MyCell>> data = new LinkedHashMap<>();
	FileInputStream fis = new FileInputStream(new File(fileOne));
	FileInputStream fis1 = new FileInputStream(new File(fileTwo));
	FileInputStream fis2 = new FileInputStream(new File(UPLOADED_FOLDER2));
	if (fileOne.endsWith(".xls")) {
	    data = readHSSFWorkbook(fis, fis1, fis2, fileOne, fileTwo, UPLOADED_FOLDER2,selectedHeaderExcelOne, selectedHeaderExcelTwo,projectId,reportType);
	} else if (fileOne.endsWith(".xlsx")) {
	    data = readXSSFWorkbook(fis, fis1, fis2, fileOne, fileTwo, selectedHeaderExcelOne, selectedHeaderExcelTwo, projectId,reportType);
	}
	int maxNrCols = data.values().stream().mapToInt(List::size).max().orElse(0);
	data.values().stream().filter(ls -> ls.size() < maxNrCols).forEach(ls -> {
	    IntStream.range(ls.size(), maxNrCols).forEach(i -> ls.add(new MyCell("")));
	});
	return data;
    }

    private String readCellContent(Cell cell) {
	String content;
	switch (cell.getCellTypeEnum()) {
	case STRING:
	    content = cell.getStringCellValue();
	    break;
	case NUMERIC:
	    if (DateUtil.isCellDateFormatted(cell)) {
		content = cell.getDateCellValue() + "";
	    } else {
		content = NumberToTextConverter.toText(cell.getNumericCellValue()) + "";
	    }
	    break;
	case BOOLEAN:
	    content = cell.getBooleanCellValue() + "";
	    break;
	case FORMULA:
	    content = cell.getCellFormula() + "";
	    break;
	default:
	    content = "";
	}
	return content;
    }

    private Map<Integer, List<MyCell>> readHSSFWorkbook(FileInputStream fis, FileInputStream fis1,
	    FileInputStream fis2, String UPLOADED_FOLDER, String UPLOADED_FOLDER1, String UPLOADED_FOLDER2,String selectedHeaderExcelOne, String selectedHeaderExcelTwo, String projectId,String reportType) throws IOException {
	HSSFWorkbook ctsWorkBook = null;
	HSSFWorkbook walmartWorkBook = null;
	HSSFWorkbook baseWorkBook = null;
	HSSFRow row;
	HSSFCell cell;
	TreeMap<String,AggregatedData> dailyData =new TreeMap<String,AggregatedData>();
	TreeMap<String,WeekEndData> weekEndData =new TreeMap<String,WeekEndData>();
	Map<Integer, List<MyCell>> data = new LinkedHashMap<>();
	String excelData = "";
	String excelComparatorData = "";
	String excelDateData = "";
	String excelNameData = "";
	String projectIdData = "";
	String weekEndDate = "";
	int excelOneIdIndexNo = 0;
	int excelOnedateIndexNo = 0;
	int excelTwoIdIndexNo = 0;
	int excelOneComparatorIndexNo = 0;
	int excelTwoComparatorIndexNo = 0;
	int excelTwoDateIndexNo = 0;
	int excelOneNameIndexNo = 0;
	int projectIdIndexNo = 0;
	int excelOneWeekEndIndexNo = 0;
	int excelTwoWeekEndIndexNo = 0;
	int excelThreeAssociateIdIndexNo = 0;
	int excelThreeWorkerIdIndexNo = 0;
	int length=0;
	int hours=0;
	List<CTSExcel> cognizantList = new ArrayList<CTSExcel>();
	Boolean check = false;
	List<WalmartExcel> walmartList = new ArrayList<WalmartExcel>();
	
	try {
	    ctsWorkBook = new HSSFWorkbook(fis);
	    walmartWorkBook = new HSSFWorkbook(fis1);
	    baseWorkBook = new HSSFWorkbook(fis2);
	    HSSFSheet ctsSheet = ctsWorkBook.getSheetAt(0);
	    HSSFSheet walmartSheet = walmartWorkBook.getSheetAt(0);
	    HSSFSheet baseSheet = baseWorkBook.getSheetAt(0);
	    
	    for (int i = baseSheet.getFirstRowNum(); i <= baseSheet.getLastRowNum(); i++) {
		row = baseSheet.getRow(i);
		for (int j = 0; j < row.getLastCellNum(); j++) {
		    cell = row.getCell(j);
		    excelThreeData = readCellContent(cell);
		    if (excelThreeData.toLowerCase().equalsIgnoreCase(idOne)) {
			excelThreeAssociateIdIndexNo = j;
		    }

		    else if (excelThreeData.toLowerCase().equalsIgnoreCase(idTwo)) {
			excelThreeWorkerIdIndexNo = j;
		    }

		    if (excelThreeAssociateIdIndexNo < excelThreeWorkerIdIndexNo) {
			if (j == excelThreeAssociateIdIndexNo && i > 0) {
			    copy = excelThreeData;
			    baseExcelIdOne.add(copy);
			} else if (j == excelThreeWorkerIdIndexNo && i > 0) {
			    String key = copy;
			    baseExcelIdTwo.add(excelThreeData);
			    baseExcel.put(key, new BaseExcel(excelThreeData));
			    System.out.println("baseExcel" + baseExcel.put(key, new BaseExcel(excelThreeData)));

			}
		    }

		    else if (excelThreeWorkerIdIndexNo < excelThreeAssociateIdIndexNo) {
			if (j == excelThreeWorkerIdIndexNo && i > 0) {
			    copy = excelThreeData;
			    baseExcelIdTwo.add(copy);
			} else if (j == excelThreeAssociateIdIndexNo && i > 0) {
			    String key = copy;
			    baseExcelIdOne.add(excelThreeData);
			    baseExcel.put(key, new BaseExcel(excelThreeData));
			    System.out.println("baseExcel" + baseExcel.put(key, new BaseExcel(excelThreeData)));

			}
		    }

		}
	    }
	    
	    // ctsExcel reading operation
	    row = ctsSheet.getRow(0);
	    if (row != null) {
		for (int j = 0; j < row.getLastCellNum(); j++) {
		    cell = row.getCell(j);
		    if(cell!=null) {
		    excelData = readCellContent(cell);
		    }
		    if (excelData.toLowerCase().equalsIgnoreCase(idOne)) {
			excelOneIdIndexNo = j;
		    } else if (excelData.toLowerCase().equalsIgnoreCase(idTwo)) {
			excelOneIdIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase(selectedHeaderExcelOne)) {
			excelOneComparatorIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase("associate name")) {
			excelOneNameIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase("reporting date")) {
			excelOnedateIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase("project id")) {
			projectIdIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase("period end date")) {
			excelOneWeekEndIndexNo = j;
		    }

		}
	    }

	    for (String associateId : baseExcelIdOne) {
		 List<CTSExcelChildData> objectChild = new ArrayList<CTSExcelChildData>();
		 CTSExcelChildData obj;
		for (int i = 1; i <= ctsSheet.getLastRowNum(); i++) {
		     obj = new CTSExcelChildData();
		     row = ctsSheet.getRow(i);
		     cell = row.getCell(excelOneIdIndexNo);
		    excelData = readCellContent(cell);
		    if (associateId.equalsIgnoreCase(excelData)) {
			check = true;
			cell = row.getCell(excelOneComparatorIndexNo);
			if(cell!=null) {
			excelComparatorData = readCellContent(cell);}
			 length = excelComparatorData.length();
			if(StringUtils.isNumeric(excelComparatorData) && length ==1) {
			    hours = Integer.parseInt(excelComparatorData);
			}
			cell = row.getCell(excelOnedateIndexNo);
			if(cell!=null) {
			excelDateData = readCellContent(cell);}
			cell = row.getCell(excelOneNameIndexNo);
			if(cell!=null) {
			excelNameData = readCellContent(cell);}
			cell = row.getCell(projectIdIndexNo);
			if(cell!=null) {
			projectIdData = readCellContent(cell);}
			cell = row.getCell(excelOneWeekEndIndexNo);
			if(cell!=null) {
			weekEndDate = readCellContent(cell);}
			obj.setProjectId(projectIdData);
			obj.setDate(excelDateData.intern());
			obj.setTimeQty(excelComparatorData.intern());
			obj.setWeekend(weekEndDate.intern());
			obj.setHours(hours);
			objectChild.add(obj);
			System.out.println("filter process excel One");
		    }
		}
		if (check) {
		    CTSExcel data1 = new CTSExcel();
		    data1.setAssoicateId(associateId.intern());
		    data1.setAssociateName(excelNameData.intern());
		    data1.setChildData(objectChild);
		    cognizantList.add(data1);
		    data1 = null;
		    objectChild = null;
		    check = false;
		    obj = null;
		}
	    }
	    
	    check = false;

	    //walmartExcel reading operation
	    
	    row = walmartSheet.getRow(0);
	    if (row != null) {
		for (int j = 0; j < row.getLastCellNum(); j++) {
		    cell = row.getCell(j);
		    if(cell!=null) {
		    excelData = readCellContent(cell);
		    }
		    if (excelData.toLowerCase().equalsIgnoreCase(idOne)) {
			excelTwoIdIndexNo = j;
		    } else if (excelData.toLowerCase().equalsIgnoreCase(idTwo)) {
			excelTwoIdIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase(selectedHeaderExcelTwo)) {
			excelTwoComparatorIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase("time entry date")) {
			excelTwoDateIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase("time sheet end date")) {
			excelTwoWeekEndIndexNo = j;
		    }
		    
		}
	    }

	    for (String workerId : baseExcelIdTwo) {
		List<WalmartChildData> walmartChildData = new LinkedList<WalmartChildData>();
		WalmartChildData obj;
		for (int i = 1; i <= walmartSheet.getLastRowNum(); i++) {
		     obj = new WalmartChildData();
		    row = walmartSheet.getRow(i);
		    cell = row.getCell(excelTwoIdIndexNo);
		    excelData = readCellContent(cell);
		    if (workerId.equalsIgnoreCase(excelData)) {
			check = true;
			cell = row.getCell(excelTwoComparatorIndexNo);
			if(cell!=null) {
			excelComparatorData = readCellContent(cell);}
			 length = excelComparatorData.length();
			if(StringUtils.isNumeric(excelComparatorData) && length ==1) {
			    hours = Integer.parseInt(excelComparatorData);
			}
			cell = row.getCell(excelTwoDateIndexNo);
			if(cell!=null) {
			excelDateData = readCellContent(cell);}
			cell = row.getCell(excelTwoWeekEndIndexNo);
			if(cell!=null) {
			weekEndDate = readCellContent(cell);}
			obj.setDate(excelDateData.intern());
			obj.setTimeQty(excelComparatorData.intern());
			obj.setWeekend(weekEndDate);
			obj.setHours(hours);
			walmartChildData.add(obj);
			System.out.println("filter process excel Two");
		    }
		}
		if (check) {
		    WalmartExcel walmartExcel = new WalmartExcel();
		    walmartExcel.setWorkerId(workerId.intern());
		    walmartExcel.setWalmartChildData(walmartChildData);
		    walmartList.add(walmartExcel);
		    walmartExcel = null;
		    walmartChildData = null;
		    check = false;
		    obj = null;
		}
	    }

                 //Prepare the data for UI (daily basis)
	    
		if (cognizantList != null && walmartList != null && reportType.equalsIgnoreCase("daily")) {
		    dailyData = daily(cognizantList, walmartList, projectId);
		       MyCell element1 = new MyCell();
		       MyCell element2 = new MyCell();
		       MyCell element7 = new MyCell();
		       MyCell element8 = new MyCell();
		       MyCell element9 = new MyCell();
		       MyCell element10 = new MyCell();
		int i=0;
		int columnNo=0;
		i=0;
		    data.put(i, new ArrayList<>());
		    element1.setContent("Associate Id");
		    data.get(i).add(columnNo, element1);
		    columnNo++;
		    element2.setContent("Associate Name");
		    data.get(i).add(columnNo, element2);
		    columnNo++;
	            element7.setContent("Worker Id");
	            data.get(i).add(columnNo, element7);
	            columnNo++;
	            element10.setContent("Entry Date");
	            data.get(i).add(columnNo, element10);
	            columnNo++;
		    element8.setContent(selectedHeaderExcelOne);
		    data.get(i).add(columnNo, element8);
		    columnNo++;
		    element9.setContent(selectedHeaderExcelTwo);
		    data.get(i).add(columnNo, element9);
		    columnNo++;
		    i++;
			  for (Map.Entry<String, AggregatedData> entry : dailyData.entrySet()) {
			       columnNo = 0;
				       MyCell element = new MyCell();
				       MyCell element3 = new MyCell();
				       MyCell element4 = new MyCell();
				       MyCell element5 = new MyCell();
				       MyCell element6 = new MyCell();
				       MyCell element11 = new MyCell();
				       MyCell element12 = new MyCell();
			    data.put(i, new ArrayList<>());
			    element4.setContent(entry.getValue().getAssociateId().intern());
			    data.get(i).add(columnNo, element4);
			    columnNo++;
			    element5.setContent(entry.getValue().getAssociateName().intern());
			    data.get(i).add(columnNo, element5);
			    columnNo++;
		            element6.setContent(entry.getValue().getWalmartId().intern());
		            data.get(i).add(columnNo, element6);
		            columnNo++;
			    element3.setContent(entry.getValue().getDate().intern());
			    data.get(i).add(columnNo, element3);
			    columnNo++;
			    element.setContent(entry.getValue().getTimeQty().intern());
			    data.get(i).add(columnNo, element);
			    columnNo++;
			    element11.setContent(entry.getValue().gettimeQtyTwo().intern());
			    data.get(i).add(columnNo, element11);
			    columnNo++;
			    element12.setContent(entry.getValue().getMatcher().intern());
			    data.get(i).add(columnNo, element12);
			    columnNo++;
			    i++;
			    element = null;
			    element3 = null;
			    element4 = null;
			    element5 = null;
			    element6 = null;
			    element1=null;
			    element2=null;
			    element7=null;
			    element8=null;
			    element9=null;
				}
		
	    }
		
		// prepare the data for UI (weekly basis)  
		
		else if(cognizantList != null && walmartList != null && reportType.equalsIgnoreCase("weekly")) {
		   weekEndData =  weekEnd(cognizantList, walmartList, projectId);
		   MyCell element1 = new MyCell();
		   MyCell element2 = new MyCell();
		   MyCell element3 = new MyCell();
		   MyCell element4 = new MyCell();
		   MyCell element5 = new MyCell();
		   MyCell element6 = new MyCell();
		   int i=0;
		   int columnNo=0;
		   i=0;
		    data.put(i, new ArrayList<>());
		    element1.setContent("Associate Id");
		    data.get(i).add(columnNo, element1);
		    columnNo++;
		    element2.setContent("Associate Name");
		    data.get(i).add(columnNo, element2);
		    columnNo++;
	            element3.setContent("Worker Id");
	            data.get(i).add(columnNo, element3);
	            columnNo++;
	            element4.setContent("WeekEnd Date");
	            data.get(i).add(columnNo, element4);
	            columnNo++;
		    element5.setContent(selectedHeaderExcelOne);
		    data.get(i).add(columnNo, element5);
		    columnNo++;
		    element6.setContent(selectedHeaderExcelTwo);
		    data.get(i).add(columnNo, element6);
		    columnNo++;
		    i++;
		    
		   for (Map.Entry<String, WeekEndData> entry : weekEndData.entrySet()) {
		   columnNo = 0;
		   MyCell element7 = new MyCell();
		   MyCell element8 = new MyCell();
		   MyCell element9 = new MyCell();
		   MyCell element10 = new MyCell();
		   MyCell element11 = new MyCell();
		   MyCell element12 = new MyCell();
		   MyCell element13 = new MyCell();
		   
		    data.put(i, new ArrayList<>());
		    element7.setContent(entry.getValue().getAssociateId().intern());
		    data.get(i).add(columnNo, element7);
		    columnNo++;
		    element8.setContent(entry.getValue().getAssociateName().intern());
		    data.get(i).add(columnNo, element8);
		    columnNo++;
	            element9.setContent(entry.getValue().getWorkerId().intern());
	            data.get(i).add(columnNo, element9);
	            columnNo++;
	            element10.setContent(entry.getValue().getWeekEnd().intern());
	            data.get(i).add(columnNo, element10);
	            columnNo++;
	            element11.setContent(String.valueOf(entry.getValue().getCtsHours()));
	            data.get(i).add(columnNo, element11);
	            columnNo++;
	            element12.setContent(String.valueOf(entry.getValue().getWalmartHours()));
	            data.get(i).add(columnNo, element12);
	            columnNo++;
	            element13.setContent(entry.getValue().getMatcher());
	            data.get(i).add(columnNo, element13);
	            columnNo++;
	            i++;
	            
	            element1 = null;
	            element2 = null;
	            element3 = null;
	            element4 = null;
	            element5 = null;
	            element6 = null;
	            element7 = null;
	            element8 = null;
	            element9 = null;
	            element10 = null;
	            element11 = null;
	            element12 = null;
	            element13 = null;

		   }
		   }
	} finally

	{
	    if (ctsWorkBook != null) {
		ctsWorkBook.close();
	    }
	    if(walmartWorkBook != null) {
		walmartWorkBook.close();
	    }
	    if(baseWorkBook != null) {
		baseWorkBook.close();
	    }
	    System.gc();
	}
	return data;
    }

    public Map<Integer, List<MyCell>> readXSSFWorkbook(FileInputStream fis, FileInputStream

    fis1, FileInputStream fis2, String UPLOADED_FOLDER, String UPLOADED_FOLDER1, String selectedHeaderExcelOne, String selectedHeaderExcelTwo, String projectId,String reportType) throws IOException {
	XSSFWorkbook ctsWorkBook = null;
	XSSFWorkbook walmartWorkBook = null;
	//XSSFWorkbook workbook2 = null;
	XSSFRow row;
	XSSFCell cell;
	List<String> baseExcelIdOne = new ArrayList<String>();
	List<String> baseExcelIdTwo = new ArrayList<String>();
	TreeMap<String,AggregatedData> dailyData =new TreeMap<String,AggregatedData>();
	TreeMap<String,WeekEndData> weekEndData =new TreeMap<String,WeekEndData>();
	Map<Integer, List<MyCell>> data = new LinkedHashMap<>();
	String excelData = "";
	String excelComparatorData = "";
	String excelDateData = "";
	String excelNameData = "";
	String projectIdData = "";
	String weekEndDate = "";
	int excelOneIdIndexNo = 0;
	int excelOnedateIndexNo = 0;
	int excelTwoIdIndexNo = 0;
	int excelOneComparatorIndexNo = 0;
	int excelTwoComparatorIndexNo = 0;
	int excelTwoDateIndexNo = 0;
	int excelOneNameIndexNo = 0;
	int projectIdIndexNo = 0;
	int excelOneWeekEndIndexNo = 0;
	int excelTwoWeekEndIndexNo = 0;
	int length=0;
	int hours=0;
	List<CTSExcel> cognizantList = new ArrayList<CTSExcel>();
	Boolean check = false;
	List<WalmartExcel> walmartList = new ArrayList<WalmartExcel>();
	
	ReadExcelXSSF readBaseExcel = new ReadExcelXSSF();
	
	try {

	    ctsWorkBook = new XSSFWorkbook(fis);
	    walmartWorkBook = new XSSFWorkbook(fis1);
	   // workbook2 = new XSSFWorkbook(fis2);
	    XSSFSheet ctsSheet = ctsWorkBook.getSheetAt(0);
	    XSSFSheet walmartSheet = walmartWorkBook.getSheetAt(0);
              
	    baseExcelIdOne = readBaseExcel.baseExcelIdOne;
	    baseExcelIdTwo = readBaseExcel.baseExcelIdTwo;
	    
	 // ctsExcel reading operation
	    
	    row = ctsSheet.getRow(0);
	    if (row != null) {
		for (int j = 0; j < row.getLastCellNum(); j++) {
		    cell = row.getCell(j);
		    if(cell!=null) {
		    excelData = readCellContent(cell);
		    }
		    if (excelData.toLowerCase().equalsIgnoreCase(idOne)) {
			excelOneIdIndexNo = j;
		    } else if (excelData.toLowerCase().equalsIgnoreCase(idTwo)) {
			excelOneIdIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase(selectedHeaderExcelOne)) {
			excelOneComparatorIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase("associate name")) {
			excelOneNameIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase("reporting date")) {
			excelOnedateIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase("project id")) {
			projectIdIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase("period end date")) {
			excelOneWeekEndIndexNo = j;
		    }

		}
	    }

	    for (String associateId : baseExcelIdOne) {
		 List<CTSExcelChildData> objectChild = new ArrayList<CTSExcelChildData>();
		 CTSExcelChildData obj;
		for (int i = 1; i <= ctsSheet.getLastRowNum(); i++) {
		     obj = new CTSExcelChildData();
		     row = ctsSheet.getRow(i);
		     cell = row.getCell(excelOneIdIndexNo);
		    excelData = readCellContent(cell);
		    if (associateId.equalsIgnoreCase(excelData)) {
			check = true;
			cell = row.getCell(excelOneComparatorIndexNo);
			if(cell!=null) {
			excelComparatorData = readCellContent(cell);}
			 length = excelComparatorData.length();
			if(StringUtils.isNumeric(excelComparatorData) && length ==1) {
			    hours = Integer.parseInt(excelComparatorData);
			}
			cell = row.getCell(excelOnedateIndexNo);
			if(cell!=null) {
			excelDateData = readCellContent(cell);}
			cell = row.getCell(excelOneNameIndexNo);
			if(cell!=null) {
			excelNameData = readCellContent(cell);}
			cell = row.getCell(projectIdIndexNo);
			if(cell!=null) {
			projectIdData = readCellContent(cell);}
			cell = row.getCell(excelOneWeekEndIndexNo);
			if(cell!=null) {
			weekEndDate = readCellContent(cell);}
			obj.setProjectId(projectIdData);
			obj.setDate(excelDateData.intern());
			obj.setTimeQty(excelComparatorData.intern());
			obj.setWeekend(weekEndDate.intern());
			obj.setHours(hours);
			objectChild.add(obj);
			System.out.println("filter process excel One");
		    }
		}
		if (check) {
		    CTSExcel data1 = new CTSExcel();
		    data1.setAssoicateId(associateId.intern());
		    data1.setAssociateName(excelNameData.intern());
		    data1.setChildData(objectChild);
		    cognizantList.add(data1);
		    data1 = null;
		    objectChild = null;
		    check = false;
		    obj = null;
		}
	    }
	    
	    check = false;

	    //walmartExcel reading operation
	    
	    row = walmartSheet.getRow(0);
	    if (row != null) {
		for (int j = 0; j < row.getLastCellNum(); j++) {
		    cell = row.getCell(j);
		    if(cell!=null) {
		    excelData = readCellContent(cell);
		    }
		    if (excelData.toLowerCase().equalsIgnoreCase(idOne)) {
			excelTwoIdIndexNo = j;
		    } else if (excelData.toLowerCase().equalsIgnoreCase(idTwo)) {
			excelTwoIdIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase(selectedHeaderExcelTwo)) {
			excelTwoComparatorIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase("time entry date")) {
			excelTwoDateIndexNo = j;
		    }
		    if (excelData.equalsIgnoreCase("time sheet end date")) {
			excelTwoWeekEndIndexNo = j;
		    }
		    
		}
	    }

	    for (String workerId : baseExcelIdTwo) {
		List<WalmartChildData> walmartChildData = new LinkedList<WalmartChildData>();
		WalmartChildData obj;
		for (int i = 1; i <= walmartSheet.getLastRowNum(); i++) {
		     obj = new WalmartChildData();
		    row = walmartSheet.getRow(i);
		    cell = row.getCell(excelTwoIdIndexNo);
		    excelData = readCellContent(cell);
		    if (workerId.equalsIgnoreCase(excelData)) {
			check = true;
			cell = row.getCell(excelTwoComparatorIndexNo);
			if(cell!=null) {
			excelComparatorData = readCellContent(cell);}
			 length = excelComparatorData.length();
			if(StringUtils.isNumeric(excelComparatorData) && length ==1) {
			    hours = Integer.parseInt(excelComparatorData);
			}
			cell = row.getCell(excelTwoDateIndexNo);
			if(cell!=null) {
			excelDateData = readCellContent(cell);}
			cell = row.getCell(excelTwoWeekEndIndexNo);
			if(cell!=null) {
			weekEndDate = readCellContent(cell);}
			obj.setDate(excelDateData.intern());
			obj.setTimeQty(excelComparatorData.intern());
			obj.setWeekend(weekEndDate);
			obj.setHours(hours);
			walmartChildData.add(obj);
			System.out.println("filter process excel Two");
		    }
		}
		if (check) {
		    WalmartExcel walmartExcel = new WalmartExcel();
		    walmartExcel.setWorkerId(workerId.intern());
		    walmartExcel.setWalmartChildData(walmartChildData);
		    walmartList.add(walmartExcel);
		    walmartExcel = null;
		    walmartChildData = null;
		    check = false;
		    obj = null;
		}
	    }
                
	  //Prepare the data for UI (daily basis)

		if (cognizantList != null && walmartList != null && reportType.equalsIgnoreCase("daily")) {
		    dailyData = daily(cognizantList, walmartList, projectId);
		       MyCell element1 = new MyCell();
		       MyCell element2 = new MyCell();
		       MyCell element7 = new MyCell();
		       MyCell element8 = new MyCell();
		       MyCell element9 = new MyCell();
		       MyCell element10 = new MyCell();
		int i=0;
		int columnNo=0;
		i=0;
		    data.put(i, new ArrayList<>());
		    element1.setContent("Associate Id");
		    data.get(i).add(columnNo, element1);
		    columnNo++;
		    element2.setContent("Associate Name");
		    data.get(i).add(columnNo, element2);
		    columnNo++;
	            element7.setContent("Worker Id");
	            data.get(i).add(columnNo, element7);
	            columnNo++;
	            element10.setContent("Entry Date");
	            data.get(i).add(columnNo, element10);
	            columnNo++;
		    element8.setContent(selectedHeaderExcelOne);
		    data.get(i).add(columnNo, element8);
		    columnNo++;
		    element9.setContent(selectedHeaderExcelTwo);
		    data.get(i).add(columnNo, element9);
		    columnNo++;
		    i++;
			  for (Map.Entry<String, AggregatedData> entry : dailyData.entrySet()) {
			       columnNo = 0;
				       MyCell element = new MyCell();
				       MyCell element3 = new MyCell();
				       MyCell element4 = new MyCell();
				       MyCell element5 = new MyCell();
				       MyCell element6 = new MyCell();
				       MyCell element11 = new MyCell();
				       MyCell element12 = new MyCell();
			    data.put(i, new ArrayList<>());
			    element4.setContent(entry.getValue().getAssociateId().intern());
			    data.get(i).add(columnNo, element4);
			    columnNo++;
			    element5.setContent(entry.getValue().getAssociateName().intern());
			    data.get(i).add(columnNo, element5);
			    columnNo++;
		            element6.setContent(entry.getValue().getWalmartId().intern());
		            data.get(i).add(columnNo, element6);
		            columnNo++;
			    element3.setContent(entry.getValue().getDate().intern());
			    data.get(i).add(columnNo, element3);
			    columnNo++;
			    element.setContent(entry.getValue().getTimeQty().intern());
			    data.get(i).add(columnNo, element);
			    columnNo++;
			    element11.setContent(entry.getValue().gettimeQtyTwo().intern());
			    data.get(i).add(columnNo, element11);
			    columnNo++;
			    element12.setContent(entry.getValue().getMatcher().intern());
			    data.get(i).add(columnNo, element12);
			    columnNo++;
			    i++;
			    element = null;
			    element3 = null;
			    element4 = null;
			    element5 = null;
			    element6 = null;
			    element1=null;
			    element2=null;
			    element7=null;
			    element8=null;
			    element9=null;
				}
		
	    }
		// prepare the data for UI (weekly basis)  
		 
		else if(cognizantList != null && walmartList != null && reportType.equalsIgnoreCase("weekly")) {
		   weekEndData =  weekEnd(cognizantList, walmartList, projectId);
		   MyCell element1 = new MyCell();
		   MyCell element2 = new MyCell();
		   MyCell element3 = new MyCell();
		   MyCell element4 = new MyCell();
		   MyCell element5 = new MyCell();
		   MyCell element6 = new MyCell();
		   int i=0;
		   int columnNo=0;
		   i=0;
		    data.put(i, new ArrayList<>());
		    element1.setContent("Associate Id");
		    data.get(i).add(columnNo, element1);
		    columnNo++;
		    element2.setContent("Associate Name");
		    data.get(i).add(columnNo, element2);
		    columnNo++;
	            element3.setContent("Worker Id");
	            data.get(i).add(columnNo, element3);
	            columnNo++;
	            element4.setContent("WeekEnd Date");
	            data.get(i).add(columnNo, element4);
	            columnNo++;
		    element5.setContent(selectedHeaderExcelOne);
		    data.get(i).add(columnNo, element5);
		    columnNo++;
		    element6.setContent(selectedHeaderExcelTwo);
		    data.get(i).add(columnNo, element6);
		    columnNo++;
		    i++;
		    
		   for (Map.Entry<String, WeekEndData> entry : weekEndData.entrySet()) {
		   columnNo = 0;
		   MyCell element7 = new MyCell();
		   MyCell element8 = new MyCell();
		   MyCell element9 = new MyCell();
		   MyCell element10 = new MyCell();
		   MyCell element11 = new MyCell();
		   MyCell element12 = new MyCell();
		   MyCell element13 = new MyCell();
		   
		    data.put(i, new ArrayList<>());
		    element7.setContent(entry.getValue().getAssociateId().intern());
		    data.get(i).add(columnNo, element7);
		    columnNo++;
		    element8.setContent(entry.getValue().getAssociateName().intern());
		    data.get(i).add(columnNo, element8);
		    columnNo++;
	            element9.setContent(entry.getValue().getWorkerId().intern());
	            data.get(i).add(columnNo, element9);
	            columnNo++;
	            element10.setContent(entry.getValue().getWeekEnd().intern());
	            data.get(i).add(columnNo, element10);
	            columnNo++;
	            element11.setContent(String.valueOf(entry.getValue().getCtsHours()));
	            data.get(i).add(columnNo, element11);
	            columnNo++;
	            element12.setContent(String.valueOf(entry.getValue().getWalmartHours()));
	            data.get(i).add(columnNo, element12);
	            columnNo++;
	            element13.setContent(entry.getValue().getMatcher());
	            data.get(i).add(columnNo, element13);
	            columnNo++;
	            i++;
	            
	            element1 = null;
	            element2 = null;
	            element3 = null;
	            element4 = null;
	            element5 = null;
	            element6 = null;
	            element7 = null;
	            element8 = null;
	            element9 = null;
	            element10 = null;
	            element11 = null;
	            element12 = null;
	            element13 = null;

		   }
		   }
	} finally

	{
	    if (ctsWorkBook != null) {
		ctsWorkBook.close();
	    }
	    if(walmartWorkBook != null) {
		walmartWorkBook.close();
	    }
	    System.gc();
	}
	return data;
    }
    
    // Comparison Logic for daily basis record
    
    private TreeMap<String, AggregatedData> daily(List<CTSExcel> cognizantList, List<WalmartExcel> walmartList, String projectId) {
	HashMap<String, AggregatedData> aggregatedData = new HashMap<String, AggregatedData> ();
	HashMap<String, AggregatedData> missingData = new HashMap<String, AggregatedData> ();
	HashMap<String, AggregatedData> originalData = new HashMap<String, AggregatedData> ();
	TreeMap<String, AggregatedData> sorted = new TreeMap<String, AggregatedData>();
	for (int i = 0; i < cognizantList.size(); i++) {
	    String associateName = cognizantList.get(i).getAssociateName();
	    String associateId = cognizantList.get(i).getAssoicateId();
	    String walmartId = walmartList.get(i).getWorkerId();
	    List<CTSExcelChildData> cognizantChild = cognizantList.get(i).getChildData();
	    List<WalmartChildData> walmartChild = walmartList.get(i).getWalmartChildData();
	    
     
	    cognizantChild.stream().forEach(one -> {
		walmartChild.stream().filter(two -> {
		    return (one.getTimeQty().equals(two.getTimeQty())  && one.getDate().equals(two.getDate()) && one.getProjectId().equals(projectId));
		}).forEach(two -> {
			aggregatedData.put(associateId+one.getDate(),
			   new AggregatedData(associateName,associateId, walmartId,one.getProjectId(), one.getTimeQty(), one.getDate(), two.getTimeQty(), "Matched"));
		});
	    });
	    cognizantChild.stream().forEach(one -> {
		walmartChild.stream().filter(two -> {
		    return  (!one.getTimeQty().equals(two.getTimeQty()) && one.getDate().equals(two.getDate()) && one.getProjectId().equals(projectId));
		}).forEach(two -> {
			aggregatedData.put(associateId+one.getDate(),
			    new AggregatedData(associateName,associateId, walmartId,one.getProjectId(), one.getTimeQty(), one.getDate(), two.getTimeQty(), "UnMatched"));
		});
	    });
	    
	    cognizantChild.stream().forEach(one -> {
		walmartChild.stream().filter(two -> {
		    return  (!one.getDate().contains(two.getDate()) && one.getProjectId().equals(projectId));
		}).forEach(two -> {
			missingData.put(associateId+one.getDate(),
			    new AggregatedData(associateName,associateId, walmartId,one.getProjectId(), one.getTimeQty(),one.getDate(), "", "Missing"));
		});
	    }); 
	    
	}
	    originalData.putAll(missingData);
	    originalData.putAll(aggregatedData);
	    sorted.putAll(originalData);
	    
	System.out.println("final data");
	System.out.println("originalData Size " + originalData.size() + "Data--- "+"Data -- " + originalData.toString());
	System.out.println("originalData Size " + sorted.size() + "Data--- "+"Data -- " + sorted.toString());
	return sorted;
    }
    
 // Comparison Logic for weekly basis record
    
    private TreeMap<String, WeekEndData> weekEnd(List<CTSExcel> cognizantList, List<WalmartExcel> walmartList, String projectId) {
	
	HashMap<String, WeekEndData> weekEndData = new HashMap<String, WeekEndData>();
	HashMap<String, WeekEndData> missingWeekEndData = new HashMap<String, WeekEndData>();
	HashMap<String, WeekEndData> originalWeekEndData = new HashMap<String, WeekEndData> ();
	TreeMap<String, WeekEndData> sorted = new TreeMap<String, WeekEndData> ();
	
	for (int i = 0; i < cognizantList.size(); i++) {
	    String associateName = cognizantList.get(i).getAssociateName();
	    String associateId = cognizantList.get(i).getAssoicateId();
	    String walmartId = walmartList.get(i).getWorkerId();
	    List<CTSExcelChildData> cognizantChild = cognizantList.get(i).getChildData();
	    List<WalmartChildData> walmartChild = walmartList.get(i).getWalmartChildData();
	    
	    Map<String, Integer> cts = cognizantChild.stream().collect(
	                Collectors.groupingBy(CTSExcelChildData::getWeekend, Collectors.summingInt(CTSExcelChildData::getHours)));
	    
	    Map<String, Integer> walmart = walmartChild.stream().collect(
	                Collectors.groupingBy(WalmartChildData::getWeekend, Collectors.summingInt(WalmartChildData::getHours)));
	    
	    
	    cognizantChild.stream().forEach(one -> {
			walmartChild.stream().forEach(two -> {
			    for (Map.Entry<String,Integer> entryCts : cts.entrySet()) { 
				for (Map.Entry<String,Integer> entryWalmart : walmart.entrySet()) {
			    if (one.getWeekend().equals(entryCts.getKey())  && two.getWeekend().equals(entryWalmart.getKey()) && one.getProjectId().equals(projectId) && one.getWeekend().equals(two.getWeekend())){
				if(entryCts.getValue().equals(entryWalmart.getValue()))
				    weekEndData.put(associateId+one.getWeekend(),
				   new WeekEndData(associateId, associateName,walmartId, one.getWeekend(), entryCts.getValue(), entryWalmart.getValue(), "Matched"));
			    }
				}
			    }
			    });
		    });	 
	    
	    cognizantChild.stream().forEach(one -> {
		walmartChild.stream().forEach(two -> {
		    for (Map.Entry<String,Integer> entryCts : cts.entrySet()) { 
			for (Map.Entry<String,Integer> entryWalmart : walmart.entrySet()) {
		    if (one.getWeekend().equals(entryCts.getKey())  && two.getWeekend().equals(entryWalmart.getKey()) && one.getProjectId().equals(projectId) && one.getWeekend().equals(two.getWeekend())) {
			if(!entryCts.getValue().equals(entryWalmart.getValue()))
			    weekEndData.put(associateId+one.getWeekend(),
			   new WeekEndData(associateId, associateName,walmartId, one.getWeekend(), entryCts.getValue(), entryWalmart.getValue(), "UnMatched"));
		    }
			}
		    }
		    });
	    });	  
	    
	    cognizantChild.stream().forEach(one -> {
		walmartChild.stream().forEach(two -> {
		    for (Map.Entry<String,Integer> entryCts : cts.entrySet()) { 
			for (Map.Entry<String,Integer> entryWalmart : walmart.entrySet()) {
		    if (one.getWeekend().equals(entryCts.getKey())  && two.getWeekend().equals(entryWalmart.getKey()) && one.getProjectId().equals(projectId) && !one.getWeekend().contains(two.getWeekend())) {
			    missingWeekEndData.put(associateId+one.getWeekend(),
			   new WeekEndData(associateId, associateName,walmartId, one.getWeekend(), entryCts.getValue(), 0, "Missing"));
		    }
			}
		    }
		    });
	    });	  
	    
	    
	    cts.clear();
	    walmart.clear();
	}
	    originalWeekEndData.putAll(missingWeekEndData);
	    originalWeekEndData.putAll(weekEndData);
	    sorted.putAll(originalWeekEndData);
	    
	    System.out.println("SortedWeekEndData Size " + sorted.size() + "Data--- "+"Data -- " + sorted.toString());
	    System.out.println("originalWeekEndData Size " + originalWeekEndData.size() + "Data--- "+"Data -- " + originalWeekEndData.toString());
	    System.out.println("weekendData Size " + weekEndData.size() + "Data--- "+"Data -- " + weekEndData.toString());

		return sorted;	
    }

   
}
