package com.gemserk.commons.artemis.systems;

import java.util.ArrayList;

import org.junit.Test;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.EntityProcessingSystem;

public class MessageSystemTest {

	class Message {

		private final Entity source;
		private final String id;

		public String getId() {
			return id;
		}

		public Entity getSource() {
			return source;
		}

		public Message(String id, Entity source) {
			this.id = id;
			this.source = source;
		}

	}

	class MessageQueueComponent extends Component {

		private ArrayList<Message> messages;

		public ArrayList<Message> getMessages() {
			return messages;
		}

	}

	class MessageQueue {

		private ArrayList<Message> messages;

		public ArrayList<Message> getMessages() {
			return messages;
		}

	}

	class MessageQueueSystem extends EntityProcessingSystem {

		private MessageQueue messageQueue;

		public void setMessageQueue(MessageQueue messageQueue) {
			this.messageQueue = messageQueue;
		}

		@SuppressWarnings("unchecked")
		public MessageQueueSystem() {
			super(MessageQueueComponent.class);
		}

		@Override
		protected void process(Entity e) {
			MessageQueueComponent messageQueueComponent = e.getComponent(MessageQueueComponent.class);
			messageQueue.getMessages().addAll(messageQueueComponent.getMessages());
			messageQueueComponent.getMessages().clear();
		}

	}

	class MessageHandler {

		boolean canHandle(Message m) {
			return true;
		}

		void handle(Message m) {

		}

	}

	class MessageHandlerComponent extends Component {

		ArrayList<MessageHandler> messageHandlers;

	}

	class MessageListenerSystem extends EntityProcessingSystem {

		private MessageQueue messageQueue;

		public void setMessageQueue(MessageQueue messageQueue) {
			this.messageQueue = messageQueue;
		}

		@SuppressWarnings("unchecked")
		public MessageListenerSystem() {
			super(MessageHandlerComponent.class);
		}

		@Override
		protected void process(Entity e) {
			MessageHandlerComponent messageHandlerComponent = e.getComponent(MessageHandlerComponent.class);
			ArrayList<MessageHandler> messageHandlers = messageHandlerComponent.messageHandlers;
			for (int i = 0; i < messageHandlers.size(); i++) {
				MessageHandler messageHandler = messageHandlers.get(i);
				// if (messageHandler.canHandle(m))
				for (int j = 0; j < messageQueue.messages.size(); j++) {
					Message m = messageQueue.messages.get(j);
					if (messageHandler.canHandle(m))
						messageHandler.handle(m);
				}
			}
			messageQueue.messages.clear();
		}

	}
	
	@Test
	public void test() {
		
	}

}
