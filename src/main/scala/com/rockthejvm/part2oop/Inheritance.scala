package com.rockthejvm.part2oop

//noinspection ScalaUnusedSymbol, ScalaUnusedSymbol
object Inheritance {

  val cat = new Cat
  val dog = new Dog

  def main(args: Array[String]): Unit = {
    println(dog) // println(dog.toString)
  }

  class Animal {
    val creatureType = "wild"

    def eat(): Unit = println("nomnomnom")
  }

  class Cat extends Animal {
    def crunch(): Unit = {
      eat()
      println("crunch, crunch")
    }
  }

  class Person(val name: String, age: Int) {
    def this(name: String) =
      this(name, 0)
  }

  class Adult(name: String, age: Int, idCard: String)
    extends Person(name) // must specify super-constructor

  // overriding
  class Dog extends Animal {
    override val creatureType = "domestic"

    override def eat(): Unit = println("mmm, I like this bone")

    override def toString: String = "a dog"
  }

  // overloading vs overriding
  class Crocodile extends Animal {
    override val creatureType: String = "very wild"

    override def eat(): Unit = println("I can eat anything, I'm a croc")

    // overloading: multiple methods with the same name, different signatures
    // different signature =
    //    different argument list (different number of args + different arg types)
    //    + different return type (optional)
    def eat(animal: Animal): Unit = println("I'm eating this poor fella")

    def eat(dog: Dog): Unit = println("eating a dog")

    def eat(person: Person): Unit = println(
      s"I'm eating a human with the name ${person.name}"
    )

    def eat(person: Person, dog: Dog): Unit = println(
      "I'm eating a human AND the dog"
    )

    // def eat(): Int = 45
    def eat(dog: Dog, person: Person): Unit = println(
      "I'm eating a human AND the dog"
    )

  }
}
