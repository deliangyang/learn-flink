package com.work.file

object Test {
  def main(args: Array[String]): Unit = {
    println(func())
  }

  def func(): Int = {
    try {
      throw new Exception()
    } catch {
      case _: Throwable => 2
    } finally {
      23
    }
  }
}
