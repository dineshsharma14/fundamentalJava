import java.util.Arrays;

class PetOwner {

    public void start() {

        // Animals
        Dog d = new Dog();
        Cat c = new Cat();
        Fish f = new Fish();
            
        // a polymorphic array can take any animal
        Animal[] animals = new Animal[3];
        animals[0] = d;
        animals[1] = c;
        animals[2] = f;
        System.out.println(Arrays.toString(animals));

        // function which can take any animal as argument
        // as giveShot() is with the polymorphic parameter
        Vet v = new Vet();
        v.giveShot(d);
        v.giveShot(c);
        v.giveShot(f);

        // as only Fish animal is Printable
        v.printAnimal(f);
        
    }

    public static void main(String[] args) {
        new PetOwner().start();
    }
}
