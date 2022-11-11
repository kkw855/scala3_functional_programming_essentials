package com.rockthejvm.part3fp

//noinspection ScalaUnusedSymbol,SimplifiableFoldOrReduce
object LinearCollections {

  // Seq = well-defined ordering + indexing
  def testSeq(): Unit = {
    val aSequence = Seq(4,2,3,1)
    // main API: index an element
    val thirdElement = aSequence.apply(2) // element 3
    // map/flatMap/filter/for
    val anIncrementedSequence = aSequence.map(_ + 1) // [5,3,4,2]
    val aFlatMappedSequence = aSequence.flatMap(x => Seq(x, x + 1)) // [4,5,2,3,3,4,1,2]
    val aFilteredSequence =  aSequence.filter(_ % 2 == 0) // [4,2]
    // other methods
    val reversed = aSequence.reverse
    val concatenation = aSequence ++ Seq(5,6,7)
    val sortedSequence = aSequence.sorted // [1,2,3,4]
    val sum = aSequence.foldLeft(0)(_ + _) // 10
    val stringRep = aSequence.mkString("[", ",", "]")

    println(aSequence)
    println(concatenation)
    println(sortedSequence)
  }

  def testLists(): Unit = {
    val aList = List(1,2,3)
    // same API as Seq
    val firstElement = aList.head
    val rest = aList.tail
    // appending and prepending
    val aBiggerList = 0 +: aList :+ 4
    val prepending = 0 :: aList
    // utility methods
    val scalax5 = List.fill(5)("Scala")
  }

  def main(args: Array[String]): Unit = {
    testSeq()
    testLists()
  }
}
