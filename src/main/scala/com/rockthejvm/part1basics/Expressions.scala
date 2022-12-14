package com.rockthejvm.part1basics

//noinspection TypeAnnotation,ScalaUnusedExpression,ScalaUnusedSymbol
object Expressions {

  // expressions are structures that can be evaluated to a value
  val meaningOfLife = 40 + 2

  // mathematical expressions: +, -, *, /, %, bitwise |, &, <<, >>, >>>
  val mathExpression = 2 + 3 * 4

  // comparison expressions: <, <=, >, >=, ==, !=
  val equalityTest = 1 == 2

  // boolean expression: !, ||, &&
  val nonEqualityTest = !equalityTest

  // instructions vs expressions
  // expressions are evaluated, instructions are executed
  // we think in terms of expressions

  // ifs are expressions
  val aCondition = true
  val anIfExpression = if (aCondition) 45 else 99

  // code blocks
  val aCodeBlock = {
    // local values
    val localValue = 78
    // expressions...

    // last expression = value of the block
    localValue + 54
  }

  // everything is an expression

  /** Exercise:
   * Without running the code, what do you think these values will print out?
   */
  // Exercise 1
  val someValue = {
    2 < 3
  }

  // Exercise 2
  val someOtherValue = {
    if (someValue) 239 else 986
    42
  }

  // Exercise 3
  val yetAnotherValue: Unit = println("Scala")
  val theUnit: Unit = ()

  def main(args: Array[String]): Unit = {
    println(someValue) // true
    println(someOtherValue) // 42
    println(yetAnotherValue) // ()
  }
}
