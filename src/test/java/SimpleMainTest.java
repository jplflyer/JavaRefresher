import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleMainTest {

    @Test
    void testAdd() {
        SimpleMain sm = new SimpleMain();
        int result = sm.add(1, 2);
        assertEquals(3, result);
        System.out.println("Result: " + result);
    }
}