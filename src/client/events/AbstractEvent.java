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
	
	public AbstractEvent(Channel session) {
		this.session = session;
	}
}
