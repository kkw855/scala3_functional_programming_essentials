package com.rockthejvm.part4power

import scala.util.Random

//noinspection TypeAnnotation,ScalaUnusedSymbol,RedundantBlock
object PatternMatching {

  // switch on steroids
  val random = new Random()
  val aValue = random.nextInt(100)

  val description = aValue match {
    case 1 => "the first"
    case 2 => "the second"
    case 3 => "the third"
    case _ => s"something else: $aValue"
  }

  // decompose values
  case class Person(name: String, age: Int)
  val bob = Person("Bob", 20)

  val greeting = bob match {
    case Person(n, a) if a < 18 => s"Hello there, my name is $n and I'm $a years old."
    case Person(n, a) => s"Hello there, my name is $n and I'm not allowed to say my age."
    case null => "I don't know who I am."
  }

  /*
    Patterns are matched in order: put the most specific patterns first.
    What if no cases match? MatchError
    What's the type returned? The lowest common ancestor of all types on the right hand side of each branch.
  */

  // Pattern matching on sealed hierarchies
  sealed abstract class Animal
  case class Dog(breed: String) extends Animal
  case class Cat(meowStyle: String) extends Animal

  val anAnimal: Animal = Dog("Terra Nova")
  val animalPM = anAnimal match {
    case Dog(someBreed) => "I've detected a dog"
    case Cat(meow) => "I've detected a cat"
  }

  /*
   * Exercise
   *  show(Sum(Number(2), Number(3))) = "2 + 3"
   *  show(Sum(Sum(Number(2), Number(3)), Number(4)) = "2 + 3 + 4"
   *  show(Prod(Sum(Number(2), Number(3)), Number(4))) = "(2 + 3) * 4"
   *  show(Sum(Prod(Number(2), Number(3)), Number(4)) = "2 * 3 + 4"
  */
  sealed trait Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(expr: Expr): String = expr match
    case Number(n) => n.toString
    case Sum(left, right) => show(left) + " + " + show(right)
    case Prod(left, right) => {
      def maybeShowParentheses(exp: Expr): String = exp match
        case e: Sum => s"(${show(exp)})"
        case _ => show(exp)

      maybeShowParentheses(left) + " * " + maybeShowParentheses(right)
    }

  def main(args: Array[String]): Unit = {
    println(description)
    println(greeting)

    println(show(Sum(Number(2), Number(3))))
    println(show(Sum(Sum(Number(2), Number(3)), Number(4))))
    println(show(Prod(Sum(Number(2), Number(3)), Number(4))))
    println(show(Sum(Prod(Number(2), Number(3)), Number(4))))
  }
}
