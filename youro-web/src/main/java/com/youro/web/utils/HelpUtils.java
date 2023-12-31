package com.youro.web.utils;

import com.youro.web.entity.*;
import com.youro.web.pojo.Response.SlotInfo;
import org.springframework.context.annotation.ComponentScan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@ComponentScan
public class HelpUtils {

    static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzz)");
    public static Map<Date, SlotInfo> slotsInfo = new TreeMap<>();


    public static String convertDateTime(Date date, Date time, String timeZone)
    {
        if(timeZone.isEmpty()) {
            timeFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().toZoneId()));
            outputFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().toZoneId()));
            dateFormat.setTimeZone(TimeZone.getTimeZone(TimeZone.getDefault().toZoneId()));
        }
        else
        {
            timeFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            outputFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
            dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        }
        String formattedDate = "";
        try {
            // Parse the time and date strings
            Date time_Conv = timeFormat.parse(timeFormat.format(time));
            Date date_Conv = dateFormat.parse(dateFormat.format(date));

            // Convert the Date objects to Calendar objects
            Calendar startTimeCal = Calendar.getInstance();
            startTimeCal.setTime(time_Conv);

            Calendar dateCal = Calendar.getInstance();
            dateCal.setTime(date_Conv);

            // Extract the time components
            int hours = startTimeCal.get(Calendar.HOUR_OF_DAY);
            int minutes = startTimeCal.get(Calendar.MINUTE);
            int seconds = startTimeCal.get(Calendar.SECOND);

            // Set the time components in the date Calendar
            dateCal.set(Calendar.HOUR_OF_DAY, hours);
            dateCal.set(Calendar.MINUTE, minutes);
            dateCal.set(Calendar.SECOND, seconds);

            formattedDate = outputFormat.format(dateCal.getTime());
            System.out.println(formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }


    public  static List<SlotInfo> getSlots(List<DoctorSchedule> doctorsSch) throws ParseException {
        //List<GetCustomerAvailResponse.SlotInfo> slots = new ArrayList<>();

        slotsInfo = new HashMap<>();

        for(DoctorSchedule sch : doctorsSch)
        {

            Date tempDate = sch.getSchStartTime();
            //addDetails(tempDate, sch.getDoctorId().userId);
            while(tempDate.before(sch.getSchEndTime()))
            {
                addDetails(tempDate, sch.getDoctorId().userId);
                tempDate = addTime(tempDate);

            }
        }

        return slotsInfo.values().stream().toList();

    }

    public static Date addTime(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 30);
        return calendar.getTime();

    }
    public static Date addTime(Date date, int sec)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, sec);
        return calendar.getTime();

    }

    public static String addTime(String date) throws ParseException {
        Date formated_date = addTime(outputFormat.parse(date));
        return outputFormat.format(formated_date);
    }

    public static User getUser(int uID)
    {
        User user = new User();
        user.setUserId(uID);
        return user;
    }

    public static CarePlan getCarePlan(int cID)
    {
        CarePlan carePlan = new CarePlan();
        carePlan.setCarePlanId(cID);
        return carePlan;
    }

    public static Diagnosis getDiagnosis(int dID)
    {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagId(dID);
        return diagnosis;
    }
    public static Prescription getPrescription(int pID)
    {
        Prescription prescription = new Prescription();
        prescription.setPresId(pID);
        return prescription;
    }

    public static Appointments getAppointments(int aID)
    {
        Appointments appointments = new Appointments();
        appointments.setApptId(aID);
        return appointments;
    }

    public static void addDetails(Date startTime, int doctorID) {
        SlotInfo slotInfo = new SlotInfo();
        if (slotsInfo.containsKey(startTime)) {
            slotInfo = slotsInfo.get(startTime);
            slotInfo.noOfDoctors += slotInfo.noOfDoctors;
            slotInfo.doctorIds.add(doctorID);
        } else {
            slotInfo.noOfDoctors = 1;
            slotInfo.doctorIds = new ArrayList<>();
            slotInfo.doctorIds.add(doctorID);
            slotInfo.startTime = startTime;

        }
        slotsInfo.put(startTime, slotInfo);
    }

    }