package net.viralpatel.spring.excel;

import java.util.List;

public class WalmartExcel {

    private String workerId;

    private List<WalmartChildData> walmartChildData;

    public WalmartExcel() {

    }

    public String getWorkerId() {
	return workerId;
    }

    public void setWorkerId(String workerId) {
	this.workerId = workerId;
    }

    public List<WalmartChildData> getWalmartChildData() {
	return walmartChildData;
    }

    public void setWalmartChildData(List<WalmartChildData> walmartChildData) {
	this.walmartChildData = walmartChildData;
    }

}
