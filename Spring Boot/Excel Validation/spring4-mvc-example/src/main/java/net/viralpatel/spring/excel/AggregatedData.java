package net.viralpatel.spring.excel;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AggregatedData {

    private String timeQty;

    private String associateId;
    
    private String associateName;

    private String walmartId;
    
    private String matcher;
    
    private String dateString;
    
    private String projectId;
    

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    private Date date;
    public String getAssociateName() {
        return associateName;
    }

    public void setAssociateName(String associateName) {
        this.associateName = associateName;
    }

    public String getTimeQtyTwo() {
        return timeQtyTwo;
    }

    public String getMatcher() {
        return matcher;
    }

    public void setMatcher(String matcher) {
        this.matcher = matcher;
    }

    public void setTimeQtyTwo(String timeQtyTwo) {
        this.timeQtyTwo = timeQtyTwo;
    }

    private String timeQtyTwo;

    public String gettimeQtyTwo() {
        return timeQtyTwo;
    }

    public void settimeQtyTwo(String timeQtyTwo) {
        this.timeQtyTwo = timeQtyTwo;
    }

    public AggregatedData(String associateName,String associateId, String walmartId, String projectId, String timeQty, String dateString,String timeQtyTwo,String matcher ) {
	this.associateName = associateName;
	this.walmartId = walmartId;
	this.timeQty = timeQty;
	this.dateString = dateString;
	this.associateId = associateId;
	this.timeQtyTwo = timeQtyTwo;
	this.matcher = matcher;
	this.projectId = projectId;
    }

    public String getTimeQty() {
	return timeQty;
    }

    public void setTimeQty(String timeQty) {
	this.timeQty = timeQty;
    }

    public String getAssociateId() {
	return associateId;
    }

    public void setAssociateId(String associateId) {
	this.associateId = associateId;
    }

    public String getWalmartId() {
	return walmartId;
    }

    public void setWalmartId(String walmartId) {
	this.walmartId = walmartId;
    }
    
    public  String  getDate2(String dateFormat) throws ParseException {
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
	//getDate2();
	return dateString;
    }

    public void setDate(String dateString) {
	this.dateString = dateString;
    }

    @Override
    public String toString() {
	return "AggregatedData [timeQtyOne=" + timeQty+"timeQtyTwo=" + timeQtyTwo + ", associateId=" + associateId + ", walmartId=" + walmartId
		+ ", date=" + date + "]";
    }
    

}
