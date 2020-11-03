package com.work.file

import org.apache.flink.api.java.aggregation.Aggregations
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.streaming.api.scala._


object Time {

  def main(args: Array[String]): Unit = {
    val params = ParameterTool.fromArgs(args)

    val env = ExecutionEnvironment.getExecutionEnvironment
    env.getConfig.setGlobalJobParameters(params)
    val text = env.readTextFile(params.get("input"))
    val mapper = new ObjectMapper

    val split = text.flatMap {
      _.split("\n")
    }

    val counter3 = split
      .map {
        value => {
          val node = mapper.readValue(value.substring(value.indexOf('{')), classOf[Log])
          ((node.method, node.path), 1)
        }
      }.groupBy(0)
      .sum(1)

    val executeTime = split
      .map {
        value => {
          val node = mapper.readValue(value.substring(value.indexOf('{')), classOf[Log])
          ((node.method, node.path), node.extra.executionTime)
        }
      }.groupBy(0)

    val counter4 = executeTime
      .aggregate(Aggregations.SUM, 1)

    val counter23 = executeTime
      .minBy(1)

    val counter233 = executeTime
      .maxBy(1)

    val result = counter23.rightOuterJoin(counter233)
      .where(0)
      .equalTo(0)
      .apply((first, second) => {
        if (second == null) {
          (first._1, first._2, "null")
        } else {
          (first._1, first._2, second._2)
        }
      })

    val result2 = result.rightOuterJoin(counter3)
      .where(0)
      .equalTo(0)
      .apply((first, second) => {
        if (second == null) {
          (first._1, first._2, first._3, "0")
        } else {
          (first._1, first._2, first._3, second._2)
        }
      })

    val result3 = result2.rightOuterJoin(counter4)
      .where(0)
      .equalTo(0)
      .apply((first, second) => {
        if (second == null) {
          (first._1, first._2, first._3, first._4, "0")
        } else {
          (first._1, first._2, first._3, first._4, second._2)
        }
      })

    result3.writeAsCsv(params.get("output_time"))
    env.execute("log_stat")
  }

}
