package xthreading;

import java.util.stream.IntStream;

public class App1 {
	public static void main(String[] args) {
		Thread th = new Thread(new Runnable() {

			@Override
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

		});
		
		th.start();

	}
}
