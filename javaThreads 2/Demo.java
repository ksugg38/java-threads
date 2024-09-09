public class Demo {
    public static void main(String[] args) {
        int numRounds = 5; // Set how many times the philosophers should eat and think
        PeckishPhilosopher.setRounds(numRounds);

        // Create 5 philosopher threads
        Thread[] philosophers = new Thread[5];
        for (int i = 0; i < 5; i++) {
            philosophers[i] = new Thread(new PeckishPhilosopher(i));
            philosophers[i].start();
        }

        // Wait for all threads to finish
        for (Thread philosopher : philosophers) {
            try {
                philosopher.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("All philosophers have finished " + numRounds + " rounds.");
    }
}
