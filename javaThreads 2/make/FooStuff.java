public class FooStuff extends Thread {

  public void run( ) {
    System.out.println("doing foo stuff");
  }
   
  public static void main (String[] args) {
    FooStuff thExt = new FooStuff();
    thExt.start();
  }

}
