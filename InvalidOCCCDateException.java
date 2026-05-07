// Java Final
// Adan Villa
// OCCC Spring 2026
// Advanced Java

// Custom exception that extends IllegalArgumentException
public class InvalidOCCCDateException extends IllegalArgumentException
{
    public InvalidOCCCDateException()
    {
        super("The provided date is invalid.");
    }

    public InvalidOCCCDateException(String message)
    {
        super(message);
    }
}