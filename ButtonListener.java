import se.umu.cs.unittest.TestClass;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * ButtonListener functions as the Controller of the program. Depending on the input from
 * the user, it outputs different things.
 */
public class ButtonListener implements ActionListener
{
    private JTextField textField;
    private JTextArea textArea;

    /**
     * Constructor for a ButtonListener which determines what actions to do, depending on what type of button it is.
     * @param textField The inputfield for the name of the test.
     * @param textArea The outputfield for the tests.
     */
    public ButtonListener (JTextField textField, JTextArea textArea)
    {
        this.textField = textField;
        this.textArea = textArea;
    }

    /**
     * Depending on what button the user is pressing on, different things will happen.
     * clearButton - The actionPerformed method clears the information in the GUI.
     * runTestsButton - The actionPerformed method tries to run the test file provided in the
     *                  text input area.
     * @param e A mouseclick on either the clearButton or the runTestsButton.
     */
    public void actionPerformed (ActionEvent e)
    {
        if (e.getActionCommand()=="clearButton")
        {
            textField.setText("");
            textArea.setText("Please fill in the upper field and press 'Run Tests'.");

        }
        if (e.getActionCommand()=="runTestsButton")
        {
            try
            {
                String className = textField.getText().toString();

                Class cls = Class.forName(className);

                if (isValidClass(cls))
                {
                    Object clsInstance = cls.newInstance();
                    Method[] methods = clsInstance.getClass().getMethods();
                    ArrayList<OutputAreaModel> oam = new ArrayList<>();


                    runMethods(methods,clsInstance,oam);

                    updateTextArea(oam);
                }
                else
                {
                    textArea.setText("The test file provided have incorrect number of parameters or " +
                            "does not implement TestClass.");
                }

            }
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException e2)
            {
                textArea.setText("The test file provided does not exist. Please try again.");
            }
        }
    }

    /**
     * Checks if the Test class provided is a valid class.
     * 1. Checks if the class implements TestClass.
     * 2. Checks if the class has a constructor with no parameters.
     * @param c The Test Class provided that needs validification.
     * @return True - if the test class is valid.
     * @return False - if the test class is not valid.
     */
    private boolean isValidClass(Class c)
    {
        boolean implementsTestClass = false;

        Class[] i = c.getInterfaces();

        for (Class anI : i)
        {
            if (anI.getName() == TestClass.class.getName())
            {
                implementsTestClass = true;
                break;
            }
        }

        Constructor[] cns = c.getConstructors();

        boolean hasCorrectConstructor = false;

        for (Constructor cn : cns)
        {
            if (cn.getParameterCount() == 0)
            {
                hasCorrectConstructor = true;
            }
        }

        return implementsTestClass && hasCorrectConstructor;
    }

    /**
     * Method to run the setUp method of the test class provided, if such a method exists that equals to 'setUp'.
     * Should be called before every test method.
     * @param methods An array of methods to the test class provided.
     * @param instance An instance of the test class.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void runSetUp(Method[] methods, Object instance) throws InvocationTargetException, IllegalAccessException
    {
        for (Method method : methods)
        {
            if (method.getName() == "setUp")
            {
                method.invoke(instance);
            }
        }
    }

    /**
     * Method to run the test methods of the test class provided, if they begin with 'test', returns a boolean and
     * has no parameters.
     * @param methods An array of methods to the test class provided.
     * @param instance An instance of the test class.
     * @param oam An ArrayList of the type OutputAreaModel.
     */
    private void runMethods(Method[] methods, Object instance, ArrayList<OutputAreaModel> oam)
    {
        for (Method m : methods)
        {
            String name = m.getName();

            if (!name.startsWith("test") || m.getGenericReturnType() != boolean.class || m.getParameterCount() != 0)
            {
                continue;
            }

            try
            {
                runSetUp(methods, instance);
                Object o = m.invoke(instance);
                oam.add(new OutputAreaModel(name, (Boolean) o));
                runTearDown(methods, instance);
            }
            catch (InvocationTargetException x)
            {
                Throwable c = x.getCause();
                oam.add(new OutputAreaModel(name, false, c.toString()));
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Method to run the tearDown method of the test class provided, if such a method exists that equals to 'tearDown'.
     * Should be called after every test method.
     * @param methods An array of methods to the test class provided.
     * @param instance An instance of the test class.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void runTearDown(Method[] methods, Object instance) throws InvocationTargetException, IllegalAccessException
    {
        for (Method method : methods)
        {
            if (method.getName() == "tearDown")
            {
                method.invoke(instance);
            }
        }
    }

    /**
     * Method to update the information displayed in the GUI. Also summarizes the amount of methods failed,
     * failed with an exception and succeeded.
     * @param oam An ArrayList of OutputAreaModel.
     */
    private void updateTextArea(ArrayList<OutputAreaModel> oam)
    {
        String textAreaString = "";
        int sucCount=0;
        int excCount=0;
        int faiCount=0;

        for (OutputAreaModel o : oam)
        {
            textAreaString += o.printOutString()+"\n";
            if (o.getSucceeded())
            {
                sucCount++;
            }
            else if (o.hasErrorMsg())
            {
                excCount++;
            }
            else
            {
                faiCount++;
            }
        }

        textAreaString += "\n\n";
        textAreaString += sucCount + " tests succeeded\n";
        textAreaString += faiCount + " tests failed\n";
        textAreaString += excCount + " tests failed because of an exception\n";

        textArea.setText(textAreaString);
    }
}
