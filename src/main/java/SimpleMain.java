import java.util.Arrays;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class SimpleMain {
    private static final Logger logger = LogManager.getLogger(SimpleMain.class);

    interface SimpleInterface {
        public void run(String str, int n);
    }

    public static void main(String [] args) {
        logger.info("Application started");
        System.out.println("Hello, World!");

        var list = Arrays.asList(1, 2, 3, 4, 5);
        list.forEach( n -> { System.out.println("Value: " + n); } );

        SimpleInterface si = (a, b) -> System.out.println("Char " + b + " == " + a.charAt(b));
        printFormatted("Hello, World!", si);

    }
    public static void printFormatted(String str, SimpleInterface format) {
        for (int index = 0; index < str.length(); ++index) {
            format.run(str, index);
        }
    }

    public int add(int a, int b) {
        return a + b;
    }
}
