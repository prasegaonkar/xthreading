package xthreading;

import java.util.stream.IntStream;

class Runner extends Thread {
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

public class App2 {
	public static void main(String[] args) {

		Runner th = new Runner();
		th.start();

	}
}
