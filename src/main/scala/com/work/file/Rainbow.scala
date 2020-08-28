package com.work.file

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.streaming.api.scala._

import scala.beans.BeanProperty

case class WordCount(word: String, count: Int)


object Rainbow {

  @JsonIgnoreProperties(ignoreUnknown = true)
  case class MyValue(
                      @JsonProperty("user") @BeanProperty user: Long,
                      @JsonProperty("ip") @BeanProperty ip: String,
                      @JsonProperty("path") @BeanProperty path: String,
                      @JsonProperty("method") @BeanProperty method: String
                    )


  def main(args: Array[String]): Unit = {
    val params = ParameterTool.fromArgs(args)

    val env = ExecutionEnvironment.getExecutionEnvironment
    env.getConfig.setGlobalJobParameters(params)
    val text = env.readTextFile(params.get("input"))
    val mapper = new ObjectMapper

    val counter = text.flatMap {
      _.split("\n")
    }
      .map {
        value => {
          val node = mapper.readValue(value.substring(value.indexOf('{')), classOf[MyValue])
          ((node.method, node.path), 1)
        }
      }.groupBy(0)
      .sum(1)
    counter.writeAsCsv(params.get("output"))
    env.execute("helloworld")

  }

}
