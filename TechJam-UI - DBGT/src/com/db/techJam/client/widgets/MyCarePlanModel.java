package com.db.techJam.client.widgets;

public class MyCarePlanModel {

	public MyCarePlanModel(String sequenceNo, String category, String activity) {
		super();
		this.sequenceNo = sequenceNo;
		this.category = category;
		this.activity = activity;
	}
	String sequenceNo;
	String category;
	String activity;
	
	public String getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	
	
}
