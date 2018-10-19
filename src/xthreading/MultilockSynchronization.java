package xthreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultilockSynchronization {
	public static void main(String[] args) {
		new Worker_with_problem().main();
		new Worker_that_works().main();
	}

	private static class Worker_with_problem {
		private Random random = new Random();
		private List<Integer> list1 = new ArrayList<>();
		private List<Integer> list2 = new ArrayList<>();

		private void process() {
			for (int i = 0; i < 1000; i++) {
				stage1();
				stage2();
			}
		}

		private synchronized void stage1() {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list1.add(random.nextInt(100));
		}

		private synchronized void stage2() {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			list2.add(random.nextInt(100));
		}

		public void main() {
			System.out.println("Starting...");
			long start = System.currentTimeMillis();
			Thread t1 = new Thread(() -> process());
			Thread t2 = new Thread(() -> process());
			t1.start();
			t2.start();
			try {
				t1.join();
				t2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();
			System.out.println("List1: " + list1.size() + ", List2: " + list2.size());
			System.out.println("Time taken: " + (end - start));
		}
	}

	private static class Worker_that_works {
		private Random random = new Random();
		private Object lock1 = new Object();
		private Object lock2 = new Object();

		private List<Integer> list1 = new ArrayList<>();
		private List<Integer> list2 = new ArrayList<>();

		private void process() {
			for (int i = 0; i < 1000; i++) {
				stage1();
				stage2();
			}
		}

		private void stage1() {
			synchronized (lock1) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				list1.add(random.nextInt(100));
			}
		}

		private void stage2() {
			synchronized (lock2) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				list2.add(random.nextInt(100));
			}
		}

		public void main() {
			System.out.println("Starting...");
			long start = System.currentTimeMillis();
			Thread t1 = new Thread(() -> process());
			Thread t2 = new Thread(() -> process());
			t1.start();
			t2.start();
			try {
				t1.join();
				t2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long end = System.currentTimeMillis();
			System.out.println("List1: " + list1.size() + ", List2: " + list2.size());
			System.out.println("Time taken: " + (end - start));
		}
	}
}
