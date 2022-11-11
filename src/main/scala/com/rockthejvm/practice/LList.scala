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
  def map[B](transformer: A => B): LList[B]
  def filter(predicate: A => Boolean): LList[A]
  def withFilter(predicate: A => Boolean): LList[A] = filter(predicate)
  def flatMap[B](transformer: A => LList[B]): LList[B]

  // host and curries exercises
  def foreach(f: A => Unit): Unit
  def sort(compare: (A, A) => Int): LList[A]
  def zipWith[B, T](list: LList[T], zip: (A, T) => B): LList[B]
  def foldLeft[B](start: B)(operator: (B, A) => B): B
}

//noinspection NoTargetNameAnnotationForOperatorLikeDefinition
case class Empty[A]() extends LList[A] {
  override def head: A = throw new NoSuchElementException

  override def tail: LList[A] = throw new NoSuchElementException

  override def isEmpty: Boolean = true

  override def toString: String = "[]"

  override infix def ++(anotherList: LList[A]): LList[A] = anotherList

  override def map[B](transformer: A => B): LList[B] = Empty()

  override def filter(predicate: A => Boolean): LList[A] = this

  override def flatMap[B](transformer: A => LList[B]): LList[B] =
    Empty()

  // HOFs exercises
  override def foreach(f: A => Unit): Unit = ()
  override def sort(compare: (A, A) => Int): LList[A] = this
  override def zipWith[B, T](list: LList[T], zip: (A, T) => B): LList[B] = Empty()
  override def foldLeft[B](start: B)(operator: (B, A) => B): B = start
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
  override def map[B](transformer: A => B): LList[B] =
    Cons(transformer(head), tail.map(transformer))

  /*
    example
    [1,2,3].filter(n % 2 == 0) =
    [2,3].filter(n % 2 == 0) =
    new Cons(2, [3].filter(n % 2 == 0)) =
    new Cons(2, [].filter(n % 2 == 0)) =
    new Cons(2, []) =
    [2]
   */
  override def filter(predicate: A => Boolean): LList[A] =
    if (predicate(head)) Cons(head, tail.filter(predicate))
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
  override def flatMap[B](transformer: A => LList[B]): LList[B] =
    transformer(head) ++ tail.flatMap(transformer)

  // HOFs exercises
  override def foreach(f: A => Unit): Unit =
    f(head); tail.foreach(f)

  override def sort(compare: (A, A) => Int): LList[A] = {
    /*
      compare = x - y
      insert(3, [1,2,4]) =
      Cons(1, insert(3, [2,4])) =
      Cons(1, Cons(2, insert(3, [4]))) =
      Cons(1, Cons(2, Cons(3, [4]))) = [1,2,3,4]
     */
    // insertion sort, O(n^2), stack recursive
    def insert(elem: A, sortedList: LList[A]): LList[A] =
      if (sortedList.isEmpty) Cons(elem, Empty())
      else if (compare(elem, sortedList.head) <= 0) Cons(elem, sortedList)
      else Cons(sortedList.head, insert(elem, sortedList.tail))

    val sortedTail = tail.sort(compare)
    insert(head, sortedTail)
  }

  override def zipWith[B, T](list: LList[T], zip: (A, T) => B): LList[B] =
    if (list.isEmpty) Empty()
    else Cons(zip(head, list.head), tail.zipWith(list.tail, zip))

  /*
    [1,2,3,4].foldLeft(0)(x + y)
    = [2,3,4].foldLeft(1)(x + y)
    = [3,4].foldLeft(3)(x + y)
    = [4].foldLeft(6)(x + y)
    = [].foldLeft(10)(x + y)
    = 10
  */
  override def foldLeft[B](start: B)(operator: (B, A) => B): B =
    tail.foldLeft(operator(start, head))(operator)
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

object LList {
  @tailrec
  def find[A](list: LList[A], predicate: A => Boolean): A =
    if (list.isEmpty) throw new NoSuchElementException
    else if (predicate(list.head)) list.head
    else find(list.tail, predicate)
}

//noinspection ScalaFileName, DuplicatedCode,ConvertExpressionToSAM
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

    val someStrings = Cons("dog", Cons("cat", Cons("crocodile", Empty())))
    println(someStrings)

    val evenPredicate: Int => Boolean = _ % 2 == 0

    val doubler: Int => Int = _ * 2

    val doublerList: Int => LList[Int] =
      value => Cons(value, Cons(value + 1, Empty()))

    // map testing
    val numbersDoubler = first3Numbers.map(doubler)
    println(numbersDoubler)
    val numbersNested = first3Numbers.map(doublerList)
    println(numbersNested)

    // filter testing
    val onlyEvenNumbers = first3Numbers.filter(evenPredicate) // [2]
    println(onlyEvenNumbers)

    // test concatenation
    val listInBothWays = first3Numbers ++ first3Numbers_v2
    println(listInBothWays)

    // test flatMap
    val flattenedList = first3Numbers.flatMap(doublerList)
    println(flattenedList)

    // find test
    println(LList.find(first3Numbers, evenPredicate)) // 2
    // println(LList.find(first3Numbers, _ > 5)) // throws a NSEException

    // HOFs exercise testing
    first3Numbers.foreach(println)
    println(first3Numbers_v2.sort(_ - _))
    val zippedList = first3Numbers.zipWith(someStrings, (number, string) => s"$number-$string")
    println(zippedList)
    println(first3Numbers.foldLeft(0)(_ + _ ))
  }
}
