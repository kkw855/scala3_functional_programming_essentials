package com.rockthejvm.part2oop

val meaningOfLife = 42
//noinspection ScalaUnusedSymbol
def computeMyLife: String = "Scala"

//noinspection NotImplementedCode,ScalaUnusedSymbol,SingleImport,TypeAnnotation
object PackagesImports { // top-level

  // packages = "folders"

  // fully qualified name
  val aList: com.rockthejvm.practice.LList[Int] = ??? // throws NotImplementedError

  // import

  import com.rockthejvm.practice.LList

  val anotherList: LList[Int] = ???

  // importing under an alias

  import java.util.List as JList

  val aJavaList: JList[Int] = ???

  // import everything

  import com.rockthejvm.practice.*

  val aPredicate: Predicate[Int] = ???

  // import multiple symbols

  import PhysicsConstants.{EARTH_GRAVITY, SPEED_OF_LIGHT}

  val c = SPEED_OF_LIGHT
  val mol = meaningOfLife

  import com.rockthejvm.part2oop.*

  def main(args: Array[String]): Unit = {

  }

  // default imports
  // scala.*, scala.Predef.*, java.lang.*

  // exports
  class PhysicsCalculator {

    import PhysicsConstants.*

    def computePhotonEnergy(wavelength: Double): Double =
      PLACK / wavelength
  }

  // import everything but something
  object PlayingPhysics {

    import PhysicsConstants.{PLACK as _, *}
    // val plank = PLANK // will not work
  }

  object ScienceApp {
    val physicsCalculator = new PhysicsCalculator

    export physicsCalculator.computePhotonEnergy

    def computeEquivalentMass(wavelength: Double): Double =
      computePhotonEnergy(wavelength) / (SPEED_OF_LIGHT * SPEED_OF_LIGHT)
  }
}

object PhysicsConstants {
  // constants
  val SPEED_OF_LIGHT = 299792458
  val PLACK = 6.62e-34 // scientific
  val EARTH_GRAVITY = 9.8
}
