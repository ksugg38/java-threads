package implementation;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Semaphore;

public class PeckishPhilosopher {
    public static void main(String[] args) throws InterruptedException {
        final AtomicInteger counter = new AtomicInteger(0);

        class Philosopher {
            String id;
            int order;
            boolean isEating;
            boolean isThinking;


            public Philosopher(String id) {
                this.id = id;
                this.order = counter.getAndIncrement();
            }

            public boolean change(Philosopher next){
                synchronized (this) {
                    synchronized (next){
                        if (this.order < next.order) {
                            this.isEating = false;
                            this.isThinking = true;
                            next.isEating = true;
                            next.isThinking = false;
                            return true;
                        }
                        return false;
                    }
                }

            }
            public synchronized boolean getIsEating() { return isEating; }
            public synchronized boolean getIsThinking() { return isThinking; }
        }

        final Philosopher phil1 =  new Philosopher("A");
        phil1.isEating = true;
        phil1.isThinking = false;
        final Philosopher phil2 =  new Philosopher("B");
        phil2.isEating = false;
        phil2.isThinking = true;
        final Philosopher phil3 =  new Philosopher("C");
        phil3.isEating = true;
        phil3.isThinking = false;
        final Philosopher phil4 =  new Philosopher("D");
        phil4.isEating = false;
        phil4.isThinking = true;
        final Philosopher phil5 =  new Philosopher("E");
        phil5.isEating = false;
        phil5.isThinking = true;

        class RoundOne extends Thread {
            public void run(){
                for (int i = 0; i < 100000; i++) {
                    phil1.change(phil2);
                }
            }
        }

        class RoundTwo extends Thread {
            public void run(){
                for (int i = 0; i < 100000; i++) {
                    phil3.change(phil4);
                }
            }
        }

        RoundOne thread1 = new RoundOne();
        RoundTwo thread2 = new RoundTwo();
        thread1.start(); thread2.start();
        thread1.join(); thread2.join();
        System.out.println("phil1 is eating: " + phil1.getIsEating());
        System.out.println("phil2 is eating: " + phil2.getIsEating());
        System.out.println("phil3 is eating: " + phil3.getIsEating());
        System.out.println("phil4 is eating: " + phil4.getIsEating());
        System.out.println("phil5 is eating: " + phil5.getIsEating());

        System.out.println("phil1 is thinking: " + phil1.getIsThinking());
        System.out.println("phil2 is thinking: " + phil2.getIsThinking());
        System.out.println("phil3 is thinking: " + phil3.getIsThinking());
        System.out.println("phil4 is thinking: " + phil4.getIsThinking());
        System.out.println("phil5 is thinking: " + phil5.getIsThinking());



    }
}
