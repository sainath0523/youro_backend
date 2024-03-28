package com.youro.web.mapper;

import com.youro.web.entity.*;
import com.youro.web.pojo.Request.SaveCarePlanRequest;
import com.youro.web.pojo.Response.GetCarePlanResponse;
import com.youro.web.pojo.Response.GetCarePlanVersions;
import com.youro.web.pojo.Response.GetCarePlaneDetails;
import com.youro.web.pojo.Response.PrescriptionDetails;
import com.youro.web.utils.HelpUtils;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.Map;
import java.util.HashMap;

import java.util.Optional;

import java.util.stream.Collectors;

public class CarePlanMapper {

    public static GetCarePlaneDetails mergeMaps(
            GetCarePlaneDetails map1, GetCarePlaneDetails map2) {
        GetCarePlaneDetails mergedMap = new GetCarePlaneDetails();

        // Combine keys from both maps
        List<Integer> allKeys = new ArrayList<>();
        allKeys.addAll(map1.getPresTypes().keySet());
        allKeys.addAll(map2.getPresTypes().keySet());

        // Iterate over combined keys
        for (Integer key : allKeys) {
            List<PrescriptionDetails> combinedList = new ArrayList<>();
            if (map1.getPresTypes().containsKey(key)) {
                combinedList.addAll(map1.getPrescriptionDetails(key));
            }
            if (map2.getPresTypes().containsKey(key)) {
                combinedList.addAll(map2.getPrescriptionDetails(key));
            }
            // Resolve duplicates based on indicator
            List<PrescriptionDetails> resolvedList = combinedList.stream()
                    .collect(Collectors.toMap(
                            PrescriptionDetails::getPresId, // Key by id
                            // Value resolver: Keep the object if indicator is true or if it's the only occurrence for that id
                            item -> item,
                            (existing, replacement) -> {
                                if (replacement.indicator) {
                                    return replacement;
                                }
                                return existing;
                            }
                    ))
                    .values()
                    .stream()
                    .toList();
            for(PrescriptionDetails pd: resolvedList) {
                mergedMap.addPrescriptionDetails(key, pd);
            }
        }
        return mergedMap;
    }

    public static GetCarePlaneDetails getCarePlaneDetails(GetCarePlaneDetails carePlaneDetails, List<Prescription> prescriptionList)
    {
        GetCarePlaneDetails getCarePlaneDetails = new GetCarePlaneDetails();
        GetCarePlaneDetails prescriptionDetails = getCarePlaneByPrescription(prescriptionList);

//        getCarePlaneDetails.vitamins = combineLists(carePlaneDetails.vitamins, prescriptionDetails.vitamins);
//        getCarePlaneDetails.imaging = combineLists(carePlaneDetails.imaging, prescriptionDetails.imaging);
//        getCarePlaneDetails.labs = combineLists(carePlaneDetails.labs, prescriptionDetails.labs);
//        getCarePlaneDetails.medicines = combineLists(carePlaneDetails.medicines, prescriptionDetails.medicines);
//        getCarePlaneDetails.lifeStyle = combineLists(carePlaneDetails.lifeStyle, prescriptionDetails.lifeStyle);
        return  mergeMaps(getCarePlaneDetails,prescriptionDetails);
    }
    public static List<PrescriptionDetails> combineLists(List<PrescriptionDetails> details, List<PrescriptionDetails> detailsList)
    {
        List<PrescriptionDetails> combinedList = new ArrayList<>(details);
        combinedList.addAll(detailsList);
        return combinedList.stream()
                .collect(Collectors.toMap(
                        PrescriptionDetails::getPresId, // Key by id
                        // Value resolver: Keep the object if indicator is true or if it's the only occurrence for that id
                        item -> item,
                        (existing, replacement) -> {
                            if (replacement.indicator) {
                                return replacement;
                            }
                            return existing;
                        }
                ))
                .values()
                .stream()
                .toList();
    }


