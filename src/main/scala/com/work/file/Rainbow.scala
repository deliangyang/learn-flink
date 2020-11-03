package com.work.file

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.streaming.api.scala._


object Rainbow {

  def main(args: Array[String]): Unit = {
    val params = ParameterTool.fromArgs(args)
    val env = ExecutionEnvironment.getExecutionEnvironment
    env.getConfig.setGlobalJobParameters(params)
    val text = env.readTextFile(params.get("input"))
    val mapper = new ObjectMapper

    val split = text.flatMap {
      _.split("\n")
    }

    val counter = split
      .map {
        value => {
          val node = mapper.readValue(value.substring(value.indexOf('{')), classOf[Log])
          ((node.method, node.path, node.user, node.ip), 1)
        }
      }.groupBy(0)
      .sum(1)

    counter.writeAsCsv(params.get("output"))
    env.execute("log_api_visit_stat")
  }

}
