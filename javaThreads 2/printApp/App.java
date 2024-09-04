public class App {

  //public static void main (String[] args) throws InterruptedException{
  public static void main (String[] args) throws InterruptedException{

    // job 1
    System.out.println("-- doing job 1 -------");
    
    // job 2
    MyTask task = new MyTask();
    //task.execWork();
    task.start();
    
    // job 3
    for (int i=1; i<=10; i++) {
      System.out.println("^^ printing doc #"+i);
    }
    
    //task.join();
    //job 4
    System.out.println("-- doing job 4 -------");
  }
}

/*
class MyTask {
  public void execWork() {
    for (int i=1; i<=10; i++) {
      System.out.println("@@ printing doc #"+i +" -- printer B");
    }
  }
}
*/

class MyTask extends Thread {
  //public void execWork() {
  public void run( ) {
    for (int i=1; i<=10; i++) {
      System.out.println("@@ printing doc #"+i +" -- printer B");
    }
  }
}