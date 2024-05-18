package action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timer.Timer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiscreteActionDependentTest {

    private static class Door {
        public void open() {
            System.out.println("Door opened");
        }
    }

    private static class Light {
        public void on() {
            System.out.println("Light on");
        }
    }

    private DiscreteActionDependent actionDependent;
    private Door door;
    private Light light1;
    private Light light2;

    @BeforeEach
    public void setUp() {
        door = new Door();
        light1 = new Light();
        light2 = new Light();

        Timer timerBase = new Timer() {
            @Override
            public Integer next() {
                return 480; // 8:00 AM
            }

            @Override
            public boolean hasNext() {
                return true;
            }
        };

        Timer timerDependence1 = new Timer() {
            @Override
            public Integer next() {
                return 10; // 10 minutes after door opens
            }

            @Override
            public boolean hasNext() {
                return true;
            }
        };

        Timer timerDependence2 = new Timer() {
            @Override
            public Integer next() {
                return 5; // 5 minutes after first light
            }

            @Override
            public boolean hasNext() {
                return true;
            }
        };

        actionDependent = new DiscreteActionDependent(door, "open", timerBase);
        actionDependent.addDependence(light1, "on", timerDependence1);
        actionDependent.addDependence(light2, "on", timerDependence2);
    }

	@Test
    public void testSpendTime() {
        // Initial time should be at 8:00 AM for the door
        actionDependent.spendTime(480); // 8:00 AM
		actionDependent.nextMethod();
        assertEquals(0, actionDependent.getCurrentLapsTime());

        // Light 1 should turn on at 8:10 AM
        actionDependent.nextMethod();
        actionDependent.spendTime(10); // 8:10 AM
        assertEquals(0, actionDependent.getCurrentLapsTime());

        // Light 2 should turn on at 8:15 AM
        actionDependent.nextMethod();
        actionDependent.spendTime(5); // 8:15 AM
        assertEquals(0, actionDependent.getCurrentLapsTime());
    }

}
