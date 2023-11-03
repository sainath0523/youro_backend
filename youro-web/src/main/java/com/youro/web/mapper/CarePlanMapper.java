package com.youro.web.mapper;

import com.youro.web.entity.*;
import com.youro.web.pojo.Request.SaveCarePlanRequest;
import com.youro.web.pojo.Response.GetCarePlaneDetails;
import com.youro.web.pojo.Response.PrescriptionDetails;
import com.youro.web.utils.HelpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CarePlanMapper {


    public static GetCarePlaneDetails getCarePlaneDetails(GetCarePlaneDetails carePlaneDetails, GetCarePlaneDetails prescriptionDetails)
    {
        GetCarePlaneDetails getCarePlaneDetails = new GetCarePlaneDetails();
        getCarePlaneDetails.vitamins = combineLists(carePlaneDetails.vitamins, prescriptionDetails.vitamins);
        getCarePlaneDetails.imaging = combineLists(carePlaneDetails.imaging, prescriptionDetails.imaging);
        getCarePlaneDetails.labs = combineLists(carePlaneDetails.labs, prescriptionDetails.labs);
        getCarePlaneDetails.medicines = combineLists(carePlaneDetails.medicines, prescriptionDetails.medicines);
        getCarePlaneDetails.lifeStyle = combineLists(carePlaneDetails.lifeStyle, prescriptionDetails.lifeStyle);
        return getCarePlaneDetails;
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
            presDetails.setName(pres.getName());
            if(pres.getPresType() == PrescriptionType.VITAMINS)
            {
                getCarePlaneDetails.vitamins.add(presDetails);
            }
            else if(pres.getPresType() == PrescriptionType.MEDICINES)
            {
                getCarePlaneDetails.medicines.add(presDetails);
            }
            else if(pres.getPresType() == PrescriptionType.LAB)
            {
                getCarePlaneDetails.labs.add(presDetails);
            }
            else if(pres.getPresType() == PrescriptionType.LIFESTYLE)
            {
                getCarePlaneDetails.lifeStyle.add(presDetails);
            }
            else if(pres.getPresType() == PrescriptionType.IMAGING)
            {
                getCarePlaneDetails.imaging.add(presDetails);
            }
        }

        return getCarePlaneDetails;
    }

    public static GetCarePlaneDetails mapCarePlanEntity(List<CarePlan> carePlanList)
    {

        GetCarePlaneDetails getCarePlaneDetails = new GetCarePlaneDetails();

        for(CarePlan pres : carePlanList)
        {
            PrescriptionDetails presDetails = new PrescriptionDetails();
            presDetails.setPresId(pres.getPresId().getPresId());
            presDetails.setType(pres.getPresId().getPresType());
            presDetails.setName(pres.getPresId().getName());
            presDetails.setDosage(pres.getDosage());
            presDetails.setIndicator(true);
            if(presDetails.getType()== PrescriptionType.VITAMINS)
            {
                getCarePlaneDetails.vitamins.add(presDetails);
            }
            else if(presDetails.getType() == PrescriptionType.MEDICINES)
            {
                getCarePlaneDetails.medicines.add(presDetails);
            }
            else if(presDetails.getType() == PrescriptionType.LAB)
            {
                getCarePlaneDetails.labs.add(presDetails);
            }
            else if(presDetails.getType() == PrescriptionType.LIFESTYLE)
            {
                getCarePlaneDetails.lifeStyle.add(presDetails);
            }
            else if(presDetails.getType() == PrescriptionType.IMAGING)
            {
                getCarePlaneDetails.imaging.add(presDetails);
            }
        }

        return getCarePlaneDetails;
    }


    public static List<CarePlan> toCarePlanEntity(SaveCarePlanRequest saveCarePlanRequest)
    {

        List<CarePlan> carePlanList = new ArrayList<>();
        Diagnosis diag = HelpUtils.getDiagnosis(saveCarePlanRequest.diagID);
        Appointments appt = HelpUtils.getAppointments(saveCarePlanRequest.apptId);
        GetCarePlaneDetails details = saveCarePlanRequest.carePlanDetails;
        if(details!=null) {
            carePlanList.addAll(getCarePlan(details.imaging,appt, diag ));
            carePlanList.addAll(getCarePlan(details.labs,appt, diag ));
            carePlanList.addAll(getCarePlan(details.vitamins,appt, diag ));
            carePlanList.addAll(getCarePlan(details.medicines,appt, diag ));
            carePlanList.addAll(getCarePlan(details.lifeStyle,appt, diag ));
        }
        return carePlanList;
    }

    public static List<CarePlan> getCarePlan(List<PrescriptionDetails> prescriptionDetails, Appointments appt, Diagnosis diag)
    {
        List<CarePlan> carePlanList = new ArrayList<>();

        for(PrescriptionDetails pres : prescriptionDetails)
        {
            CarePlan carePlan = new CarePlan();
            carePlan.setDosage(pres.getDosage());
            carePlan.setPresId(HelpUtils.getPrescription(pres.getPresId()));
            carePlan.setAppointments(appt);
            carePlan.setPresType(pres.getType());
            carePlan.setLastUpdated(new Date());
            carePlan.setDiagnosis(diag);
            carePlanList.add(carePlan);

        }

        return carePlanList;
    }



}