package manager;
import model.*;
import view.OutputViewer;

public class PatientManager extends BaseManager<Patient>{

    @Override public boolean update(Patient newPat){
        Patient updPatID = findByID(newPat.getPatientID()); 
        if (updPatID != null){
            int patInd = list.indexOf(updPatID); 
            list.set(patInd, newPat); 
            return true; 
        }
        return false;
    }

    public void printHeader(){
        System.out.printf("| %-15s | %-30s | %-7s | %-50s | %-10s | %-12s | %-20s | %-20s | %-15s | %-12s | %-12s |\n", 
                "Patient ID", "Full Name", "Sex", "Address", "DOB", "Phone", "Diagnosis", "Assigned Doctor", "Status", "Create Date", "Update Date");
    }

    public void showAll(){
        if (isEmptyList("Patient")) return;
        printHeader();
        for (Patient pat : list) pat.showInfo();
    }

    public void searchPatientByID(String patID){
        if (isEmptyList("Patient")) return;
        Patient p = findByID(patID);
        if (p == null) OutputViewer.unFind("Patient", patID);
        else {
            printHeader();
            p.showInfo();
        }
    }

    public void searchAdvance(String keyword){
        if (isEmptyList("Patient")) return;
        boolean found = false;
        String keyLower = keyword.toLowerCase().trim();
        for (Patient p : list){
            boolean isMatch = p.getPatientID().toLowerCase().contains(keyLower) || 
                              p.getPatientName().toLowerCase().contains(keyLower) || 
                              p.getPatDiagnosis().toLowerCase().contains(keyLower) || 
                              p.getAssignedDoctor().toLowerCase().contains(keyLower) || 
                              p.getPatAdmissionStatus().toLowerCase().contains(keyLower);
            if (isMatch){
                if (!found){
                    printHeader();
                    found = true;
                }
                p.showInfo();
            }
        }
        if (!found) OutputViewer.unFind("Patient", keyword);
    }
}
