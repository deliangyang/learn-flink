package com.kakfa.test

import java.util.Properties

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.connectors.kafka._


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

    env.execute("flink-kafka")
  }
}
