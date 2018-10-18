package xthreading;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerWithBlockingQueue {

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
		Thread producer = new Thread(new Producer(queue));
		Thread consumer = new Thread(new Consumer(queue));
		producer.start();
		consumer.start();
		producer.join();
		consumer.join();
	}

	private static class Producer implements Runnable {
		private BlockingQueue<Integer> queue;

		public Producer(BlockingQueue<Integer> queue) {
			this.queue = queue;
		}

		public void run() {
			Random random = new Random();
			while (true) {
				try {
					queue.put(random.nextInt(100));
				} catch (InterruptedException e) {
				}
			}
		}
	}

	private static class Consumer implements Runnable {
		private BlockingQueue<Integer> queue;

		public Consumer(BlockingQueue<Integer> queue) {
			this.queue = queue;
		}

		public void run() {
			Random random = new Random();
			while (true) {
				try {
					Thread.sleep(10);
					if (random.nextInt(10) == 0) {
						Integer value = queue.take();
						System.out.println("Value: " + value + "; Queue size: " + queue.size());
					}
				} catch (InterruptedException e) {
				}
			}
		}
	}

}
