import lombok.Getter;
import lombok.Setter;

/**
 * This is where I'm going to do a little testing with lombok.
 */
public class Data {
    public static void main(String [] args) {
        System.out.println("Hello World!");
    }

    @Getter @Setter private String name;
}
