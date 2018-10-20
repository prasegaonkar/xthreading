package xthreading;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class InterruptedThread {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Starting...");
		Thread t = new Thread(() -> {
			Random r = new Random();
			for (int i = 0; i < 1E8; i++) {
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("Interrupted");
					break;
				}
				Math.sin(r.nextDouble());
			}
		});
		t.start();
		t.interrupt();
		t.join();
		System.out.println("Done");

		System.out.println("Starting...");
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<Integer> call = executor.submit(() -> {
			Random r = new Random();
			for (int i = 0; i < 1E8; i++) {
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("Interrupted");
					break;
				}
				Math.sin(r.nextDouble());
			}
			return 1;
		});

		executor.shutdown();
		call.cancel(false);
		executor.shutdownNow();
		executor.awaitTermination(1, TimeUnit.DAYS);
		System.out.println("Done");
	}
}
