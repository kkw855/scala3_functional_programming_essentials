package com.rockthejvm.part2oop

import scala.annotation.tailrec

//noinspection ScalaUnusedSymbol,TypeAnnotation
object Exceptions {

  val aString: String = null
  // aString.length crashes with a NPE

  // 1 - throw exceptions
  // val aWeirdValue: Int = throw new NullPointerException // returning Nothing

  // type Throwable
  //    Error, e.g. SOError, OOMError
  //    Exception, e.g. NPException, NSEException, ....
  val potentialFail = try {
    // code that might fail
    getInt(true) // an Int
  } catch {
    // most specific exceptions first
    case e: NullPointerException => 35
    case e: RuntimeException => 54 // an Int
    // ...
  } finally {
    // executed no matter what
    // closing resources
    // Unit here
  }
  val myException = new MyException

  def getInt(withExceptions: Boolean): Int =
    if (withExceptions) throw new RuntimeException("No int for you!")
    else 42

  def soCrash(): Unit = {
    def infinite(): Int = 1 + infinite()

    infinite()
  }

  /*
    Exercises:

    1. Crash with SOError
    2. Crash with OOMError
    3. Find an element matching a predicate in LList
  */

  def main(args: Array[String]): Unit = {
    println(potentialFail)
    // val throwingMyException = throw myException

    // soCrash()
    oomCrash()
  }

  def oomCrash(): Unit = {
    @tailrec
    def bigString(n: Int, acc: String): String =
      if (n == 0) acc
      else bigString(n - 1, acc + acc)

    bigString(99999999, "Scala")
  }

  // custom exceptions
  class MyException extends RuntimeException {
    // fields and methods
    override def getMessage: String = "MY EXCEPTION"
  }
}
