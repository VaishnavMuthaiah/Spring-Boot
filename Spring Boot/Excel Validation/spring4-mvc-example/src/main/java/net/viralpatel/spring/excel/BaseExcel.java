package net.viralpatel.spring.excel;

public class BaseExcel {

    private Integer associateId;
    private String workerId;

    public BaseExcel(String workerId) {
	this.workerId = workerId;
    }

    public Integer getAssociateId() {
	return associateId;
    }

    public void setAssociateId(Integer associateId) {
	this.associateId = associateId;
    }

    public String getWorkerId() {
	return workerId;
    }

    public void setWorkerId(String workerId) {
	this.workerId = workerId;
    }

}
