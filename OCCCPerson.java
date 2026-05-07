// Java Final
// Adan Villa
// OCCC Spring 2026
// Advanced Java


import java.io.Serializable;

public class OCCCPerson extends RegisteredPerson implements Serializable {
    private String studentID;

    public OCCCPerson(String firstName, String lastName, OCCCDate dob, String govID, String studentID) {
        super(firstName, lastName, dob, govID);
        this.studentID = studentID; //[cite: 4]
    }

    @Override
    public String toString() {
        return super.toString() + " (OCCC ID: " + studentID + ")"; //[cite: 4]
    }
}