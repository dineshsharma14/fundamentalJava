import java.util.ArrayList;
import java.util.List;

public class Dinesh {

    private String mode;
    private int grumpiness;
    private int gratefullness;
    private boolean happy = true;

    // if property is a boolean getter should begin with "is"
    public boolean isHappy() {
        return happy;
    }

    // if property is non boolean getter should begin with "get"
    public String getMode() {
        return mode;
    }

    // setter method should begin with "set"
    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setHappy(boolean happy) {
        this.happy = happy;
    }

    public static void main(String... args) {

        Dinesh d = new Dinesh("satvik", 2, 8);
        System.out.println("Todays mode is: " + d.mode +
            ", and grumpiness on (1-10) is " + d.grumpiness +
            " happiness today is " + d.gratefullness);

        Dinesh d1 = new Dinesh("rajsik");
        System.out.println("Todays mode is: " + d1.mode +
            ", and grumpiness on (1-10) is " + d1.grumpiness +
            " happiness today is " + d1.gratefullness);

        // using lambada expressions - closures in Java
        List<String> myTasks = new ArrayList<String>();
        myTasks.add("java coding");
        myTasks.add("cheshta");
        myTasks.add("fun");
        myTasks.add("nature walk");

        System.out.println(myTasks);

        // let's do the main task
        myTasks.removeIf(tasks -> tasks.charAt(0) != 'c');
        System.out.println(myTasks);
    }

    public Dinesh(String mode) {
        this(mode, 3);
    }

    public Dinesh(String mode, int gru) {
        this(mode, gru, 7);
    }

    public Dinesh(String mode, int gru, int gra) {
        this.mode = mode;
        grumpiness = gru;
        gratefullness = gra;
    }

}
    
