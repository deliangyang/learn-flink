package com.test.demo

import java.util.Properties

import org.apache.kafka.clients.producer._

object Demo {
  def main(args: Array[String]): Unit = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
    props.put("producer.type", "async")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    val msg = new ProducerRecord[String, String]("test", "hello1", "this is val")
    producer.send(msg)
  }
}
