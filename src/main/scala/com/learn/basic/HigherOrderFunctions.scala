package com.learn.basic

object HigherOrderFunctions {
  def main(args: Array[String]): Unit = {
    val salaries = Seq(2000, 4000, 5000)
    val double = salaries.map(x => {
      x * 2
    })

    val double2 = salaries.map(_ * 2)
    println(double)
    println(double2)
  }
}
