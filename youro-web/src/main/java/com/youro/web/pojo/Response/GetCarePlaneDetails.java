package com.youro.web.pojo.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Getter
@Setter
public class GetCarePlaneDetails {

   private Map<Integer,List<PrescriptionDetails>> presTypes;
   public GetCarePlaneDetails(){
      this.presTypes = new HashMap<>();
   }

   public void addPrescriptionDetails(Integer key, PrescriptionDetails prescriptionDetails) {
      presTypes.computeIfAbsent(key, k -> new ArrayList<>()).add(prescriptionDetails);
   }

   public List<PrescriptionDetails> getPrescriptionDetails(Integer key) {
      return presTypes.getOrDefault(key, new ArrayList<>());
   }

   public Map<Integer, List<PrescriptionDetails>> getPresTypes() {
      return presTypes;
   }

//    public List<PrescriptionDetails> vitamins = new ArrayList<>();
//    public List<PrescriptionDetails> medicines = new ArrayList<>();
//
//    public List<PrescriptionDetails> lifeStyle = new ArrayList<>();
//
//    public List<PrescriptionDetails> labs = new ArrayList<>();
//
//    public List<PrescriptionDetails> imaging  = new ArrayList<>();

}