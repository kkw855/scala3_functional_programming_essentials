package com.rockthejvm.practice

import scala.annotation.tailrec

//noinspection ScalaUnusedSymbol
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

  def removePerson(network: Map[String, Set[String]], person: String): Map[String, Set[String]] =
    network - person

  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] = {
    if (!network.contains(a)) throw new IllegalArgumentException(s"The person $a is not part of the network")
    else if (!network.contains(b)) throw new IllegalArgumentException(s"The person $b is not part of the network")
    else {
      val friendsOfA = network(a)
      val friendsOfB = network(b)

      network + (a -> (friendsOfA + b)) + (b -> (friendsOfB + a))
    }
  }

//  def friend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] =
//    if (network.contains(a) && network.contains(b)) {
//      val plusToA = network.updatedWith(a)(_.map(_ + b))
//      plusToA.updatedWith(b)(_.map(_ + a))
//    } else {
//      network
//    }

  def unfriend(network: Map[String, Set[String]], a: String, b: String): Map[String, Set[String]] =
    if (!network.contains(a) || !network.contains(b)) network
    else {
      val friendsOfA = network(a)
      val friendsOfB = network(b)

      network + (a -> (friendsOfA - b)) + (b -> (friendsOfB - a))
    }

  // 2
  def nFriends(network: Map[String, Set[String]], person: String): Int =
    if (!network.contains(person)) -1
    else network(person).size

  def mostFriends(network: Map[String, Set[String]]): String =
    if (network.isEmpty) throw new RuntimeException("Network is empty, no-one with most friends")
    else {
      val best = network.foldLeft(("", -1)) { (currentBest, newAssociation) =>
        // code block
        val currentMostPopularPerson = currentBest._1
        val mostFriendsSoFar = currentBest._2

        val newPerson = newAssociation._1
        val newPersonFriends = newAssociation._2.size

        if (mostFriendsSoFar < newPersonFriends) (newPerson, newPersonFriends)
        else currentBest
      }

      best._1
    }

//  def mostFriends(network: Map[String, Set[String]]): String = {
//    network.view.mapValues(_.size).foldLeft("" -> 0)((z, pair) => if (z._2 > pair._2) z else pair)._1
//  }

  def nPeopleWithNoFriends(network: Map[String, Set[String]]): Int =
    network.count(pair => pair._2.isEmpty)

  // Breadth-first search
  def socialConnection(network: Map[String, Set[String]], a: String, b: String): Boolean = {
    @tailrec
    def search(discoveredPeople: Set[String], consideredPeople: Set[String]): Boolean =
      if (discoveredPeople.isEmpty) false
      else {
        val person = discoveredPeople.head
        val personFriends = network(person)

        if (personFriends.contains(b)) true
        else search(discoveredPeople - person ++ personFriends -- consideredPeople, consideredPeople + person)
      }

    if (!network.contains(a) || !network.contains(b)) false
    else search(network(a), Set(a))
  }

  def main(args: Array[String]): Unit = {
    val empty: Map[String, Set[String]] = Map()
    val network = addPerson(addPerson(empty, "Bob"), "Mary")
    println(network)
    println(unfriend(friend(network, "Bob", "Mary"), "Bob", "Mary"))

    val people = addPerson(addPerson(addPerson(empty, "Bob"), "Mary"), "Jim")
    val simpleNet = friend(friend(people, "Bob", "Mary"), "Jim", "Mary")
    println(simpleNet)
    println(nFriends(simpleNet, "Mary")) // 2
    println(nFriends(simpleNet, "Bob")) // 1
    println(nFriends(simpleNet, "Daniel")) // -1

    println(mostFriends(simpleNet))

    println(nPeopleWithNoFriends(addPerson(simpleNet, "Daniel")))

    println(socialConnection(simpleNet, "Bob", "Jim")) // true
    println(socialConnection(addPerson(simpleNet, "Daniel"), "Bob", "Daniel")) // false
  }
}