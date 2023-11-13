package com.youro.web.pojo.Response;

import java.util.Date;

public class GetCheckListResponse {

//    public int doctorId;
//    public int patientId;
//    public int apptId;
    
    public Date apptDate;
    public String patientName;

    public Boolean orders;
    public Boolean followUp;
    public Boolean notes;
    
	public Date getApptDate() {
		return apptDate;
	}
	public void setApptDate(Date apptDate) {
		this.apptDate = apptDate;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public Boolean getOrders() {
		return orders;
	}
	public void setOrders(Boolean orders) {
		this.orders = orders;
	}
	public Boolean getFollowUp() {
		return followUp;
	}
	public void setFollowUp(Boolean followUp) {
		this.followUp = followUp;
	}
	public Boolean getNotes() {
		return notes;
	}
	public void setNotes(Boolean notes) {
		this.notes = notes;
	}

}
