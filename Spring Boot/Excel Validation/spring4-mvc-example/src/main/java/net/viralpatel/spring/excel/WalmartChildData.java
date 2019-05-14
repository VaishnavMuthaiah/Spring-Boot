package net.viralpatel.spring.excel;

public class WalmartChildData {

    private String timeQty;
    private String date;
    private String weekend;
    private int hours;

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getWeekend() {
        return weekend;
    }

    public void setWeekend(String weekend) {
        this.weekend = weekend;
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

    public void setDate(String date) {
	this.date = date;
    }

}
