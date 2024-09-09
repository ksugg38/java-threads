import java.util.concurrent.Semaphore;
import java.util.Random;

class PeckishPhilosopher implements Runnable {
    //semaphore to allow only 2 philosophers to eat at the same time
    private static final Semaphore semaphore = new Semaphore(2);

    private static final int numPhilosophers = 5;

    //random number generator for random sleep intervals
    private static final Random random = new Random();

    private static int firstPhilosopher = 0;
    private static int secondPhilosopher = 2;

    private static int rounds;

    private static int firstEatingTime = 0;
    private static int secondEatingTime = 0;

    //variables to track when both philosophers are done eating
    private static boolean firstPhilosopherDone = false;
    private static boolean secondPhilosopherDone = false;

    private final int philosopherId;
    
    private static final Object roundLock = new Object();

    public PeckishPhilosopher(int id) {
        this.philosopherId = id;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < rounds; i++) {
                synchronized (roundLock) {
                    //philosophers eat if they are selected
                    if (canEat(philosopherId)) {
                        //acquire semaphore to make sure only 2 philosophers eat at a time
                        semaphore.acquire();
                        eat(i + 1);
                        semaphore.release();
                    }
                }
                think(i + 1);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //check if the philosopher is allowed to eat
    private boolean canEat(int philosopherId) {
        return philosopherId == firstPhilosopher || philosopherId == secondPhilosopher;
    }

    private void eat(int round) throws InterruptedException {
        int eatingTime = random.nextInt(1000) + 1000;
        if (philosopherId == firstPhilosopher) {
            firstEatingTime = eatingTime;
        } else if (philosopherId == secondPhilosopher) {
            secondEatingTime = eatingTime;
        }

        System.out.println("Round " + round + ": Philosopher " + philosopherId + " is eating for " + eatingTime + " milliseconds.");

        Thread.sleep(eatingTime);

        // synchronize the two philosophers' eating times
        synchronized (roundLock) {
            if (philosopherId == firstPhilosopher) {
                firstPhilosopherDone = true;
            } else if (philosopherId == secondPhilosopher) {
                secondPhilosopherDone = true;
            }

            //check if both philosophers are done eating, sync
            checkAndSyncPhilosophers(round);
        }
    }

    // Simulate philosopher thinking
    private void think(int round) throws InterruptedException {
        System.out.println("Round " + round + ": Philosopher " + philosopherId + " is thinking.");
        Thread.sleep(1000); 
    }

    // Check both philosophers and ensure they move to the next round only when both are done eating
    private static void checkAndSyncPhilosophers(int round) throws InterruptedException {
        //check if both philosophers are done eating
        if (firstPhilosopherDone && secondPhilosopherDone) {
            int longerEatingTime = Math.max(firstEatingTime, secondEatingTime);
            int shorterEatingTime = Math.min(firstEatingTime, secondEatingTime);

            //the philosopher with the shorter eating time should wait for the difference
            int timeDifference = longerEatingTime - shorterEatingTime;

            System.out.println("Philosopher with the shorter eating time waits for " + timeDifference + " milliseconds.");
            Thread.sleep(timeDifference); 
            //reset done status for the next round
            firstPhilosopherDone = false;
            secondPhilosopherDone = false;

            System.out.println("Both philosophers have finished Round " + round + ".");

            randomSleepBeforeNextRound();

            shiftPhilosophers();
        }
    }

    //shift the two eating philosophers to the right
    private static void shiftPhilosophers() {
        firstPhilosopher = (firstPhilosopher + 1) % numPhilosophers;
        secondPhilosopher = (firstPhilosopher + 2) % numPhilosophers;

        System.out.println("Next philosophers to eat: " + firstPhilosopher + " and " + secondPhilosopher);
    }

    //sleep for a random amount of time before the next round starts
    private static void randomSleepBeforeNextRound() throws InterruptedException {
        int sleepTime = random.nextInt(3000) + 1000; 
        System.out.println("Sleeping for " + sleepTime + " milliseconds before next round.");
        Thread.sleep(sleepTime); 
    }

    public static void setRounds(int numRounds) {
        rounds = numRounds;
    }
}
}
