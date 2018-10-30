package tests;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class testStuff {

    @Test
    public void testWereNotGoingNuts() {
        assertTrue(true);
    }

    public void testWeCanDoTehMaths() {
        assertEquals(1, 1);
        assertEquals(1, 2);

        assertEquals(2 + 2, 4);
    }
}
