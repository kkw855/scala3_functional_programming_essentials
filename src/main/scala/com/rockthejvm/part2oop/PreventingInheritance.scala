package com.rockthejvm.part2oop

//noinspection ScalaUnusedSymbol
object PreventingInheritance {
  def main(args: Array[String]): Unit = {}

  // sealing a type hierarchy = inheritance only permitted inside this file
  sealed class Guitar(nStrings: Int)

  class Person(val name: String) {
    final def enjoyLife(): Int = 42
  }
  // class Cat extends Animal // illegal

  class Adult(name: String) extends Person(name) {
    // override def enjoyLife(): Int = 999 // cannot override final method
  }

  final class Animal // cannot be inherited

  class ElectricGuitar(nStrings: Int) extends Guitar(nStrings)

  class AcousticGuitar(nStrings: Int) extends Guitar(6)

  // no modifier = "not encouraging" inheritance
  // not mandatory, good practice
  open class ExtensibleGuitar(
                               nStrings: Int
                             ) // open = specifically marked for extension
}
