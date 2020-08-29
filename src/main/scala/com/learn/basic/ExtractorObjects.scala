package com.learn.basic

import scala.util.Random


object CustomerID {
  def apply(name: String) = s"${name}--${Random.nextLong()}"

  def unapply(customerID: String): Option[String] = {
    val stringArray: Array[String] = customerID.split("--")
    if (stringArray.tail.nonEmpty) Some(stringArray.head) else None
  }
}

object ExtractorObjects {
  def main(args: Array[String]): Unit = {
    val customer1ID = CustomerID("Sukyoung")
    customer1ID match {
      case CustomerID(name) => println(name)
      case _ => println("Could not extract a CustomerID")
    }
  }
}
