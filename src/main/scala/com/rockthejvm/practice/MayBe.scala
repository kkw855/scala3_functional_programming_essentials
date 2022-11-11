package com.rockthejvm.practice

abstract class MayBe[A] {
  def filter(f: A => Boolean): MayBe[A]
  def map[B](f: A => B): MayBe[B]
  def flatMap[B](f: A => MayBe[B]): MayBe[B]
}

case class MayBeNot[A]() extends MayBe[A] {
  override def filter(f: A => Boolean): MayBe[A] = this
  override def map[B](f: A => B): MayBe[B] = MayBeNot()
  override def flatMap[B](f: A => MayBe[B]): MayBe[B] = MayBeNot()
}

case class Just[A](value: A) extends MayBe[A] {
  override def filter(f: A => Boolean): MayBe[A] =
    if (f(value)) this
    else MayBeNot()
  override def map[B](f: A => B): MayBe[B] = Just(f(value))
  override def flatMap[B](f: A => MayBe[B]): MayBe[B] = f(value)
}

object MayBeTest {
  def main(args: Array[String]): Unit = {
    val maybeInt: MayBe[Int] = Just(3)
    val maybeInt2: MayBe[Int] = MayBeNot()
    val maybeIncrementedInt = maybeInt.map(_ + 1)
    val maybeIncrementedInt2 = maybeInt2.map(_ + 1)
    println(maybeIncrementedInt)
    println(maybeIncrementedInt2)

    val maybeFiltered = maybeInt.filter(_ % 2 == 0)
    println(maybeFiltered)

    val maybeFlatMapped = maybeInt.flatMap(number => Just(number * 3))
    println(maybeFlatMapped)
  }
}
