package com.kakfa.test

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.connectors.kafka._
import org.apache.flink.streaming.connectors.kafka.internals.KeyedSerializationSchemaWrapper


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

    val counter = stream.flatMap { _.toLowerCase.split("\\W+") filter { _.nonEmpty } }
      .map { (_, 1) }
      .keyBy(0)
      .timeWindow(Time.seconds(5))
      .sum(1)

    counter.print()

    val producer = new FlinkKafkaProducer(
      "test",
      new SimpleStringSchema(),
      properties
    )

    counter.addSink(producer)

    env.execute("flink-kafka")
  }


}
