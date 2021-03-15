package com.smartchoice.productprocessor.services.executor;

import java.util.concurrent.Executor;

/**
 * Inject and call execute() to execute an action after commit.
 *
 * Courtesy of
 * http://azagorneanu.blogspot.be/2013/06/transaction-synchronization-callbacks.html
 *
 */
public interface AfterCommitExecutor extends Executor {
}
