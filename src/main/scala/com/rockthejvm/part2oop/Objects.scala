package com.rockthejvm.part2oop

//noinspection ScalaUnusedSymbol,TypeAnnotation, AccessorLikeMethodIsEmptyParen
object Objects {

  val theSingleton: MySingleton.type = MySingleton
  val anotherSingleton: MySingleton.type = MySingleton
  val isSameSingleton = theSingleton == anotherSingleton
  // objects can have fields and methods
  val theSingletonField = MySingleton.aField
  val theSingletonMethodCall = MySingleton.aMethod(99)
  // methods and fields in classes are used for instance-dependent functionality
  val mary = new Person("Mary")
  val mary_v2 = new Person("Mary")
  val maryGreeting = mary.sayHi()
  // methods fields in objects are used for instance-independent functionality - "static"
  val humansCanFly = Person.canFly()
  val nEyesHuman = Person.N_EYES
  // equality
  // 1 - equality of reference - usually defined as ==
  val sameMary = mary eq mary_v2 // false, difference instances
  val sameSingleton = MySingleton eq MySingleton // true
  // 2 - equality of "sameness" - in Java defined as .equals
  val sameMary_v2 = mary equals mary_v2
  val sameMary_v3 = mary == mary_v2 // same as equals - false
  val sameSingleton_v2 = MySingleton == MySingleton // true

  def main(args: Array[String]): Unit = {
    println(sameMary)
    println(sameMary_v2)
    println(sameMary_v3)
  }

  class Person(name: String) {
    def sayHi(): String = s"Hi, my name is $name"
  }

  object MySingleton { // type + the only instance of this type
    val aField = 45

    def aMethod(x: Int): Int = x + 1
  }

  // companions = class + object with same name in the same file
  object Person { // companion object
    // can access each other's private fields and methods
    val N_EYES = 2

    def canFly(): Boolean = false
  }

  // Scala application = object + def main(args: Array[String]): Unit
  /*
    public class Objects {
      public static void main(String[] args) { ... }
    }
   */

  // objects can extend classes
  object BigFoot extends Person("Big Foot")
}
