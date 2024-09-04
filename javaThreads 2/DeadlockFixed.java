//
// DeadlockFixed.java
//
import java.util.concurrent.atomic.AtomicInteger;

public class DeadlockFixed {
    public static void main(String[] args) throws InterruptedException {
        final AtomicInteger counter = new AtomicInteger(0);
        class Account {
            int balance = 100;
            int order;
            public Account(int balance) {
                this.balance = balance;
                this.order = counter.getAndIncrement();
            }
            public synchronized void deposit(int amount) { balance += amount; }
            public synchronized boolean withdraw(int amount) {
                if (balance >= amount) {
                    balance -= amount;
                    return true;
                }
                return false;
            }
            public boolean transfer(Account destination, int amount) {
                Account first;
                Account second;
                if (this.order < destination.order) {
                    first = this;
                    second = destination;
                }
                else {
                    first = destination;
                    second = this;
                }
                synchronized(first) {
                    synchronized(second) {
                        if (balance >= amount) {
                            balance -= amount;
                            destination.balance += amount;
                            return true;
                        }
                        return false;
                    }
                }
            }
            public synchronized int getBalance() { return balance; }
        }

        final Account bob = new Account(200000);
        final Account jae = new Account(300000);

        class FirstTransfer extends Thread {
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    bob.transfer(jae, 2);
                }
            }
        }
        class SecondTransfer extends Thread {
            public void run() {
                for (int i = 0; i < 100000; i++) {
                    jae.transfer(bob, 1);
                }
            }
        }

        FirstTransfer thread1 = new FirstTransfer();
        SecondTransfer thread2 = new SecondTransfer();
        thread1.start(); thread2.start();
        thread1.join(); thread2.join();
        System.out.println("Bob's balance: " + bob.getBalance());
        System.out.println("Jae's balance: " + jae.getBalance());
    }
}