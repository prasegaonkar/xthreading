package xthreading;

import java.util.concurrent.Semaphore;

public class ATMQueue {
	// max 4 people 4 ATM machines
	static Semaphore semaphore = new Semaphore(4);

	public static void main(String[] args) {

		System.out.println("Total available Semaphore permits : " + semaphore.availablePermits());

		MyATMThread t1 = new MyATMThread("A");
		t1.start();

		MyATMThread t2 = new MyATMThread("B");
		t2.start();

		MyATMThread t3 = new MyATMThread("C");
		t3.start();

		MyATMThread t4 = new MyATMThread("D");
		t4.start();

		MyATMThread t5 = new MyATMThread("E");
		t5.start();

		MyATMThread t6 = new MyATMThread("F");
		t6.start();

	}

	private static class MyATMThread extends Thread {

		String name = "";

		MyATMThread(String name) {
			this.name = name;
		}

		public void run() {

			try {

				System.out.println(name + " : waiting in queue...");
				System.out.println(name + " : available machines: " + semaphore.availablePermits());

				semaphore.acquire();
				System.out.println(name + " : got the machine!");

				try {

					for (int i = 1; i <= 5; i++) {

						System.out.println(name + " : is performing operation " + i + ", available machines : "
								+ semaphore.availablePermits());

						// sleep 1 second
						Thread.sleep(1000);

					}

				} finally {

					// calling release() after a successful acquire()
					System.out.println(name + " : releasing machine used...");
					semaphore.release();
					System.out.println(name + " : available machines now: " + semaphore.availablePermits());

				}

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

		}
	}
}