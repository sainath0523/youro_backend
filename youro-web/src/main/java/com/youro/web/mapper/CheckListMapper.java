package com.youro.web.mapper;

import com.youro.web.entity.CheckList;
import com.youro.web.pojo.Response.GetCheckListResponse;
import com.youro.web.repository.AppointmentsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CheckListMapper {

	public static List<GetCheckListResponse> entityToResponseMapping(List<CheckList> checklists, AppointmentsRepository appointmentsRepository) {
		
		List<GetCheckListResponse> resp = new ArrayList<>();
        
		for(CheckList checklist : checklists)
        {
            if(!checklist.notes || !checklist.orders) {
                GetCheckListResponse res = new GetCheckListResponse();
                res.apptDate = appointmentsRepository.findById(checklist.appointments.getApptId()).get().getApptDate();
                res.patientName = checklist.getPatientId().getFirstName() + " " + checklist.getPatientId().getLastName();
                res.notes = checklist.getNotes();
                res.orders = checklist.getOrders();
                resp.add(res);
            }
        }
        
        Collections.sort(resp, new Comparator<GetCheckListResponse>() {
            @Override
            public int compare(GetCheckListResponse o1, GetCheckListResponse o2) {
                return o1.apptDate.compareTo(o2.apptDate);
            }
        });
        
        return resp;
	}

}