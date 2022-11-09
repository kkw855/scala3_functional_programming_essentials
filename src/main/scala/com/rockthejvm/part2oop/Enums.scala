package com.rockthejvm.part2oop

//noinspection ScalaUnusedSymbol,TypeAnnotation
object Enums {

  object PermissionsWithBits {
    def fromBits(bits: Int): PermissionsWithBits = // whatever
      PermissionsWithBits.NONE
  }

  enum Permissions {
    case READ, WRITE, EXECUTE, NONE

    def openDocument(): Unit =
      if (this == READ) println("opening document...")
      else println("reading not allowed.")
  }

  // constructor args
  enum PermissionsWithBits(bits: Int) {
    case READ extends PermissionsWithBits(4) // 100
    case WRITE extends PermissionsWithBits(4) // 010
    case EXECUTE extends PermissionsWithBits(4) // 001
    case NONE extends PermissionsWithBits(4) // 000
  }

  val somePermissions: Permissions = Permissions.READ

  // standard API
  val somePermissionsOrdinal = somePermissions.ordinal
  val allPermissions = Permissions.values // array of all possible values of the enum
  val readPermission: Permissions = Permissions.valueOf("READ") // Permissions.READ

  def main(args: Array[String]): Unit = {
    somePermissions.openDocument()
    println(somePermissionsOrdinal)
  }
}
