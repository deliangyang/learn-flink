package com.learn.basic

import scala.util.Random

object Match {
  def main(args: Array[String]): Unit = {
    val x: Int = Random.nextInt(100)
    val b = x match {
      case 0 => "zero"
      case 1 => "one"
      case 2 => "two"
      case _ => "other"
    }
    println(x, b)
  }
}
