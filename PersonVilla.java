// Java Final
// Adan Villa
// OCCC Spring 2026
// Advanced Java


import java.io.Serializable;

public class PersonVilla implements Serializable, Comparable<PersonVilla> {
    protected String firstName, lastName;
    protected OCCCDate dob; 

    public PersonVilla(String firstName, String lastName, OCCCDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob; //[cite: 5]
    }

    @Override
    public int compareTo(PersonVilla other) {
        int lastComp = this.lastName.compareTo(other.lastName);
        if (lastComp != 0) return lastComp;
        int firstComp = this.firstName.compareTo(other.firstName);
        if (firstComp != 0) return firstComp;
        return this.dob.compareTo(other.dob); //[cite: 5]
    }

    @Override
    public String toString() { return firstName + " " + lastName + " (" + dob + ")"; } //[cite: 5]
}