package net.viralpatel.spring.excel;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyCell {
    private String content;
    private String textColor;
    private String bgColor;
    private String textSize;
    private String textWeight;
    private String dateFormat;

    public MyCell() {
    }

    
    public  String  setDate(String dateFormat) throws ParseException {
	DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
	Date date = (Date)formatter.parse(dateFormat);
	System.out.println(date);        
 	Calendar cal = Calendar.getInstance();
 	cal.setTime(date);
 	dateFormat = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" +cal.get(Calendar.YEAR);
 	System.out.println("formatedDate : " + dateFormat);    
 	return dateFormat;
     }
    
    public String getDate() {
	return dateFormat;
    }
    
    public MyCell(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
   
    public void setContent(String list) {
        this.content = list;
    }
    
   
    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getTextSize() {
        return textSize;
    }

    public void setTextSize(String textSize) {
        this.textSize = textSize;
    }

    public String getTextWeight() {
        return textWeight;
    }

    public void setTextWeight(String textWeight) {
        this.textWeight = textWeight;
    }

}