    public static GetCarePlaneDetails mapPrescriptionEntity(List<Prescription> prescriptionList)
    {

        GetCarePlaneDetails getCarePlaneDetails = new GetCarePlaneDetails();

        for(Prescription pres : prescriptionList)
        {
            PrescriptionDetails presDetails = new PrescriptionDetails();
            presDetails.setPresId(pres.getPresId());
            presDetails.setType(pres.getPresType());
            Integer presType = pres.getPresType().getPresTypeId();
            getCarePlaneDetails.addPrescriptionDetails(presType,presDetails);
//            Integer presType = details.getType().getPresTypeId();
//            carePlaneDetails.presTypes.get(presType).add(details);
            //presDetails.setName(pres.getName());
//            if(pres.getPresType() == PrescriptionType.VITAMINS)
//            {
//                getCarePlaneDetails.vitamins.add(presDetails);
//            }
//            else if(pres.getPresType() == PrescriptionType.MEDICINES)
//            {
//                getCarePlaneDetails.medicines.add(presDetails);
//            }
//            else if(pres.getPresType() == PrescriptionType.LAB)
//            {
//                getCarePlaneDetails.labs.add(presDetails);
//            }
//            else if(pres.getPresType() == PrescriptionType.LIFESTYLE)
//            {
//                getCarePlaneDetails.lifeStyle.add(presDetails);
//            }
//            else if(pres.getPresType() == PrescriptionType.IMAGING)
//            {
//                getCarePlaneDetails.imaging.add(presDetails);
//            }
        }

        return getCarePlaneDetails;
    }

    public static List<CarePlanDetails> toCarePlanDetailsEntity(GetCarePlaneDetails carePlaneDetails, CarePlan carePlan)
    {
        List<CarePlanDetails> carePlanDetails = new ArrayList<>();
        if(carePlaneDetails!=null) {
            for (Map.Entry<Integer, List<PrescriptionDetails>> entry : carePlaneDetails.getPresTypes().entrySet()) {
                Integer key = entry.getKey();
                List<PrescriptionDetails> prescriptionDetailsList = entry.getValue();
                carePlanDetails.addAll(getCarePlan(prescriptionDetailsList,carePlan));
            }
        }
        return  carePlanDetails;
    }


    public static CarePlan toCarePlanEntity(SaveCarePlanRequest saveCarePlanRequest, List<CarePlan> carePlanList)
    {
        CarePlan carePlan = new CarePlan();
        Diagnosis diag = HelpUtils.getDiagnosis(saveCarePlanRequest.diagID);
        Appointments appt = HelpUtils.getAppointments(saveCarePlanRequest.apptId);
        carePlan.setNotes(saveCarePlanRequest.getNotes());
        if(carePlanList.isEmpty()) {
            carePlan.setCreateBy(HelpUtils.getUser(saveCarePlanRequest.getDoctorId()));
        }
        else
        {
            carePlan.setCreateBy(carePlanList.get(0).getCreateBy());
            carePlan.setLastUpdatedBy(HelpUtils.getUser(saveCarePlanRequest.getDoctorId()));
        }
        if(saveCarePlanRequest.followUp !=null)
        {
            carePlan.setFollowUp(saveCarePlanRequest.followUp);
        }
        carePlan.setDiagnosis(diag);
        carePlan.setAppointments(appt);
        carePlan.setLastUpdated(new Date());
        return carePlan;
    }

    public static List<CarePlanDetails> getCarePlan(List<PrescriptionDetails> prescriptionDetails, CarePlan carePlan)
    {
        List<CarePlanDetails> carePlanList = new ArrayList<>();
        for(PrescriptionDetails pres : prescriptionDetails)
        {
            CarePlanDetails carePlanDetails = new CarePlanDetails();
            carePlanDetails.setDosage(pres.getDosage());
            carePlanDetails.setCarePlan(carePlan);
            carePlanDetails.setPrescription(HelpUtils.getPrescription(pres.getPresId()));
            carePlanList.add(carePlanDetails);
        }
        return carePlanList;
    }

    public static List<GetCarePlanVersions> mapCarePlanVersions(List<CarePlan> carePlanList)
    {
        int count = carePlanList.size();
        List<GetCarePlanVersions> versions = new ArrayList<>();

        boolean flag = true;
        for(CarePlan carePlan : carePlanList)
        {
            GetCarePlanVersions version = new GetCarePlanVersions();
            version.cId = carePlan.getCarePlanId();
            version.version = "version - " + count;
            version.edit = flag;
            flag = false;
            count--;
            versions.add(version);
        }
        return versions;
    }

