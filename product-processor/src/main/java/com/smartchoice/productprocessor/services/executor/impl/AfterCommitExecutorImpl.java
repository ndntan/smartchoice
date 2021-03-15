package com.smartchoice.productprocessor.services.executor.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.smartchoice.productprocessor.services.executor.AfterCommitExecutor;

@Service("afterCommitExecutor")
public class AfterCommitExecutorImpl extends TransactionSynchronizationAdapter implements AfterCommitExecutor {

	private static final Logger log = LogManager.getLogger(AfterCommitExecutorImpl.class);

	private final ThreadLocal<List<Runnable>> runnables = new ThreadLocal<List<Runnable>>();

	@Override
	public void execute(Runnable runnable) {
		log.debug("Submitting new runnable {} to run after commit", runnable);
		if (!TransactionSynchronizationManager.isSynchronizationActive()) {
			log.debug("Transaction synchronization is NOT ACTIVE. Executing right now runnable {}", runnable);
			runnable.run();
			return;
		}
		List<Runnable> threadRunnables = runnables.get();
		if (threadRunnables == null) {
			threadRunnables = new ArrayList<>();
			runnables.set(threadRunnables);
			TransactionSynchronizationManager.registerSynchronization(this);
		}
		threadRunnables.add(runnable);
	}

	@Override
	public void afterCommit() {
		List<Runnable> threadRunnables = runnables.get();
		log.debug("Transaction successfully committed, executing {} runnables", threadRunnables.size());
		for (int i = 0; i < threadRunnables.size(); i++) {
			Runnable runnable = threadRunnables.get(i);
			log.debug("Executing runnable {}", runnable);
			try {
				runnable.run();
			} catch (RuntimeException e) {
				log.error("Failed to execute runnable " + runnable, e);
			}
		}
	}

	@Override
	public void afterCompletion(int status) {
		log.debug("Transaction completed with status {}", status == STATUS_COMMITTED ? "COMMITTED" : "ROLLED_BACK");
		runnables.remove();
	}

}
