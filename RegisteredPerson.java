// Java Final
// Adan Villa
// OCCC Spring 2026
// Advanced Java


import java.io.Serializable;

public class RegisteredPerson extends PersonVilla implements Serializable {
    private String governmentID;

    public RegisteredPerson(String firstName, String lastName, OCCCDate dob, String id) {
        super(firstName, lastName, dob);
        this.governmentID = id; //[cite: 6]
    }

    @Override
    public String toString() {
        return super.toString() + " [GovID: " + governmentID + "]"; //[cite: 6]
    }
}