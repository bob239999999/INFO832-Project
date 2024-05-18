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
    public void testAddDependence() {
        // Check initial action
        assertEquals(door.getClass(), actionDependent.getObject().getClass());
        assertEquals("open", actionDependent.getMethod().getName());

        // Simulate the actions
        actionDependent.nextMethod();
        assertEquals(light1.getClass(), actionDependent.getObject().getClass());
        assertEquals("on", actionDependent.getMethod().getName());

        actionDependent.nextMethod();
        assertEquals(light2.getClass(), actionDependent.getObject().getClass());
        assertEquals("on", actionDependent.getMethod().getName());

        actionDependent.nextMethod();
        assertEquals(door.getClass(), actionDependent.getObject().getClass());
        assertEquals("open", actionDependent.getMethod().getName());
    }

	@Test
    public void testConstruction() {
        // Check initial action
        assertEquals(door.getClass(), actionDependent.getObject().getClass());
        assertEquals("open", actionDependent.getMethod().getName());
    }

    @Test
    public void testAddDependence1() {
        // Simulate the actions
        actionDependent.nextMethod();
        assertEquals(light1.getClass(), actionDependent.getObject().getClass());
        assertEquals("on", actionDependent.getMethod().getName());
    }

    @Test
    public void testAddDependence2() {
        // Simulate the actions
        actionDependent.nextMethod();
        actionDependent.nextMethod();
        assertEquals(light2.getClass(), actionDependent.getObject().getClass());
        assertEquals("on", actionDependent.getMethod().getName());
    }

	/* 
	@Test
	public void testGetCurrentLapsTime() {
		// Initial state: base action
		actionDependent.nextMethod(); // door -> light1
		assertEquals(480, actionDependent.getCurrentLapsTime());

		// Advance to the first dependent action
		actionDependent.nextMethod(); // door -> light1
		assertEquals(10, actionDependent.getCurrentLapsTime());

		// Advance to the second dependent action
		actionDependent.nextMethod(); // light1 -> light2
		assertEquals(5, actionDependent.getCurrentLapsTime());

		// Cycle back to the base action
		actionDependent.nextMethod(); // light2 -> door
		assertEquals(480, actionDependent.getCurrentLapsTime());
	}
	*/

}
