package com.rockthejvm.part2oop

import com.rockthejvm.part2oop.PreventingInheritance.Guitar

//noinspection ScalaUnusedSymbol,TypeAnnotation
object AccessModifiers {

  val aPerson = new Person("Alice")
  val aKid = new Kid("David", 5)

  def main(args: Array[String]): Unit = {
    println(aKid.greetPolitely())
  }

  class Person(val name: String) {
    protected def sayHi(): String = s"Hi, my name is $name"

    private def watchNetflix(): String =
      "I'm binge watching my favorite series..." // only accessible inside the class
  }

  // protected == access to inside the class + children classes
  class Kid(override val name: String, age: Int) extends Person(name) {
    def greetPolitely(): String = // no modifier = "public"
      sayHi() + "I love to play!"
  }

  // complication
  class KidWithParents(
      override val name: String,
      age: Int,
      momName: String,
      dadName: String
  ) extends Person(name) {
    var mom = new Person(momName)
    val dad = new Person(dadName)

    // def everyoneSayHi(): String =
    //  sayHi() + s"Hi, I'm $name, and here are my parents: " + mom.sayHi() + dad.sayHi() // not legal
  }

  val kid = KidWithParents("a", 10, "b", "c")
  println(kid.mom.name)
  println(kid.dad.name)
}
