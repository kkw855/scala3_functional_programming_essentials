package com.rockthejvm.practice

abstract class MayBe[A] {
  def filter(f: A => Boolean): MayBe[A]
  def map[B](f: A => B): MayBe[B]
  def flatMap[B](f: A => MayBe[B]): MayBe[B]
}

case class None[A]() extends MayBe[A] {
  override def filter(f: A => Boolean): MayBe[A] = this
  override def map[B](f: A => B): MayBe[B] = None()
  override def flatMap[B](f: A => MayBe[B]): MayBe[B] = None()
}

case class Some[A](value: A) extends MayBe[A] {
  override def filter(f: A => Boolean): MayBe[A] =
    if (f(value)) this
    else None()
  override def map[B](f: A => B): MayBe[B] = Some(f(value))
  override def flatMap[B](f: A => MayBe[B]): MayBe[B] = f(value)
}