package com.rockthejvm.practice

import jdk.jfr.TransitionFrom

import java.lang.annotation.Annotation
import scala.annotation.{tailrec, targetName}

//noinspection NoTargetNameAnnotationForOperatorLikeDefinition
// singly linked list
// [1,2,3] = [1] -> [2] -> [3] -> |
abstract class LList[A] {
  def head: A

  def tail: LList[A]

  def isEmpty: Boolean

  def add(element: A): LList[A] = Cons(element, this)

  infix def ++(anotherList: LList[A]): LList[A]

  def map[B](transformer: Transformer[A, B]): LList[B]

  def filter(predicate: Predicate[A]): LList[A]

  def flatMap[B](transformer: Transformer[A, LList[B]]): LList[B]
}

//noinspection NoTargetNameAnnotationForOperatorLikeDefinition
case class Empty[A]() extends LList[A] {
  override def head: A = throw new NoSuchElementException

  override def tail: LList[A] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def toString: String = "[]"

  override infix def ++(anotherList: LList[A]): LList[A] = anotherList

  override def map[B](transformer: Transformer[A, B]): LList[B] = Empty()

  override def filter(predicate: Predicate[A]): LList[A] = this

  override def flatMap[B](transformer: Transformer[A, LList[B]]): LList[B] = Empty()
}

//noinspection NoTargetNameAnnotationForOperatorLikeDefinition
case class Cons[A](override val head: A, override val tail: LList[A])
  extends LList[A] {
  override def isEmpty: Boolean = false

  override def toString: String = {
    @tailrec
    def concatenateElements(remainder: LList[A], acc: String): String =
      if (remainder.isEmpty) acc
      else concatenateElements(remainder.tail, s"$acc, ${remainder.head}")

    s"[${concatenateElements(this.tail, s"$head")}]"
  }

  /*
    example
    [1,2,3] ++ [4,5,6] =
    new Cons(1, [2,3] ++ [4,5,6]) =
    new Cons(1, new Cons(2, [3] ++ [4,5,6])) =
    new Cons(1, new Cons(2, new Cons(3, [] ++ [4,5,6]))) =
    new Cons(1, new Cons(2, new Cons(3, [4,5,6]))) =
    [1,2,3,4,5,6]
  */
  override infix def ++(anotherList: LList[A]): LList[A] =
    Cons(head, tail ++ anotherList)

  /*
    example
    [1,2,3].map(n * 2) =
    new Cons(2, [2,3].map(n * 2)) =
    new Cons(2, new Cons(4, [3].map(n * 2))) =
    new Cons(2, new Cons(4, new Cons(6, [].map(n * 2)))) =
    new Cons(2, new Cons(4, new Cons(6, [])))) =
    [2,4,6]
   */
  override def map[B](transformer: Transformer[A, B]): LList[B] =
    Cons(transformer.transform(head), tail.map(transformer))

  /*
    example
    [1,2,3].filter(n % 2 == 0) =
    [2,3].filter(n % 2 == 0) =
    new Cons(2, [3].filter(n % 2 == 0)) =
    new Cons(2, [].filter(n % 2 == 0)) =
    new Cons(2, []) =
    [2]
   */
  override def filter(predicate: Predicate[A]): LList[A] =
    if (predicate.test(head)) Cons(head, tail.filter(predicate))
    else tail.filter(predicate)

  /*
    example
    [1,2,3].flatMap(n => [n, n + 1]) =
    [1,2] ++ [2,3].flatMap(n => [n, n + 1]) =
    [1,2] ++ [2,3] ++ [3].flatMap(n => [n, n + 1]) =
    [1,2] ++ [2,3] ++ [3,4] ++ [].flatMap(n => [n, n + 1]) =
    [1,2] ++ [2,3] ++ [3,4] ++ [] =
    [1,2,2,3,3,4]
  */
  override def flatMap[B](transformer: Transformer[A, LList[B]]): LList[B] =
    transformer.transform(head) ++ tail.flatMap(transformer)
}

/*
  Exercise: LList extension
     1. Generic trait Predicate[T] with a little method test(T) => Boolean
     2. Generic trait Transformer[A, B] with a method transform(A) => B
     3. LList:
        - map(transformer: Transformer[A, B]) => LList[B]
        - filter(predicate: Predicate[A]) => LList[A]
        - flatMap(transformer from A to LList[B]) => LList[B]

        class EvenPredicate extends Predicate[Int]
        class StringToIntTransformer extends Transformer[String, Int]

        [1,2,3].map(n * 2) = [2,4,6]
        [1,2,3,4].filter(n % 2 == 0) = [2,4]
        [1,2,3].flatMap(n => [n, n+1]) => [1,2, 2,3, 3,4]
 */

trait Predicate[T] {
  def test(a: T): Boolean
}

//noinspection DuplicatedCode,ScalaUnusedSymbol
class EvenPredicate extends Predicate[Int] {
  override def test(element: Int): Boolean =
    element % 2 == 0
}

trait Transformer[A, B] {
  def transform(a: A): B
}

//noinspection ScalaUnusedSymbol
class Doubler extends Transformer[Int, Int] {
  override def transform(value: Int): Int = value * 2
}

class DoublerList extends Transformer[Int, LList[Int]] {
  override def transform(value: Int): LList[Int] =
    Cons(value, Cons(value + 1, Empty()))
}

//noinspection ScalaFileName, DuplicatedCode
object LListTest {
  def main(args: Array[String]): Unit = {
    val empty = Empty[Int]()

    println(empty.isEmpty)
    println(empty)

    /*
      Empty
      Cons(3, Empty)
      Cons(2, Cons(3, Empty))
      Cons(1, Cons(2, Cons(3, Empty)))
     */
    val first3Numbers = Cons(1, Cons(2, Cons(3, empty)))
    println(first3Numbers)

    val first3Numbers_v2 = empty.add(1).add(2).add(3)
    println(first3Numbers_v2)
    println(first3Numbers_v2.isEmpty)

    val someStrings = Cons("dog", Cons("cat", Empty()))
    println(someStrings)

    val evenPredicate = new Predicate[Int] {
      override def test(element: Int): Boolean =
        element % 2 == 0
    }

    val doubler = new Transformer[Int, Int] {
      override def transform(value: Int): Int = value * 2
    }

    // map testing
    val numbersDoubler = first3Numbers.map(doubler)
    println(numbersDoubler)
    val numbersNested = first3Numbers.map(new DoublerList)
    println(numbersNested)

    // filter testing
    val onlyEvenNumbers = first3Numbers.filter(evenPredicate) // [2]
    println(onlyEvenNumbers)

    // test concatenation
    val listInBothWays = first3Numbers ++ first3Numbers_v2
    println(listInBothWays)

    // test flatMap
    val flattenedList = first3Numbers.flatMap(new DoublerList)
    println(flattenedList)
  }
}
