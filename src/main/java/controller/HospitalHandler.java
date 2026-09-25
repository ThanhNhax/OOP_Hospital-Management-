package controller;

import view.*;

public class HospitalHandler {
    private PatientHandle patHan = new PatientHandle();

    public void handleAddNew(){
        patHan.addPatient();
    }

    public void handleDelete(){
        patHan.deletePatient();
    }

    public void handleUpdate(){
        patHan.updatePat();
    }

    public void handleSearch(){
        String keyword = InputValidator.readNonEmptyString("Keyword: ");
        patHan.getPatMan().searchAdvance(keyword);
    }

    public void handleDisplayAPatient(){
        while(true){
            String patID = InputValidator.readNonEmptyString("Patient ID (Enter 0 to exit): ");
            if (patID.equals("0")) return;
            patHan.getPatMan().searchPatientByID(patID);
        }
    }

    public void handleDisplayAll(){
        patHan.getPatMan().showAll();
    }

    public void handleSaveToFile(){
        patHan.saveToFile();
        OutputViewer.Successfully("Save", "file");;
    }

    public void handleLoadFromFile(){
        patHan = new PatientHandle();
        OutputViewer.Successfully("Load", "All Data from Files");
    }
}