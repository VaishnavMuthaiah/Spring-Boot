package net.viralpatel.spring.excel;

public class WeekEndData {
    
    private String associateId;
    
    private String associateName;
    
    private String workerId;
    
    private String weekEnd;
    
    private int ctsHours;
    
    private int walmartHours;
    
    public void setCtsHours(int ctsHours) {
        this.ctsHours = ctsHours;
    }
    
    

    public int getCtsHours() {
        return ctsHours;
    }



    public int getWalmartHours() {
        return walmartHours;
    }



    public void setWalmartHours(int walmartHours) {
        this.walmartHours = walmartHours;
    }

    private String matcher;

    public WeekEndData(String associateId, String associateName, String workerId, String weekEnd, int ctsHours,
	    int walmartHours, String matcher) {
	this.associateId = associateId;
	this.associateName = associateName;
	this.workerId = workerId;
	this.weekEnd = weekEnd;
	this.ctsHours = ctsHours;
	this.walmartHours = walmartHours;
	this.matcher = matcher;
    }


    public String getAssociateId() {
        return associateId;
    }

    public void setAssociateId(String associateId) {
        this.associateId = associateId;
    }

    public String getAssociateName() {
        return associateName;
    }

    public void setAssociateName(String associateName) {
        this.associateName = associateName;
    }

    public String getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(String weekEnd) {
        this.weekEnd = weekEnd;
    }

    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public String getMatcher() {
        return matcher;
    }

    @Override
    public String toString() {
	return "WeekEndData [associateId=" + associateId + ", associateName=" + associateName + ", workerId=" + workerId
		+ ", weekEnd=" + weekEnd + ", ctsHours=" + ctsHours + ", walmartHours=" + walmartHours + ", matcher="
		+ matcher + "]";
    }


    public void setMatcher(String matcher) {
        this.matcher = matcher;
    }
    
}
