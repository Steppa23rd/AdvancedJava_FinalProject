// Java Final
// Adan Villa
// OCCC Spring 2026
// Advanced Java


import java.io.Serializable;
import java.util.GregorianCalendar;

public class OCCCDate implements Comparable<OCCCDate>, Serializable {
    private int day, month, year;

    public OCCCDate(int day, int month, int year) {
        if (!isValid(day, month, year)) {
            throw new InvalidOCCCDateException("Date " + month + "/" + day + "/" + year + " is invalid."); //[cite: 3]
        }
        this.day = day;
        this.month = month;
        this.year = year;
    }

    private boolean isValid(int day, int month, int year) {
        GregorianCalendar cal = new GregorianCalendar(year, month - 1, day);
        return (cal.get(GregorianCalendar.DAY_OF_MONTH) == day &&
                cal.get(GregorianCalendar.MONTH) == (month - 1) &&
                cal.get(GregorianCalendar.YEAR) == year); //[cite: 3]
    }

    @Override
    public int compareTo(OCCCDate other) {
        if (this.year != other.year) return this.year - other.year;
        if (this.month != other.month) return this.month - other.month;
        return this.day - other.day; //[cite: 3]
    }

    @Override
    public String toString() { return month + "/" + day + "/" + year; } //[cite: 3]
}