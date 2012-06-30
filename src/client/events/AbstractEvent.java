package client.events;

import org.jboss.netty.channel.Channel;

/**
 * An abstract networking event, as a <code>Runnable</code>.
 * @author tomato
 * @version 1.0
 * @since alpha
 */
public abstract class AbstractEvent implements Runnable {
	protected Channel session;
	
	/**
	 * Creates a runnable event wrapping a <code>Channel</code>
	 * @param session the <code>Channel</code> session to be wrapped
	 */
	public AbstractEvent(Channel session) {
		this.session = session;
	}
}
