package com.kakfa.test

import java.lang
import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka._
import org.apache.kafka.clients.producer.ProducerRecord


object KafkaTest {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val properties = new Properties()
    properties.setProperty("bootstrap.servers", "localhost:9092")
    properties.setProperty("group.id", "testdb")
    val stream = env.addSource(
      new FlinkKafkaConsumer[String](
        "new-dev.party.detailGift",
        new SimpleStringSchema(),
        properties
      )
    )
    stream.print()

    val counter: DataStream[(String, Int)] = stream.flatMap { _.toLowerCase.split("\\W+") filter { _.nonEmpty } }
      .map { (_, 1) }
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)

    counter.print()

    val properties2 = new Properties()
    properties2.setProperty("bootstrap.servers", "localhost:9092")

    counter.addSink(new FlinkKafkaProducer[(String, Int)](
      "test",
      new Tt("test"),
      properties2,
      FlinkKafkaProducer.Semantic.EXACTLY_ONCE
    ))

//    val producer = new FlinkKafkaProducer(
//      "test",
//      new SimpleStringSchema(),
//      properties
//    )
//    val conf = new FlinkJedisPoolConfig.Builder().setHost("10.0.0.11").setPort(7000).build()
//
//    val redisSink = new RedisSink[String](conf, new RedisMapper[String] {
//      override def getCommandDescription: RedisCommandDescription = {
//        new RedisCommandDescription(RedisCommand.HSET, "sensor")
//      }
//
//      override def getKeyFromData(t: String): String = {
//        t.split(",")(0)
//      }
//
//      override def getValueFromData(t: String): String = {
//        t.split(",")(1)
//      }
//    })
//
//    val a = new RichSinkFunction[String] {}
//
//    counter.addSink(a)

    env.execute("flink-kafka")
  }

}


class Tt(topic: String) extends KafkaSerializationSchema[(String, Int)] {
  override def serialize(t: (String, Int), aLong: lang.Long): ProducerRecord[Array[Byte], Array[Byte]] = {
    new ProducerRecord[Array[Byte], Array[Byte]](topic, t._1.getBytes(), t._2.toString.getBytes())
  }
}
