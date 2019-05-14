package net.viralpatel.spring.excel;



public class CTSExcelChildData {
  
    @Override
    public String toString() {
	return "CTSExcelChildData [timeQty=" + timeQty + ", date=" + date + ", projectId=" + projectId + ", weekend="
		+ weekend + "]";
    }

    public CTSExcelChildData() {

    }

    private String timeQty;
    private String date;
    private String projectId;
    private String weekend;
    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    private int hours;

    public String getWeekend() {
        return weekend;
    }

    public void setWeekend(String weekend) {
        this.weekend = weekend;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTimeQty() {
	return timeQty;
    }

    public void setTimeQty(String timeQty) {
	this.timeQty = timeQty;
    }

    public String getDate() {
	return date;
    }

    public void  setDate(String date) {
	this.date = date;
    }

}
