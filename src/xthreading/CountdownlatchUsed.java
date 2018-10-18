package xthreading;

import java.util.concurrent.CountDownLatch;

public class CountdownlatchUsed {

	private static class Processor implements Runnable {
		private CountDownLatch latch;

		public Processor(CountDownLatch latch) {
			this.latch = latch;
		}

		public void run() {
			System.out.println("Started");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			latch.countDown();

		}

	}

	public static void main(String[] args) {
		CountDownLatch latch = new CountDownLatch(3);
		Thread t1 = new Thread(new Processor(latch));
		Thread t2 = new Thread(new Processor(latch));
		Thread t3 = new Thread(new Processor(latch));
		t1.start();
		t2.start();
		t3.start();
		try {
			latch.await();
		} catch (InterruptedException e) {
		}
		System.out.println("All completed cleanly.");
	}

}
