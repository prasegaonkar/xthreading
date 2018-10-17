package xthreading;

import java.util.stream.IntStream;

class Runner2 implements Runnable {
	public void run() {
		IntStream.rangeClosed(0, 10).forEach(i -> {
			System.out.println("Running " + i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
	}
}

public class App3 {
	public static void main(String[] args) {
		Thread th = new Thread(new Runner2());
		th.start();

	}
}
