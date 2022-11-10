package com.rockthejvm.part3fp

//noinspection TypeAnnotation,ScalaUnusedSymbol,NotImplementedCode,DuplicatedCode,ConvertExpressionToSAM,ScalaUnnecessaryParentheses
object WhatsAFunction {
  // FP: function are "first-class" citizens
  // JVM

  trait MyFunction[A, B] {
    def apply(arg: A): B
  }

  val doubler = new MyFunction[Int, Int] {
    override def apply(arg: Int): Int = arg * 2
  }

  val meaningOfLife = 42
  val meaningDoubled = doubler(meaningOfLife) // doubler.apply(meaningOfLife)

  // function types
  val doublerStandard = new Function1[Int, Int] {
    override def apply(arg: Int): Int = arg * 2
  }

  val meaningDoubled_v2 = doublerStandard(meaningOfLife)
  val adder = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }
  val anAddition = adder(2, 67)

  // (Int, String, Double, Boolean) => Int ==== Function4[Int, String, Double, Boolean, Int]
  val aThreeArgFunction = new Function4[Int, String, Double, Boolean, Int] {
    override def apply(v1: Int, v2: String, v3: Double, v4: Boolean): Int = ???
  }

  // 1
  val concatenator: (a: String, b: String) => String =
    new Function2[String, String, String] {
      override def apply(a: String, b: String): String = a + b
    }

  // all functions are instance of FunctionX with apply methods

  /*
    Exercise
    1. A function which takes 2 strings and concatenates them
    2. Replace Predicate/Transformer with the appropriate function types if necessary
    3. Define a function which takes an int as argument and return ANOTHER FUNCTION as a result.
   */

  // 2
  // yes: Predicate[T] equivalent with Function1[T, Boolean] === T => Boolean
  // yes: Transformer[A, B] equivalent with Function[A, B] === A => B

  // 3
  val returningFunction: Int => (Int => Int) = new Function1[Int, Int => Int] {
    override def apply(a: Int): Int => Int =
      new Function1[Int, Int] {
        override def apply(b: Int): Int = a + b
      }
  }

  def main(args: Array[String]): Unit = {
    println(concatenator("I love ", "Scala"))
  }
}
