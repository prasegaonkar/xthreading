package xthreading;

import java.util.Scanner;

public class WaitNotify {
	public static void main(String[] args) throws InterruptedException {

		Object mutex = new Object();

		Thread producer = new Thread(new Producer(mutex));

		Thread consumer = new Thread(new Consumer(mutex));

		producer.start();
		consumer.start();

		producer.join();
		consumer.join();

	}

	private static class Producer implements Runnable {
		private Object mutex;

		public Producer(Object mutex) {
			this.mutex = mutex;
		}

		public void run() {
			synchronized (mutex) {
				System.out.println("producer is running...");
				try {
					System.out.println("producer is relinquishing the lock...");
					mutex.wait();
				} catch (InterruptedException e) {
				}
				System.out.println("producer is resumed for completion.");
			}
		}
	}

	private static class Consumer implements Runnable {
		private Object mutex;

		public Consumer(Object mutex) {
			this.mutex = mutex;
		}

		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			Scanner scanner = new Scanner(System.in);
			synchronized (mutex) {
				System.out.println("consumer waiting for return key...");
				scanner.nextLine();
				System.out.println("Notifying others...");
				scanner.close();
				mutex.notify();
				System.out.println("But won't relinquish the lock...");
				try {
					Thread.sleep(5000);
					System.out.println("comsumer has now completed.");
				} catch (InterruptedException e) {
				}
			}
		}
	}

}
