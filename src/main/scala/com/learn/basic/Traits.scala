package com.learn.basic

import scala.collection.mutable.ArrayBuffer

// Traits 共享interface和字段
// Classes和objects可以继承traits，但是traits不能被实例化，因此没有参数

trait Iterator[A] {
  def hasNext: Boolean
  def next(): A
}


class IntIterator(to: Int) extends Iterator[Int] {
  private var current = 0
  override def hasNext: Boolean = current < to

  override def next(): Int = {
    if (hasNext) {
      val t = current
      current += 1
      t
    } else 0

  }
}


// Subtyping
trait Pet {
  val name: String
}

class Cat(val name: String) extends Pet

class Dog(val name: String) extends Pet

object Traits {
  def main(args: Array[String]): Unit = {
    val iterator = new IntIterator(10)
    println(iterator.next())
    println(iterator.next())
    println(iterator.next())
    println(iterator.next())

    // subtyping
    val dog = new Dog("Harry")
    val cat = new Cat("Sally")

    val animals = ArrayBuffer.empty[Pet]
    animals.append(dog)
    animals.append(cat)

    animals.foreach(pet => println(pet.name))
  }
}
