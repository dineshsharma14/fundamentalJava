// Concurrency problem example
 
class RyanMonicaJob implements Runnable {

    private BankAccount account = new BankAccount();

    public static void main(String[] args) {
        RyanMonicaJob theJob = new RyanMonicaJob();
        Thread one = new Thread(theJob);
        Thread two = new Thread(theJob);
        one.setName("Ryan");
        two.setName("Monica");
        one.start();
        two.start();
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("\n**** " + Thread.currentThread().getName() + " " + i);
            makeWithdrawal(10);
            if (account.getBalance() < 0) {
                System.out.println("Overdrawn!");
            }
        }
    }

    private void makeWithdrawal(int amount) {
        if (account.getBalance() >= amount) {
            System.out.println(Thread.currentThread().getName() + " is about to withdraw.");
            try {
                System.out.println(Thread.currentThread().getName() + " is going to sleep.\n");
                Thread.sleep(500);
            } catch (InterruptedException ex) {ex.printStackTrace();}
            System.out.println(Thread.currentThread().getName() + " woke up.");
            account.withdraw(amount);
            System.out.println(Thread.currentThread().getName() + " completes withdrawl.\n");
        }
        else {
            System.out.println("Sorry, not enough for " + Thread.currentThread().getName() + ".");
        }
    }
}
        
