package com.youro.web.mapper;

import com.youro.web.entity.Notes;
import com.youro.web.pojo.Request.SaveNotesRequest;
import com.youro.web.pojo.Response.GetNotesResponse;
import com.youro.web.utils.HelpUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NotesMapper {

    public static List<GetNotesResponse> entityToResponseMapping(List<Notes> notes)
    {
        List<GetNotesResponse> resp = new ArrayList<>();
        for(Notes note : notes)
        {
            GetNotesResponse res = new GetNotesResponse();
            res.apptTime = note.getAppointments().getApptStartTime();
            res.patientName = note.getPatientId().getFirstName() + " " + note.getPatientId().getLastName();
            res.doctorName = note.getDoctorId().getFirstName() + " " + note.getDoctorId().getLastName();
            res.lastUpdated = note.getLastUpdated();
            res.notes = note.notes;
            resp.add(res);
        }
        resp.sort(Comparator.comparing(GetNotesResponse:: getApptTime, Comparator.reverseOrder()));
        return resp;
    }


    public static Notes requestToEntityMapper(SaveNotesRequest request)
    {
        Notes notes = new Notes();
        notes.setNotes(request.notes);
        notes.setDoctorId(HelpUtils.getUser(request.doctorId));
        notes.setPatientId(HelpUtils.getUser(request.patientId));
        notes.setAppointments(HelpUtils.getAppointments(request.apptId));
        notes.setLastUpdated(new Date());
        return notes;
    }
}