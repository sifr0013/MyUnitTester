import se.umu.cs.unittest.TestClass;

/**
 * Created by Simon on 2018-11-20.
 */
public class Test2 implements TestClass
{
    private MyString myString;
    public Test2() {
    }

    public void setUp() {
        myString=new MyString();
    }

    public void tearDown() {
        myString=null;
    }

    //Test that should succeed
    public boolean testInitialisation() {
        return myString.getString()=="";
    }

    //Test that should succeed
    public boolean testBanana() {
        myString.setStringToBanana();
        return myString.getString() == "Banana";
    }

    //Test that should succeed
    public boolean testApple() {
        myString.setStringToApple();
        return myString.getString() == "Apple";
    }

    public boolean testOrange() {
        myString.setStringToOrange();
        return myString.getString() == "Orange";
    }

    //Test that should fail
    public boolean testFailingByException() {
        myString = null;
        myString.setStringToApple();
        return true;
    }

    //Test that should fail
    public boolean testFailing() {
        return false;
    }

    //Test that should fail
    public boolean testIfAppleIsOrange() {
        myString.setStringToApple();
        return myString.getString() == "Orange";
    }

    //Test that should fail
    public boolean testIfAppleIsBanana() {
        myString.setStringToApple();
        return myString.getString() == "Banana";
    }

    //Test that should fail
    public boolean testIfOrangeIsBanana() {
        myString.setStringToOrange();
        return myString.getString() == "Banana";
    }
}
