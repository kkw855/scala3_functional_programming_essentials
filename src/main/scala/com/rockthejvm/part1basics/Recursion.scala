package com.rockthejvm.part1basics

import scala.annotation.tailrec

//noinspection ScalaUnusedSymbol
object Recursion {

  // "repetition" = recursion
  def sumUntil(n: Int): Int =
    if (n <= 0) 0
    else n + sumUntil(n - 1) // "stack" recursion

  def sumNumbersBetween(a: Int, b: Int): Int =
    if (a > b) 0
    else a + sumNumbersBetween(a + 1, b)

  def sumNumbersBetween_v2(a: Int, b: Int): Int = {
    @tailrec
    def sumTailrec(x: Int, accumulator: Int): Int =
      if (x > b) accumulator
      else sumTailrec(x + 1, accumulator + x)

    sumTailrec(a, 0)
  }

  // 3 - yes, rephrasing
  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeUntil(t: Int): Boolean =
      if (t <= 1) true
      else if (n % t == 0) false
      else isPrimeUntil(t - 1)

    isPrimeUntil(n / 2)
  }

  def main(args: Array[String]): Unit = {
    // println(sumUntil(20000))
    println(sumUntil_v2(20000))
    println(concatenate("Scala", 5))
    println(fibonacci(5))
  }

  def sumUntil_v2(n: Int): Int = {
    /*
      sut(10, 0) =
      sut(9, 10) =
      sut(8, 9 + 10) =
      sut(7, 8 + 9 + 10) =
      ...
      sut(0, 1 + 2 + 3 + .. + 9 + 10)
      = 1 + 2 + 3 + .. + 10
     */
    @tailrec
    def sumUntilTailrec(x: Int, accumulator: Int): Int =
      if (x <= 0) accumulator
      else
        sumUntilTailrec(
          x - 1,
          accumulator + x
        ) // TAIL recursion call occurs LAST in its code path
    // no further stack frames necessary = no more risk of stack overflow

    sumUntilTailrec(n, 0)
  }

  /** Exercise
   * 1. Concatenate a string n times
   * 2. Fibonacci function, tail recursive
   * 3. Is isPrime function tail recursive or not?
   */

  // 1
  def concatenate(string: String, n: Int): String = {
    @tailrec
    def concatTailrec(remainingTimes: Int, accumulator: String): String =
      if (remainingTimes <= 0) accumulator
      else concatTailrec(remainingTimes - 1, string + accumulator)

    concatTailrec(n, "")
  }

  // 2
  def fibonacci(n: Int): Int = {
    @tailrec
    def fiboTailrec(i: Int, prev: Int, cur: Int): Int =
      if (i <= 0) 0
      else if (i <= 2) cur
      else fiboTailrec(i - 1, cur, prev + cur)

    fiboTailrec(n, 1, 1)
  }
}
