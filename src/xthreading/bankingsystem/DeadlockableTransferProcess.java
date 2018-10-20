package xthreading.bankingsystem;

import java.util.Random;
import java.util.concurrent.locks.Lock;

public class DeadlockableTransferProcess implements Runnable {
	private Account from;
	private Account to;
	private Lock fromLock;
	private Lock toLock;

	public DeadlockableTransferProcess(Account from, Account to, Lock fromLock, Lock toLock) {
		this.from = from;
		this.to = to;
		this.fromLock = fromLock;
		this.toLock = toLock;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 10000; i++) {
			fromLock.lock();
			toLock.lock();
			try {
				Account.transfer(from, to, new Random().nextInt(100));
			} finally {
				fromLock.unlock();
				toLock.unlock();
			}
		}

	}

}
