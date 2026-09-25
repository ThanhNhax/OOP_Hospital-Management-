package controller;

import java.util.Date;
import manager.*;
import model.*;
import view.InputValidator;
import view.OutputViewer;

public class PatientHandle {
    private PatientManager patMan = new PatientManager();
    private final String patFile = "patient.dat";

    public PatientHandle() {
        patMan.loadFromFile(patFile);
    }

    public PatientManager getPatMan() {
        return patMan;
    }

    public void addPatient() {
        String patID = InputValidator.readValidString("Patient ID (Enter 0 to cancel)", null, "add",
                id -> !id.isEmpty() && patMan.findByID(id) == null,
                "Error: Patient ID must not be empty and must not exist!!");
        if (patID == null) {
            OutputViewer.Cancelled("Add", "Patient");
            return;
        }

        String name = InputValidator.readNonEmptyString("Patient Name");
        String sex = InputValidator.readGender("Patient Sex (M/F)", null, "add");
        String addr = InputValidator.readNonEmptyString("Address");
        String dob = InputValidator.readDate("Date of Birth (dd/MM/yyyy)", null, "add");
        String phone = InputValidator.readPhone("Phone Number", null, "add");
        String diagnosis = InputValidator.readNonEmptyString("Diagnosis");

        String doctor = InputValidator.readNonEmptyString("Assigned Doctor");

        String status = InputValidator.readAdmissionStatus("Admission Status", null, "add");

        Patient newPat = new Patient(patID, name, sex, addr, phone, dob, diagnosis, doctor, status, new Date(), null);

        if (patMan.add(newPat)) {
            OutputViewer.Successfully("Add", "Patient");
            patMan.saveToFile(patFile);
        }
    }

    public void deletePatient() {
        String patID = InputValidator.readValidString("Patient ID (Enter 0 to cancel)", null, "delete",
                id -> !id.isEmpty() && patMan.findByID(id) != null, "Error: Patient ID does not exist!!");
        if (patID == null) {
            OutputViewer.Cancelled("Delete", "Patient");
            return;
        }
        boolean confirm = InputValidator.readConfirm("delete this Patient");
        if (confirm) {
            patMan.delete(patID);
            OutputViewer.Successfully("Delete", "Patient");
            patMan.saveToFile(patFile);
        } else
            OutputViewer.Cancelled("Delete", "Patient");
    }

    public void updatePat() {
        String patID = InputValidator.readValidString("Patient ID (Enter 0 to cancel)", null, "delete",
                id -> !id.isEmpty() && patMan.findByID(id) != null, "Error: Patient ID does not exist!!");
        if (patID == null) {
            OutputViewer.Cancelled("Update", "Patient");
            return;
        }
        Patient oldPat = patMan.findByID(patID);

        String newName = InputValidator.readUpdateString("name", oldPat.getName());
        String newSex = InputValidator.readGender("gender", oldPat.getPatSex(), "update");
        String newAddr = InputValidator.readUpdateString("address", oldPat.getPatAddress());
        String newDOB = InputValidator.readDate("Date of Birth (dd/MM/yyyy)", oldPat.getPatDOB(), "update");
        String newPhone = InputValidator.readPhone("phone", oldPat.getPatPhone(), "update");
        String newDiag = InputValidator.readUpdateString("diagnosis", oldPat.getPatDiagnosis());
        String newDoc = InputValidator.readUpdateString("assigned doctor", oldPat.getAssignedDoctor());
        String newStatus = InputValidator.readAdmissionStatus("admission status", oldPat.getPatAdmissionStatus(),
                "update");

        Patient updPat = new Patient(patID, newName, newSex, newAddr, newPhone, newDOB, newDiag, newDoc, newStatus,
                oldPat.getCreateDate(), new Date());

        if (patMan.update(updPat)) {
            OutputViewer.Successfully("Update", "Patient");
            patMan.saveToFile(patFile);
        }
    }

    public void saveToFile() {
        patMan.saveToFile(patFile);
    }
}
