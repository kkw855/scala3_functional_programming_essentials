package com.rockthejvm.part2oop

object Generics {

  def main(args: Array[String]): Unit = {
    val listOfIntegers = new NonEmpty(1, new NonEmpty(2, new Empty))
    val listOfStrings = new NonEmpty("Scala", new NonEmpty("Java", new Empty))

    val firstNumber: Any = listOfIntegers.head
  }

  abstract class MyList {
    def head: Any

    def tail: Any
  }

  class Empty extends MyList {
    override def head: Any = throw new NoSuchElementException

    override def tail: Any = throw new NoSuchElementException
  }

  class NonEmpty(override val head: Any, override val tail: MyList) extends MyList
}
