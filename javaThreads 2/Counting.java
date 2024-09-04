//
// Counting.java
//
// testtt
public class Counting {

  static int idCnt=1;
  
  public static void main(String[] args) throws InterruptedException {
  
	 class Counter {
	   private int counter = 0;
	   public void increment() { counter++; }
	   public int get() { return counter; }
	 }
	
    final Counter counter = new Counter();
        
    class CountingThread extends Thread {
      private int id = idCnt++;
      public void  run() {
        for (int i=0; i<500000; i++) { counter.increment(); }
        System.out.println("done t" + this.id); 
    } }

    CountingThread t1 = new CountingThread(); 
    CountingThread t2 = new CountingThread(); 

    t1.start(); //t1.join();
    t2.start(); //t2.join();
    t1.join();  t2.join();
    System.out.println(counter.get());
  }
}