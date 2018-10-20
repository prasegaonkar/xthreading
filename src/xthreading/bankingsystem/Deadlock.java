package xthreading.bankingsystem;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {
	public static void main(String[] args) throws InterruptedException {
		Account acc1 = new Account();
		Account acc2 = new Account();
		Lock lock1 = new ReentrantLock();
		Lock lock2 = new ReentrantLock();

		Thread t1 = new Thread(new TransferProcess(acc1, acc2, lock1, lock2));
		Thread t2 = new Thread(new TransferProcess(acc2, acc1, lock2, lock1));

		t1.start();
		t2.start();
		t1.join();
		t2.join();

		System.out.println("Account 1 balance: " + acc1.getBalance());
		System.out.println("Account 2 balance: " + acc2.getBalance());
		System.out.println("Total balance: " + (acc1.getBalance() + acc2.getBalance()));

	}
}
