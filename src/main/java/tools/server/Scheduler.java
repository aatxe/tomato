package tools.server;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * A tool for managing scheduled Runnable events.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public class Scheduler {
	private static Scheduler instance;
	private ScheduledThreadPoolExecutor stpe;
	private TimeUnit unit = TimeUnit.MILLISECONDS;
	
	/**
	 * Creates a new <code>Scheduler</code> using milliseconds.
	 * Use <code>getInstance()</code> if usage requires this.
	 */
	protected Scheduler() {
		this(TimeUnit.MILLISECONDS);
	}
	
	/**
	 * Creates a new <code>Scheduler</code>.
	 * @param unit the unit of time to use for scheduling events.
	 */
	public Scheduler(TimeUnit unit) {
		stpe = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Executors.defaultThreadFactory());
		this.unit = unit;
	}
	
	/**
	 * Gets the unit of time used for scheduling events.
	 * @return the unit of time used for scheduling events
	 */
	public TimeUnit getUnits() {
		return unit;
	}
	
	/**
	 * Runs an event one time after a set delay.
	 * @param command the <code>Runnable</code> to be executed
	 * @param delay the delay after which to execute the command
	 * @return a <code>ScheduledFuture</code> representing pending completion of the task
	 */
	public ScheduledFuture<?> runOnce(Runnable command, long delay) {
		return stpe.schedule(command, delay, unit);
	}
	
	/**
	 * Schedules an event to be executed periodically, starting immediately.
	 * @param command the <code>Runnable</code> to be executed
	 * @param period the period between executions of the command
	 * @return a <code>ScheduledFuture</code> representing pending completion of the task
	 */
	public ScheduledFuture<?> scheduleNow(Runnable command, long period) {
		return stpe.scheduleAtFixedRate(command, 0, period, unit);
	}
	
	/**
	 * Schedules an event to be executed periodically, after an initial delay.
	 * @param command the <code>Runnable</code> to be executed
	 * @param initialDelay the delay before the command is first executed
	 * @param period the period between executions of the command
	 * @return a <code>ScheduledFuture</code> representing pending completion of the task
	 */
	public ScheduledFuture<?> schedule(Runnable command, long initialDelay, long period) {
		return stpe.scheduleAtFixedRate(command, initialDelay, period, unit);
	}
	
	/**
	 * Completes all running events and shuts down the <code>ScheduledThreadPoolExecutor</code>.
	 */
	public void shutdown() {
		stpe.shutdown();
	}
	
	/**
	 * Gets the universal instance for the scheduler.
	 * @return the universal Scheduler instance
	 */
	public static Scheduler getInstance() {
		if (instance == null) {
			instance = new Scheduler();
		}
		return instance;
	}
}
