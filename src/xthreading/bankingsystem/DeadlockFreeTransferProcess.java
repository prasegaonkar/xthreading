package xthreading.bankingsystem;

import java.util.Random;
import java.util.concurrent.locks.Lock;

public class DeadlockFreeTransferProcess implements Runnable {
	private Account from;
	private Account to;
	private Lock fromLock;
	private Lock toLock;

	public DeadlockFreeTransferProcess(Account from, Account to, Lock fromLock, Lock toLock) {
		this.from = from;
		this.to = to;
		this.fromLock = fromLock;
		this.toLock = toLock;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 10000; i++) {
			acquireLocks();
			try {
				Account.transfer(from, to, new Random().nextInt(100));
			} finally {
				fromLock.unlock();
				toLock.unlock();
			}
		}

	}

	private void acquireLocks() {
		while (true) {
			boolean gotFromLock = false;
			boolean gotToLock = false;
			try {
				gotFromLock = fromLock.tryLock();
				gotToLock = toLock.tryLock();
			} finally {
				if (gotFromLock && gotToLock) {
					return;
				}

				if (gotFromLock) {
					fromLock.unlock();
				}

				if (gotToLock) {
					toLock.unlock();
				}
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}

		}
	}

}
