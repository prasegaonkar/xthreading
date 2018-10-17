package xthreading;

import java.util.stream.IntStream;

public class IncrementIn2Threads {
	private int count = 0;

	public static void main(String[] args) {
		IncrementIn2Threads o = new IncrementIn2Threads();
		o.doWork_with_problem();
		o.count = 0;
		o.doWork_that_works();
	}

	public void doWork_that_works() {
		Thread t1 = new Thread(() -> IntStream.range(0, 10000).forEach(x -> syncincrement()));
		Thread t2 = new Thread(() -> IntStream.range(0, 10000).forEach(x -> syncincrement()));
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("count is " + count);
	}

	public void increment() {
		count++;
	}

	public synchronized void syncincrement() {
		count++;
	}

	public void doWork_with_problem() {
		Thread t1 = new Thread(() -> IntStream.range(0, 10000).forEach(x -> increment()));
		Thread t2 = new Thread(() -> IntStream.range(0, 10000).forEach(x -> increment()));
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("count is " + count);
	}
}
