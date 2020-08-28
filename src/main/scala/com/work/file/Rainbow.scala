package com.work.file

import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala._

case class WordCount(word: String, count: Int)

object Rainbow {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    val input = env.fromElements(
      WordCount("hello", 1),
      WordCount("world", 2)) // Case Class Data Set

    input.groupBy(_.word)

    val input2 = env.fromElements(("hello", 1), ("world", 2), ("world", 2), ("world", 2)) // Tuple2 Data Set

    val counter = input2.map {
      value => (value._1, value._2)
    }.groupBy(0)
        .sum(1)
    counter.print()
    //env.execute("helloworld")

  }
}
