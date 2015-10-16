package taimi.backend.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * Publishes {Object} that can be received by 
 * 	org.springframework.context.event.EventListener
 * 
 * @author vpotry
 *
 */
@Service
public class DataEventPublisher implements ApplicationEventPublisherAware {
   
	private ApplicationEventPublisher publisher;

	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public void publish() {
		DataReceivedEvent ce = new DataReceivedEvent(this);
		publisher.publishEvent(ce);
	}
   
	public void publish(Object data) {
		DataReceivedEvent ce = new DataReceivedEvent(this, data);
		publisher.publishEvent(ce);
	}
}