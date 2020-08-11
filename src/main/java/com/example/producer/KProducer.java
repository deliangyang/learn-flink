package com.example.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KProducer {

    public static void main(String[] args) {
        // 仪式感是没有放弃自己最好的证明

        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        try {
            for (int i = 220; i < 1000; i++) {
                ProducerRecord<String, String> pr = new ProducerRecord<>(
                        "test", "abc" + i, "Hello world3242423" + i);
                producer.send(pr);
                System.out.println("hello world" + i + "xxx");
            }
            // producer.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
