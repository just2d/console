package com.nuoshi.console.jms;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

import com.google.gson.Gson;
import com.taofang.biz.util.BizConfig;
import com.nuoshi.console.domain.estate.EstateChangeMessage;

public class EstateJms {
	public static SendMessage instance;
	// 发送TextMessage
	public static class SendMessage {

		private static final String estateJmsUrl = "tcp://" + BizConfig.getProperty("url.jms");;
		private static Gson gson = new Gson();
		private static TopicConnection connection = null;
		private static MessageProducer producer = null;

		public SendMessage() throws JMSException {
			try {
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(estateJmsUrl);
				connection = connectionFactory.createTopicConnection();
				// connection.setClientID("consoleProducerEstate");
				connection.start();
				TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
				Topic topic = session.createTopic("Estate.Change");
				producer = session.createProducer(topic);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// connection.close();
			}
		}

		public void sendMessage(EstateChangeMessage changeMessage) throws JMSException {
			ActiveMQTextMessage msg = new ActiveMQTextMessage();
			msg.setText(gson.toJson(changeMessage));
			producer.send(msg);
		}
	}

	public static void send(int estateId, EstateChangeMessage.ChangeStatus changeStatus) {
		{
			try {
				if (instance == null) {
					instance = new SendMessage();
				}

				FreshEstateTaskThreadPoolManager.INSTANCE.addTask(estateId, changeStatus);
				// }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}