//
// NonDeteminism.java
//
public class NonDeterminism {

  public static void main(String[] args) throws InterruptedException {
  
    class Container { public String value = "Empty"; }
    final Container container = new Container();

    class FastThread extends Thread {
      public void run() { 
        try { this.sleep(2000); } catch(Exception e) {}
        container.value = "Fast"; 
    } }

    class SlowThread extends Thread {
      public void run() {
        try { this.sleep(2100); } catch(Exception e) {}
        container.value = "Slow";
    } }
        
    FastThread fast = new FastThread();
    SlowThread slow = new SlowThread();
    slow.start(); fast.start();
    //slow.join(); fast.join();
    for (int i=0; i<100000; i++) { System.out.println(container.value); }
  }
}