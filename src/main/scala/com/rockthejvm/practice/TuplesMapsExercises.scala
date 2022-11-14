package com.rockthejvm.practice

object TuplesMapsExercises {

  /*
  * Social network = Map[String, Set[String]]
  * Friend relationships are MUTUAL.
  *
  * - add a person to the network
  * - remove a person from the network
  * - add friend relationship
  * - unfriend
  *
  * - number of friends of a person
  * - who has the most friends
  * - how many people have NO friends
  * + if there is a social connection between two people (direct or not)
  *
  *  Daniel <-> Mary <-> Jane <-> Tom
  */
  def addPerson(network: Map[String, Set[String]], newPerson: String): Map[String, Set[String]] =
    network + (newPerson -> Set())

  def removePerson(network: Map[String, Set[String]], removePerson: String): Map[String, Set[String]] =
    network - removePerson

  def addRelationship(network: Map[String, Set[String]], personA: String, personB: String): Map[String, Set[String]] =
    if (network.contains(personA) && network.contains(personB)) {
      val plusToA = network.updatedWith(personA)(_.map(_ + personB))
      val plusToB = plusToA.updatedWith(personB)(_.map(_ + personA))
      plusToB
    } else {
      network
    }

  def numberOfFriends(network: Map[String, Set[String]], person: String): Int = {
    if (network.contains(person)) network(person).size
    else 0
  }

  def mostFriends(network: Map[String, Set[String]]): (String, Int) = {
    network.view.mapValues(_.size).foldLeft("" -> 0)((z, pair) => if (z._2 > pair._2) z else pair)
  }

  def noFriends(network: Map[String, Set[String]]): Int = {
    network.map(_._2.size).count(_ == 0)
  }

  def main(args: Array[String]): Unit = {

  }
}
