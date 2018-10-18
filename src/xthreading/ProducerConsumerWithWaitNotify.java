package xthreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProducerConsumerWithWaitNotify {
	public static void main(String[] args) throws InterruptedException {

		Object mutex = new Object();

		List<Integer> list = new ArrayList<>();

		Thread producer = new Thread(new Producer(mutex, list));

		Thread consumer = new Thread(new Consumer(mutex, list));

		producer.start();
		consumer.start();

		producer.join();
		consumer.join();

	}

	private static class Producer implements Runnable {
		private Object mutex;
		private List<Integer> list;
		private static final int MAX_SIZE = 10;

		public Producer(Object mutex, List<Integer> list) {
			this.mutex = mutex;
			this.list = list;
		}

		public void run() {
			Random random = new Random();
			while (true) {
				synchronized (mutex) {
					while (list.size() == MAX_SIZE) {
						try {
							mutex.wait();
						} catch (InterruptedException e) {
						}
					}
					int value = random.nextInt(100);
					System.out.println("Produced value: " + value);
					list.add(value);
					mutex.notify();
				}
			}
		}
	}

	private static class Consumer implements Runnable {
		private Object mutex;
		private List<Integer> list;

		public Consumer(Object mutex, List<Integer> list) {
			this.mutex = mutex;
			this.list = list;
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e1) {
				}
				synchronized (mutex) {
					while (list.size() == 0) {
						try {
							mutex.wait();
						} catch (InterruptedException e) {
						}
					}
					System.out.print("list size is: " + list.size());
					Integer value = list.remove(0);
					System.out.println("; Consumed value: " + value);
					mutex.notify();
				}
			}
		}
	}

}
