package com.example.consumer;


import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

public class KConsumer {

    public static void main(String[] args) {
        String topic = "test";

        Properties props = new Properties();
        props.put("group.id", "test23");
        props.put("bootstrap.servers", "127.0.0.1:9092");
      //  props.put("auto.offset.reset", "lasted");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
       // props.put("auto.commit.interval.ms", "1000");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList(topic, "new-dev.party.detailGift"));

        for (int i = 0; i < 100; i++) {
            System.out.println("i:" + i);
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1L));
            for (ConsumerRecord<String, String> record: records) {
                System.out.println("data: " + record.key() + "xxx" + record.value());
                //consumer.commitAsync();
            }
        }
    }

}
