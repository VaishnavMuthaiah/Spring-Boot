package net.viralpatel.spring.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.NumberToTextConverter;


public class ReadCellContent {
    
    public ReadCellContent() {
    }
    
    public  String readCell(Cell cell) {
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

}
