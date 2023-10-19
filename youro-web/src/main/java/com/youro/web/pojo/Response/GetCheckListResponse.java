package com.youro.web.pojo.Response;

public class GetCheckListResponse {

    public int doctorId;
    public int patientId;
    public int apptId;
    public Boolean orders;
    public Boolean followUp;
    public Boolean notes;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getApptId() {
        return apptId;
    }

    public void setApptId(int apptId) {
        this.apptId = apptId;
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
