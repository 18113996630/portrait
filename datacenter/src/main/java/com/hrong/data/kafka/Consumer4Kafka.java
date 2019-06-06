package com.hrong.data.kafka;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hrong.common.utils.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author hrong
 * @ClassName KafkaConsumer
 * @Description
 * @Date 2019/6/5 21:48
 **/
@Slf4j
public class Consumer4Kafka {
	private final KafkaConsumer<String, String> consumer;
	private ThreadPoolExecutor threadPool = null;
	private final String topic;
	private long delay;


	public Consumer4Kafka(Properties props, String topic) {
		consumer = new KafkaConsumer<>(props);
		this.topic = topic;
		ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build();
		threadPool = new ThreadPoolExecutor(5,
				200,
				0L,
				TimeUnit.MILLISECONDS,
				new LinkedBlockingDeque<>(1024),
				threadFactory,
				new ThreadPoolExecutor.AbortPolicy());
	}

	public void shutdown() {
		if (consumer != null) {
			consumer.close();
		}
		if (threadPool != null){
			threadPool.shutdown();
		}
	}

	public void run() {
		consumer.subscribe(Collections.singletonList(this.topic));
		threadPool.execute(() -> {
			while (true) {
				try {
					ConsumerRecords<String, String> records = consumer.poll(1000);
					for (ConsumerRecord<String, String> record : records) {
						System.out.println("receive:"+record);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

	}

	/**
	 * consumer 配置.
	 *
	 * @param brokers brokers
	 * @param groupId 组名
	 * @return
	 */
	private static Properties createConsumerConfig(String brokers, String groupId) {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
		return props;
	}

	public static void main(String[] args) throws InterruptedException {
		Properties props = PropertiesUtil.getProperties("kafka.properties");
		Consumer4Kafka example = new Consumer4Kafka(props, props.getProperty("topic"));
		example.run();
		Thread.sleep(24 * 60 * 60 * 1000);

		example.shutdown();
	}
}
