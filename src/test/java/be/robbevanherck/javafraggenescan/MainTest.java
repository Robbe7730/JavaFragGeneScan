package be.robbevanherck.javafraggenescan;

import com.beust.jcommander.ParameterException;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void main() throws InterruptedException {
        // Test the required parameters
        boolean hasThrown = false;
        try {
            Main.main(new String[]{});
        } catch (ParameterException ignored) {
            hasThrown = true;
        }

        assertTrue("main() did not ask for the required parameters", hasThrown);
    }
}