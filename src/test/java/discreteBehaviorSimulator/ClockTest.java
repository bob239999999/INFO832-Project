package discreteBehaviorSimulator;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;


import edu.emory.mathcs.backport.java.util.concurrent.ExecutorService;
import edu.emory.mathcs.backport.java.util.concurrent.Executors;
import edu.emory.mathcs.backport.java.util.concurrent.Future;
import edu.emory.mathcs.backport.java.util.concurrent.TimeUnit;


class ClockTest {

    

	// Test to verify that Clock is a Singleton
    @Test
    void testSingleton() {
        Clock firstInstance = Clock.getInstance();
        Clock secondInstance = Clock.getInstance();
        assertSame(firstInstance, secondInstance, "Both instances should be the same (Singleton)");
    }

    // Test to verify the behavior of getTime when virtual is false
    @Test
    void testGetTimeVirtualFalse() {
        Clock clock = Clock.getInstance();
        clock.setVirtual(false);
        long currentTime = System.currentTimeMillis();
        long clockTime = clock.getTime();
        assertTrue(Math.abs(currentTime - clockTime) < 100, "The time should be the current system time within 100 milliseconds");
        // Using assertTrue with a condition to check the difference due to minor timing variations
    }

    // Test to verify the method increase with an unexpected value
    @Test
    void testIncreaseWithInvalidTime() {
        Clock clock = Clock.getInstance();
        clock.setNextJump(10); // Set the expected next jump to 10
        Exception exception = assertThrows(Exception.class, () -> clock.increase(5),
                "An exception should have been thrown for an unexpected time jump");
        assertTrue(exception.getMessage().contains("Unexpected time change"), "Exception message should indicate an unexpected time change");
    }
    
 // Test to verify the method increase with a negative value
    @Test
    void testIncreaseWithNegativeValue() {
        Clock clock = Clock.getInstance();
        // Setting up the expected next jump to handle the internal logic of the 'increase' method
        clock.setNextJump(10);
        
        // Test to check behavior when 'increase' is called with a negative value
        Exception exception = assertThrows(Exception.class, () -> clock.increase(-2),
            "An exception should have been thrown for a negative time increase");
        
        // Verify that the error message is appropriate
        assertTrue(exception.getMessage().contains("Unexpected time change"),
            "Exception message should indicate that negative values are not allowed or unexpected");
    }

    // Test to verify write locks during a read lock
    @Test
    void testWriteLockWhenReading() throws Exception {
        final Clock clock = Clock.getInstance();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Acquire read lock in main thread
        clock.lockReadAccess();

        // Attempt to acquire write lock in another thread
        Future writeAttempt = executor.submit(() -> {
            clock.lockWriteAccess();
            try {
                // If this code is executed, then the write lock has been acquired
                fail("Should not be able to acquire write lock while read lock is held");
            } finally {
                clock.unlockWriteAccess();
            }
        });

        // Wait a bit to see if the write lock can be acquired
        try {
            writeAttempt.get(500, TimeUnit.MILLISECONDS); // Use a short delay
            fail("Write lock should not be acquired while a read lock is held");
        } catch (Exception e) {
            // Expected timeout or interruption - test should pass if we catch an exception here
        }

        // Release read lock
        clock.unlockReadAccess();
        executor.shutdownNow();
    }

}