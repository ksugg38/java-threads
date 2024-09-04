public class FooStuff2 implements Runnable {

  public void run( ) {
    System.out.println("doing foo2 stuff");
  }
   
  public static void main (String[] args) {
    Thread thRun = new Thread(new FooStuff2());
    thRun.start();
   }

}
