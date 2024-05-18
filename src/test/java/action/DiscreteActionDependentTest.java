package action;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Timer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import timer.PeriodicTimer;

class DiscreteActionDependentTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testDiscreteActionDependent() {
		fail("Not yet implemented");
    }
	

	@Test
	void testAddDependence() {
		        // Création d'un timer pour la porte qui s'ouvre à 8h
		        PeriodicTimer doorTimer = new PeriodicTimer(24, 8);

		        // Création de l'action de la porte
		        DiscreteActionDependent doorAction = new DiscreteActionDependent(new Object() {
		            public void open() {
		                System.out.println("Door opened");
		            }
		        }, "open", doorTimer);

		        // Création d'un timer pour la première lampe qui s'allume à 8h10
		        PeriodicTimer lamp1Timer = new PeriodicTimer(24, 10);

		        // Ajout de la dépendance pour la première lampe
		        doorAction.addDependence(new Object() {
		            public void On() {
		                System.out.println("Lamp 1 turned on");
		            }
		        }, "On", lamp1Timer);

		        // Création d'un timer pour la deuxième lampe qui s'allume à 8h15
		        PeriodicTimer lamp2Timer = new PeriodicTimer(24, 15);

		        // Ajout de la dépendance pour la deuxième lampe
		        doorAction.addDependence(new Object() {
		            public void On() {
		                System.out.println("Lamp 2 turned on");
		            }
		        }, "On", lamp2Timer);

		        // Vérification que la porte s'ouvre à 8h
		        assertEquals("open", doorAction.getMethod().getName());
		        assertEquals((Integer) 8, doorAction.getCurrentLapsTime());

		        // Passe à la prochaine action (allumage de la première lampe à 8h10)
		        doorAction.nextMethod();
		        assertEquals("On", doorAction.getMethod().getName());
		        assertEquals((Integer) 10, doorAction.getCurrentLapsTime());

		        // Passe à la prochaine action (allumage de la deuxième lampe à 8h15)
		        doorAction.nextMethod();
		        assertEquals("On", doorAction.getMethod().getName());
		        assertEquals((Integer) 15, doorAction.getCurrentLapsTime());
		     }



	@Test
	void testNextMethod() {
		fail("Not yet implemented");
	}

	@Test
	void testSpendTime() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateTimeLaps() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMethod() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCurrentLapsTime() {
		fail("Not yet implemented");
	}

	@Test
	void testGetObject() {
		fail("Not yet implemented");
	}

	@Test
	void testCompareTo() {
		fail("Not yet implemented");
	}

	@Test
	void testIsEmpty() {
		fail("Not yet implemented");
	}

	@Test
	void testNext() {
		fail("Not yet implemented");
	}

	@Test
	void testHasNext() {
		fail("Not yet implemented");
	}

}
