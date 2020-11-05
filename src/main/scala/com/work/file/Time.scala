package com.work.file

import org.apache.flink.api.common.functions.ReduceFunction
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

    val executeTime = split
      .map {
        value => {
          try {
            val node = mapper.readValue(value.substring(value.indexOf('{')), classOf[Log])
            Some((node.method, node.path, translate(node.extra.executionTime)), node.extra.executionTime, 1)
          } catch {
            case _: Exception => None
          }
        }
      }
      .filter(x => x.nonEmpty)
      .map(x => x.get)
      .groupBy(0)

    val counter3 = executeTime.minBy(1)
    val counter4 = executeTime.maxBy(1)
    val counter5 = executeTime.sum(2)
    val counter6 = executeTime.aggregate(Aggregations.SUM, 1)

    // min -> max -> count -> agg sum

    val result = counter3.rightOuterJoin(counter4)
      .where(0)
      .equalTo(0)
      .apply((first, second) => {
        if (second == null) {
          (first._1, first._2, "null")
        } else {
          (first._1, first._2, second._2)
        }
      })

    val result2 = result.rightOuterJoin(counter5)
      .where(0)
      .equalTo(0)
      .apply((first, second) => {
        if (second == null) {
          (first._1, first._2, first._3, "0")
        } else {
          (first._1, first._2, first._3, second._3)
        }
      })

    val result3 = result2.rightOuterJoin(counter6)
      .where(0)
      .equalTo(0)
      .apply((first, second) => {
        if (second == null) {
          (first._1, first._2, first._3, first._4, "0")
        } else {
          (first._1, first._2, first._3, first._4, second._2)
        }
      })

    result3.writeAsCsv(params.get("output"))
    env.execute("log_time_stat")
  }

  def translate(time: Long): Int = {
    if (0 < time && time < 100) {
      return 0
    } else if (100 <= time && time < 200) {
      return 1
    } else if (200 <= time && time < 500) {
      return 2
    } else if (500 <= time && time < 1000) {
      return 5
    }
    10
  }

}
