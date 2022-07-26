package com.Maker.controller;

import com.Maker.model.Appointment;
import com.Maker.model.Patient;
import com.Maker.model.Procedures;
import com.Maker.model.TreatmentPlan;
import com.Maker.service.PatientService;
import com.Maker.service.TreatmentPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/TreatmentPlan")
public class TreatmentPlanController {

    @Autowired
    private TreatmentPlanService treatmentPlanService;

    @Autowired
    private PatientService patientService;


    @PostMapping("/{pid}/AddTreatmentPlan")
    private ResponseEntity<TreatmentPlan> AddTreatmentPlan(@PathVariable int pid ,@RequestBody TreatmentPlan treatmentPlan){

        //set tooth id not pid
        treatmentPlan.setPatient(patientService.getPatient(pid));
        return ResponseEntity.accepted().body(treatmentPlanService.addTreatmentPlan(treatmentPlan));
    }


    @GetMapping("/{patientId}/GetAllPatientTreatmentPlan")
    private ResponseEntity<List<TreatmentPlan>> GetAllPatientTreatmentPlan(@PathVariable int patientId){
        Patient myPatient= patientService.getPatient(patientId);


        return ResponseEntity.accepted().body(treatmentPlanService.getAllTreatmentPlanByPatient(myPatient));
    }


    @PostMapping("/{id}/EditTreatmentPlan")
    private ResponseEntity<TreatmentPlan> EditTreatmentPlan(@PathVariable int id , @RequestBody TreatmentPlan treatmentPlan)
    {
        return ResponseEntity.accepted().body(treatmentPlanService.EditTreatmentPlan(treatmentPlan));
    }



    // Todo add fun to add  Procedures to a TP
}

