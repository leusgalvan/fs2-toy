package com.example.fs2toy
import fs2.{Pure, Stream}

object Introduction {
  def createSimpleStream(): Stream[Pure, String] = ???

  def createSingleton(element: Int): Stream[Pure, Int] = ???

  def createStreamFromList(list: List[Int]): Stream[Pure, Int] = ???

  def convertStreamToList(finiteStream: Stream[Pure, String]): List[String] = ???

  def createHyphenedString(list: List[String]): Stream[Pure, String] = ???

  def mixPeopleWithTheirHeights(people: List[String], heights: List[String]): Stream[Pure, String] = ???

  def pairSongsWithTheirRatings(songs: List[String], ratings: List[Int]): Stream[Pure, (String, Int)] = ???

  def createIntegersFrom5to10(): Stream[Pure, Int] = ???

  def createInfiniteTongueTwister(): Stream[Pure, String] = ???

  def createInfiniteStreamOfEvenNumbers(): Stream[Pure, Int] = ???

  def createInfiniteStreamOfOnes(): Stream[Pure, Int] = ???

  def takeFirstNaturalNumbers(naturalNumbers: Stream[Pure, Int], n: Int): Stream[Pure, Int] = ???

  case class Person(name: String, age: Int)
  def getPeopleNames(people: Stream[Pure, Person]): Stream[Pure, String] = ???

  def getAdults(people: Stream[Pure, Person]): Stream[Pure, Person] = ???

  def addIsPrettyCoolSuffix(words: Stream[Pure, String]): Stream[Pure, String] = ???

  def repeatEachWord(words: Stream[Pure, String]): Stream[Pure, String] = ???

  def sum(numbers: Stream[Pure, Int]): Stream[Pure, Int] = ???

  def countConsecutiveSuccessfulValues[A](stream: Stream[Pure, A]): Int = ???
}
