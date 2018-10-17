package xthreading;

import java.util.Scanner;

class Processor extends Thread {
	private volatile boolean may_be_cached_so_use_volatile_isRunning = true;

	public void run() {
		while (may_be_cached_so_use_volatile_isRunning) {
			System.out.println("Running");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

		}
	}

	public void shutdown() {
		may_be_cached_so_use_volatile_isRunning = false;
	}
}

public class VolatileUsed {

	public static void main(String[] args) {
		Processor proc = new Processor();
		proc.start();
		
		System.out.println("Press return to stop...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
		scanner.close();
		proc.shutdown();
	}

}
