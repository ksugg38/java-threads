//
// CountingFixed.java
//
public class CountingFixed {
  public static int idCnt=1;
  public static void main(String[] args) throws InterruptedException {
  
    class Counter {
      private int counter = 0;
      public synchronized void increment() { counter++; }
      public synchronized int get() { return counter; }
    }
    
    final Counter counter = new Counter();
        
    class CountingThread extends Thread {
      private int id = idCnt++;
      public void run( ) {
        for (int i=0; i<500000; i++) { counter.increment(); }
        System.out.println("done t" + this.id); 
    } }

    CountingThread thread1 = new CountingThread();
    CountingThread thread2 = new CountingThread();
    thread1.start(); thread2.start();
    thread1.join(); thread2.join();
    System.out.println(counter.get());
  }
}