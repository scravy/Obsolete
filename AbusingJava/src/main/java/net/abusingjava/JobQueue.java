package net.abusingjava;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class JobQueue {

	final BlockingDeque<Runnable> $queue;
	final Thread[] $threadPool;
	boolean $closed = false;

	class Stop implements Runnable {
		@Override
		public void run() {
		}
	}

	class Worker implements Runnable {
		@Override
		public void run() {
			try {
				while (!Thread.interrupted()) {
					Runnable $job = $queue.take();
					if ($job instanceof Stop) {
						break;
					}
					try {
						$job.run();
					} catch (Exception $exc) {

					}
				}
			} catch (InterruptedException $exc) {

			}
		}
	}

	public JobQueue() {
		this(Runtime.getRuntime().availableProcessors(), Integer.MAX_VALUE);
	}

	public JobQueue(final int $poolSize) {
		this($poolSize, Integer.MAX_VALUE);
	}

	public JobQueue(final int $poolSize, final int $queueSize) {
		$queue = new LinkedBlockingDeque<Runnable>($queueSize);
		$threadPool = new Thread[$poolSize];

		for (int $i = 0; $i < $poolSize; $i++) {
			$threadPool[$i] = new Thread(new Worker());
		}
	}

	public void start() {
		for (int $i = 0; $i < $threadPool.length; $i++) {
			$threadPool[$i].start();
		}
	}

	public boolean submit(final Runnable $job) {
		if ($closed) {
			throw new IllegalStateException();
		}
		return $queue.offerLast($job);
	}

	public boolean submitAnyways(final Runnable $job) {
		if ($closed) {
			throw new IllegalStateException();
		}
		try {
			$queue.putFirst($job);
			return true;
		} catch (InterruptedException $exc) {
			return false;
		}
	}

	public boolean submitImmediately(final Runnable $job) {
		if ($closed) {
			throw new IllegalStateException();
		}
		return $queue.offerFirst($job);
	}

	public void shutdownImmediately() {
		$closed = true;
		$queue.clear();
		for (int $i = 0; $i < $threadPool.length; $i++) {
			$threadPool[$i].interrupt();
		}
	}

	public void shutdown() {
		$closed = true;
		for (int $i = 0; $i < $threadPool.length; $i++) {
			$queue.add(new Stop());
		}
	}

	public int pending() {
		return $queue.size();
	}
}