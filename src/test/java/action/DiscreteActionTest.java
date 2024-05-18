package action;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import action.DiscreteAction;
import timer.Timer;
import timer.RandomTimer;
import java.lang.reflect.Method;
import java.util.Arrays;

class DiscreteActionTest {


    class SpecificClass {
        public void methodOne() {
            // Some logic
        }
    
        public void methodTwo() {
            // Some logic
        }
    
        public void methodThree() {
            // Some logic
        }
    }
    @Test
    public void testConstructor() {
        Object object = new SpecificClass();
        Timer timer;
        try {
            timer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 5);
            Method[] methods = object.getClass().getDeclaredMethods();

            Arrays.stream(methods).forEach(method -> {
                try {
                    DiscreteAction discreteAction = new DiscreteAction(object, method.getName(), timer);
                    assertNotNull(discreteAction);
                    System.out.println("DiscreteAction created for method: " + method.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConstructorException() throws Exception {
        Object object = new SpecificClass();
        Timer timer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 5);
        
        try {
            // Attempt to get the method
            Method method = object.getClass().getDeclaredMethod("nonExistentMethod");
            
            // If the method exists, fail the test
            fail("Expected NoSuchMethodException, but no exception was thrown");
        } catch (NoSuchMethodException e) {
            // This is the expected behavior, so the test should pass
        } catch (Exception e) {
            // Catch any unexpected exceptions and print the stack trace for debugging
            e.printStackTrace();
            fail("Unexpected exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testGetMethod() throws Exception {
        // Create an instance of the specific class
        Object object = new SpecificClass();
                
        // Create a timer
        Timer timer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 5);

        // Method name to test
        String methodName = "methodOne";

        // Create a DiscreteAction for the method
        DiscreteAction discreteAction = new DiscreteAction(object, methodName, timer);

        // Get the method using reflection
        Method method = object.getClass().getDeclaredMethod(methodName);

        // Check if the method associated with the DiscreteAction is the same as the provided method
        assertEquals(method, discreteAction.getMethod(), "Method should be the same");
    }

    @Test
    public void testSpendTime() {
        // Step 1: Create a DiscreteAction object with a specified initial lapsTime
        Object object = new SpecificClass();
        Timer timer;
        try {
            timer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 25);
            
            DiscreteAction discreteAction = new DiscreteAction(object, "methodOne", timer);
            discreteAction.next();
            int lapsTime = discreteAction.getCurrentLapsTime(); 
            discreteAction.spendTime(5);

            // Step 3: Assert that the lapsTime has been updated correctly
            assertEquals(lapsTime -5, discreteAction.getCurrentLapsTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    


    @Test
    public void testSpendTimeNull() {
        Object object = new SpecificClass();
        Timer timer;
        try {
            DiscreteAction discreteAction = new DiscreteAction(object, "methodOne", null);
            discreteAction.spendTime(5);
            assertNull(discreteAction.getCurrentLapsTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetCurrentLapTime() throws Exception {
        Object object = new SpecificClass();
        Timer timer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 5);
        DiscreteAction discreteAction = new DiscreteAction(object, "methodOne", timer);
        discreteAction.next();
        assertNotNull(discreteAction.getCurrentLapsTime());
    }

    @Test
    public void testHasNext() throws Exception {
        DiscreteAction discreteAction = new DiscreteAction(new SpecificClass(), "methodOne", null);
        assertFalse(discreteAction.hasNext());
    }

    @Test
    public void testCompareTo() throws Exception {
        Timer timer;
        try {
            timer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 50);
            DiscreteAction discreteAction1 = new DiscreteAction(new SpecificClass(), "methodOne", timer);
            discreteAction1.next();
            // Laps Time 50 
            discreteAction1.spendTime(10);
            // Laps Time Current is 40 
            DiscreteAction discreteAction2 = new DiscreteAction(new SpecificClass(), "methodTwo", timer);
            discreteAction2.spendTime(5);
            // Laps Time Current is 45 
            assertTrue(discreteAction2.compareTo(discreteAction1) > 0);
            // Positive because 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetMethodBlackBox() throws Exception {
        Object object = new SpecificClass();
        Timer timer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 5);
        DiscreteAction discreteAction = new DiscreteAction(object, "methodOne", timer);
        assertEquals("methodOne", discreteAction.getMethod().getName());
    }
}
