package net.viralpatel.spring.excel;

import java.util.List;

public class CTSExcel {

    private String assoicateId;
    private String associateName;
    private String workerId;
    public String getWorkerId() {
        return workerId;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    private List<CTSExcelChildData> childData;
    

    public CTSExcel(String assoicateId, List<CTSExcelChildData> childData) {
	super();
	this.assoicateId = assoicateId;
	this.childData = childData;

    }

    public CTSExcel() {
    }

    public String getAssoicateId() {
	return assoicateId;
    }

    public void setAssoicateId(String assoicateId) {
	this.assoicateId = assoicateId;
    }

    public String getAssociateName() {
	return associateName;
    }

    public void setAssociateName(String associateName) {
	this.associateName = associateName;
    }

    public List<CTSExcelChildData> getChildData() {
	return childData;
    }

    public void setChildData(List<CTSExcelChildData> childData) {
	this.childData = childData;
    }

}
