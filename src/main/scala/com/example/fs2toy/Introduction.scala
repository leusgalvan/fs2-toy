package com.example.fs2toy
import fs2.{Pure, Stream}

object Introduction {
  def createSimpleStream(): Stream[Pure, String] = Stream("my", "first", "stream")

  def createSingleton(element: Int): Stream[Pure, Int] = Stream.emit(element)

  def createStreamFromList(list: List[Int]): Stream[Pure, Int] = Stream.emits(list)

  def convertStreamToList(finiteStream: Stream[Pure, String]): List[String] = finiteStream.toList

  def createHyphenedString(list: List[String]): Stream[Pure, String] = Stream.emits(list).intersperse("-")

  def mixPeopleWithTheirHeights(people: List[String], heights: List[String]): Stream[Pure, String] =
    Stream.emits(people).interleave(Stream.emits(heights))

  def pairSongsWithTheirRatings(songs: List[String], ratings: List[Int]): Stream[Pure, (String, Int)] =
    Stream.emits(songs).zip(Stream.emits(ratings))

  def createIntegersFrom5to10(): Stream[Pure, Int] = Stream.range(5, 11)

  def createInfiniteTongueTwister(): Stream[Pure, String] = Stream("tres", "tristes", "tigres").repeat

  def takeFirstNaturalNumbers(naturalNumbers: Stream[Pure, Int], n: Int): Stream[Pure, Int] = naturalNumbers.take(n)

  case class Person(name: String, age: Int)
  def getPeopleNames(people: Stream[Pure, Person]): Stream[Pure, String] = people.map(_.name)

  def getAdults(people: Stream[Pure, Person]): Stream[Pure, Person] = people.filter(_.age >= 21)

  def addIsPrettyCoolSuffix(words: Stream[Pure, String]): Stream[Pure, String] =
    words ++ Stream("is", "pretty", "cool")

  def repeatEachWord(words: Stream[Pure, String]): Stream[Pure, String] = words.flatMap(s => Stream(s, s))

  def sum(numbers: Stream[Pure, Int]): Stream[Pure, Int] = numbers.fold(0)(_+_)
}
