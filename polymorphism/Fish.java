public class Fish extends Animal implements Printable {

    String food = "plankton";

    public void makeNoise() {
        System.out.println("pip pip");
    }

    public void eat() {
        System.out.println("whispering!!");
    }

    public String printTheName() {
        return "Humu Humu Nuku Nuku Apua's";
    }
}