    public static GetCarePlanResponse mapCarePlanResponse(List<CarePlanDetails> carePlanDetails, CarePlan carePlan, List<Prescription> prescriptionList)
    {

        GetCarePlanResponse res = new GetCarePlanResponse();
        GetCarePlaneDetails carePlaneDetails = new GetCarePlaneDetails();
        res.diagName = carePlan.getDiagnosis().getName();
        res.diagInfo = carePlan.getDiagnosis().getInfo();
        res.lastModified = carePlan.getLastUpdated();
        res.notes = carePlan.getNotes();
        if(carePlan.getFollowUp() != null)
        {
            res.followUp = carePlan.getFollowUp();
        }
        for(CarePlanDetails detail: carePlanDetails)
        {
            PrescriptionDetails details = new PrescriptionDetails();
            details.setPresId(detail.getPrescription().getPresId());
            details.setType(detail.getPrescription().getPresType());
            details.setShortInfo(detail.getPrescription().getShortInfo());
            details.setOverview(detail.getPrescription().getOverview());
            details.setDosage(detail.getDosage());
            details.setPresName(detail.getPrescription().getName());

            Category category = detail.getPrescription().getCategory();

            if (category != null) {
                details.setCategoryName(category.getName());
            }
            details.setIndicator(true);
            Integer presType = details.getType().getPresTypeId();
            carePlaneDetails.addPrescriptionDetails(presType, details);

//            if(details.getType()== PrescriptionType.VITAMINS)
//            {
//                carePlaneDetails.vitamins.add(details);
//            }
//            else if(details.getType() == PrescriptionType.MEDICINES)
//            {
//                carePlaneDetails.medicines.add(details);
//            }
//            else if(details.getType() == PrescriptionType.LAB)
//            {
//                carePlaneDetails.labs.add(details);
//            }
//            else if(details.getType() == PrescriptionType.LIFESTYLE)
//            {
//                carePlaneDetails.lifeStyle.add(details);
//            }
//            else if(details.getType() == PrescriptionType.IMAGING)
//            {
//                carePlaneDetails.imaging.add(details);
//            }
        }
        if(!prescriptionList.isEmpty())
        {
            carePlaneDetails = getCarePlaneDetails(carePlaneDetails,prescriptionList);
        }
        res.setCarePlan(carePlaneDetails);
        return res;
    }

    public static GetCarePlaneDetails getCarePlaneByPrescription(List<Prescription> prescriptionList)
    {
        GetCarePlaneDetails carePlaneDetails = new GetCarePlaneDetails();
        for(Prescription prescription : prescriptionList)
        {
            PrescriptionDetails details = new PrescriptionDetails();
            details.setPresId(prescription.getPresId());
            details.setType(prescription.getPresType());
            details.setPresName(prescription.getName());

            details.setCategory(prescription.getCategory());
            details.setShortInfo(prescription.getShortInfo());
            details.setOverview(prescription.getOverview());

            details.setIndicator(false);
            Integer presType = details.getType().getPresTypeId();
            carePlaneDetails.addPrescriptionDetails(presType,details);

//            switch (details.getType()){
//                case 0:
//                    carePlaneDetails.labs.add(details);
//                    break;
//
//            }
//            if(details.getType()== PrescriptionType.VITAMINS)
//            {
//                carePlaneDetails.vitamins.add(details);
//            }
//            else if(details.getType() == PrescriptionType.MEDICINES)
//            {
//                carePlaneDetails.medicines.add(details);
//            }
//            else if(details.getType() == PrescriptionType.LAB)
//            {
//                carePlaneDetails.labs.add(details);
//            }
//            else if(details.getType() == PrescriptionType.LIFESTYLE)
//            {
//                carePlaneDetails.lifeStyle.add(details);
//            }
//            else if(details.getType() == PrescriptionType.IMAGING)
//            {
//                carePlaneDetails.imaging.add(details);
//            }
        }

        return carePlaneDetails;
    }



}