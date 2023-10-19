package com.youro.web.pojo.Response;

import com.youro.web.entity.AppointmentStatus;

import java.util.Date;

public class GetAppointmentsReponse {

    public int apptId;
    public int patientId;
    public String doctorName;
    public String patientName;
    public int doctorId;
    public Date apptDate;

    public Date apptStartTime;
    public Date apptEndTime;
    public String link;
    public AppointmentStatus status;

    public int followUpId;

    public int getApptId() {
        return apptId;
    }

    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Date getApptDate() {
        return apptDate;
    }

    public void setApptDate(Date apptDate) {
        this.apptDate = apptDate;
    }

    public Date getApptStartTime() {
        return apptStartTime;
    }

    public void setApptStartTime(Date apptStartTime) {
        this.apptStartTime = apptStartTime;
    }

    public Date getApptEndTime() {
        return apptEndTime;
    }

    public void setApptEndTime(Date apptEndTime) {
        this.apptEndTime = apptEndTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public int getFollowUpId() {
        return followUpId;
    }

    public void setFollowUpId(int followUpId) {
        this.followUpId = followUpId;
    }


}
