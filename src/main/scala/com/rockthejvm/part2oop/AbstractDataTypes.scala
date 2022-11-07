package com.rockthejvm.part2oop

//noinspection ScalaUnusedSymbol
object AbstractDataTypes {

  val aDog: Animal = new Dog

  // abstract classes can't be instantiated
  // val anAnimal: Animal = new Animal
  val aNonExistentAnimal: Animal = null
  val anInt: Int = throw new NullPointerException

  def main(args: Array[String]): Unit = {}

  // traits
  trait Carnivore { // Scala 3 - traits can have constructor args
    def eat(animal: Animal): Unit
  }

  // practical
  // one class inheritance
  // multiple traits inheritance
  trait ColdBlooded

  abstract class Animal {
    val creatureType: String // abstract

    def eat(): Unit

    // non-abstract fields/methods allowed
    def preferredMeal: String = "anything" // "accessor methods"
  }

  /*
    philosophical difference abstract classes vs traits
    - abstract classes are THINGS
    - traits are BEHAVIORS
   */

  /*
    Any
      AnyRef
        All classes we write
          scala.Null (the null reference)
      AnyVal
        Int, Boolean, Char, ...



          scala.Nothing
   */

  // non-abstract classes must implement the abstract fields/methods
  class Dog extends Animal {
    override val creatureType: String = "domestic"
    // overriding is legal for everything
    override val preferredMeal: String =
      "bones" // overriding accessor method with a field

    override def eat(): Unit = println("crunching this bone")
  }

  class TRex extends Carnivore {
    override def eat(animal: Animal): Unit = println("I'm T-Rex, I eat animals")
  }

  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val creatureType: String = "croc"

    override def eat(): Unit = println("I'm croc, I just crunch stuff")

    override def eat(animal: Animal): Unit = println("croc eating animal")
  }
}
