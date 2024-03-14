package com.youro.web.mapper;

import com.youro.web.entity.Prescription;
import com.youro.web.pojo.Response.PrescriptionDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrescriptionMapper {
    public static List<PrescriptionDetails> convertPrescription(List<Prescription> prescriptionList)
    {
        List<PrescriptionDetails> resp = new ArrayList<>();

        for(Prescription prescription : prescriptionList)
        {
            PrescriptionDetails res = new PrescriptionDetails();
            res.setPresName(prescription.getName());
            res.setType(prescription.getPresType());
            res.setPresId(prescription.getPresId());
            res.setShortInfo(prescription.getShortInfo());
            res.setOverview(prescription.getOverview());
            resp.add(res);
        }
        return resp;

    }
}