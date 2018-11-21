/**
 * OutPutAreaModel functions as the Model of the program. It is used to structure the information displayed
 * in the GUI.
 */
public class OutputAreaModel
{
    private boolean succeeded;
    private String methodName;
    private String errorMsg = "";

    /**
     * Constructor for the OutPutAreaModel. Is used for when the message to be displayed is without exception.
     * @param methodName The name of the method performed.
     * @param succeeded The test result of the method performed.
     */
    public OutputAreaModel(String methodName, boolean succeeded)
    {
        this.methodName = methodName;
        this.succeeded = succeeded;
    }

    /**
     * Constructor for the OutPutAreaModel. Is used for when the message to be displayed has an exception.
     * @param methodName The name of the method performed.
     * @param succeeded The test result of the method performed.
     * @param errorMsg The exception string.
     */
    public OutputAreaModel(String methodName, boolean succeeded, String errorMsg)
    {
        this.methodName = methodName;
        this.succeeded = succeeded;
        this.errorMsg = errorMsg;
    }

    /**
     * Method to get the print out string of the OutputAreaModel, meaning the name of the method, the result and
     * if it has an errormessage it attaches that as well.
     * @return The string containing information about the method performed.
     */
    public String printOutString()
    {
        String print = methodName +":   ";
        if (succeeded)
        {
            print += "SUCCESS";
        }
        else
        {
            print += "FAIL";
            if (errorMsg != "")
            {
                print += " Generated a "+errorMsg;
            }
        }
        return print;
    }

    /**
     * Method to check if the OutputAreaModel has an exception message attached.
     * @return True - if the OutputAreaModel has an exception message attached.
     *          False - if the OutputAreaModel does not have an exception message attached.
     */
    public boolean hasErrorMsg()
    {
        return errorMsg != "";
    }

    /**
     * Method to check if the method performed succeeded or not.
     * @return True - if the method performed succeeded.
     *          False - if the method performed failed.
     */
    public boolean getSucceeded()
    {
        return succeeded;
    }
}
