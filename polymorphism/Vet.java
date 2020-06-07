class Vet {
    
    public void giveShot(Animal a) {

        System.out.println("\nVet giving the shot!");        
        a.makeNoise();

    }

    public void printAnimal(Printable a) {

        System.out.println("\nLet's print the animal!");
        System.out.println(a.printTheName());

    }

}
