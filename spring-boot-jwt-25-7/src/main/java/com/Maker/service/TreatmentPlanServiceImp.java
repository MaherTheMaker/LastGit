package com.Maker.service;

import com.Maker.dao.*;
import com.Maker.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TreatmentPlanServiceImp implements TreatmentPlanService {

    //TODO some validation

    @Autowired
    private TreatmentPlanRepo treatmentPlanRepo;

    @Autowired
    private ToothProcedureService toothProcedureService;


    @Autowired
    private ToothProcedureRepo toothProcedureRepo;

    @Autowired
    private PatientToothRepo patientToothRepo;

    @Autowired
    private MyProcedureRepo myProcedureRepo;




    @Override
    public TreatmentPlan addTreatmentPlan(TreatmentPlan treatmentPlan) {
        return treatmentPlanRepo.save(treatmentPlan);
    }



    @Override
    public TreatmentPlan EditTreatmentPlan(int tpId,TreatmentPlan treatmentPlan) {
        Optional<TreatmentPlan> optionalOldTreatmentPlan =treatmentPlanRepo.findById(tpId);
        TreatmentPlan oldPlan=optionalOldTreatmentPlan.get();
        oldPlan.setNotes(treatmentPlan.getNotes());
//        oldPlan.setProceduresList(treatmentPlan.getProceduresList());



        return treatmentPlanRepo.save(oldPlan);
    }

    @Override
    public TreatmentPlan getTreatmentPlan(int id) {

        return treatmentPlanRepo.findById(id).get();
    }

    @Override
    public List<TreatmentPlan> getAllTreatmentPlanByPatient(Patient patient) {
        return treatmentPlanRepo.findAllByPatient(patient);
    }


    @Override
    public List<TreatmentPlan> getAllTreatmentPlanByTooth(PatientTooth patientTooth) {
        return treatmentPlanRepo.findAllByPatientTooth(patientTooth);
    }

    @Override
    public TreatmentPlan addToothProcedureToTreatmentPlan(int tpId, ToothProcedure toothProcedure) {
        Optional<TreatmentPlan> optionalOldTreatmentPlan =treatmentPlanRepo.findById(tpId);
        TreatmentPlan oldPlan=optionalOldTreatmentPlan.get();
        oldPlan.getToothProcedures().add(toothProcedure);
        return treatmentPlanRepo.save(oldPlan);
    }


    @Override
    public TreatmentPlan addToothProcedure(int tpId,int pId,int proId,int PtId, ToothProcedure toothProcedure){
        ToothProcedure newToothP=  toothProcedure;
        //Patient link
        MyProcedure myProcedure = myProcedureRepo.findById(proId);
        PatientTooth patientTooth=patientToothRepo.findById(PtId).get();
        TreatmentPlan treatmentPlan=treatmentPlanRepo.findById(tpId).get();
        newToothP.setPatientTooth(patientTooth);
        newToothP.setProcedure(myProcedure);
        newToothP.setTreatmentPlan(treatmentPlan);

        return addToothProcedureToTreatmentPlan(tpId,toothProcedure);
    }

    @Override
    public TreatmentPlan removeToothProcedureFromTreatmentPlan(int tpId, int TProcId) {
        TreatmentPlan treatmentPlan=treatmentPlanRepo.findById(tpId).get();
        ToothProcedure removedTPro= toothProcedureRepo.findById(TProcId).get();
        treatmentPlan.getToothProcedures().remove(removedTPro);
        toothProcedureRepo.delete(removedTPro);
        return treatmentPlanRepo.save(treatmentPlan);

    }


    @Override
    public ToothProcedure doToothProcedure(int id) {
        ToothProcedure toothProcedure= toothProcedureRepo.findById(id).get();
        toothProcedure.setDone(true);
        return toothProcedureRepo.save(toothProcedure);

    }


    @Override
    public ToothProcedure EditToothProcedureNotes(int id, String newNotes) {
        ToothProcedure toothProcedure= toothProcedureRepo.findById(id).get();
        toothProcedure.setNotes(newNotes);
        return toothProcedureRepo.save(toothProcedure);

    }




    @Override
    public ToothProcedure getToothProcedure(int id) {
        return toothProcedureRepo.findById(id).get();
    }

    @Override
    public List<ToothProcedure> getAllToothProceduresByPatientTooth(PatientTooth patientTooth) {
        return toothProcedureRepo.findAllByPatientTooth(patientTooth);
    }

    @Override
    public List<ToothProcedure> getAllToothProceduresByProcedure(MyProcedure myProcedure) {
        return toothProcedureRepo.findAllByMyProcedure(myProcedure);
    }

    @Override
    public List<ToothProcedure> getAllToothProceduresByTreatmentPlan(TreatmentPlan treatmentPlan) {
        return toothProcedureRepo.findAllByTreatmentPlan(treatmentPlan);
    }





}
