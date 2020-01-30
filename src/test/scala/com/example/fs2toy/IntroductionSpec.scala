package com.example.fs2toy

import fs2.{Pure, Stream}
import org.specs2.mutable.Specification

class IntroductionSpec extends Specification {
  import Introduction._

  "Introduction" should {
    "create a stream with apply" in {
      createSimpleStream().toList shouldEqual List("my", "first", "stream")
    }

    "create a singleton stream" in {
      createSingleton(42).toList shouldEqual List(42)
    }

    "create a stream from a given list" in {
      createStreamFromList(List(1, 5, 4)).toList shouldEqual List(1, 5, 4)
    }

    "convert a finite stream of strings to a list of strings" in {
      convertStreamToList(Stream("hola", "que", "tal")) shouldEqual List("hola", "que", "tal")
    }

    "create a stream consisting of the given list of strings with hyphens in between" in {
      createHyphenedString(List("a", "lot", "of", "strings")).toList shouldEqual
        List("a", "-", "lot", "-", "of", "-", "strings")
    }

    "create a stream consisting of the given people with their given heights in between" in {
      mixPeopleWithTheirHeights(List("Kobe", "Leus"), List("1.98", "1.79")).toList shouldEqual
        List("Kobe", "1.98", "Leus", "1.79")
    }

    "create a stream consisting of songs paired with their rankings" in {
      pairSongsWithTheirRatings(List("Asereje", "Amiga mia", "Something about us"), List(2, 1, 3)).toList shouldEqual
        List(("Asereje", 2), ("Amiga mia", 1), ("Something about us", 3))
    }

    "create a stream with all integers from 5 to 15" in {
      createIntegersFrom5to10().toList shouldEqual List(5, 6, 7, 8, 9, 10)
    }

    "create an infinite stream consisting of the words 'tres', 'tristes', and 'tigres' repeated forever" in {
      createInfiniteTongueTwister().take(9).toList shouldEqual
        List("tres", "tristes", "tigres", "tres", "tristes", "tigres", "tres", "tristes", "tigres")
    }

    "create a stream with the first n natural numbers, given an array of all the natural numbers" in {
      val naturalNumbers = Stream.iterate(1)(_ + 1)
      takeFirstNaturalNumbers(naturalNumbers, 5).toList shouldEqual List(1, 2, 3, 4, 5)
    }

    "create a stream with the names of the given people" in {
      val people = Stream(Person("Leus", 29), Person("Masi", 28), Person("Ile", 32))
      getPeopleNames(people).toList shouldEqual List("Leus", "Masi", "Ile")
    }

    "create a stream with given people who are 21 or older" in {
      val homero = Person("Homero", 39)
      val lisa = Person("Lisa", 8)
      val marge = Person("Marge", 36)
      val juan = Person("Juan", 21)
      val people = Stream(homero, lisa, marge, juan)
      getAdults(people).toList shouldEqual List(homero, marge, juan)
    }

    "create a stream consisting of the given words plus the suffix 'is', 'pretty', 'cool'" in {
      addIsPrettyCoolSuffix(Stream("fs2")).toList shouldEqual List("fs2", "is", "pretty", "cool")
    }

    "create a stream of words where each given word is repeated" in {
      repeatEachWord(Stream("co", "pa", "me")).toList shouldEqual List("co", "co", "pa", "pa", "me", "me")
    }

    "create a stream whose only element is the sum of the elements of the given stream" in {
      sum(Stream.range(1, 101)).toList shouldEqual List(5050)
    }

    "create a stream consisting only of ones" in {
      createInfiniteStreamOfOnes().take(100).toList.forall(_ == 1) should beTrue
    }

    "count the exceptions produced by a stream" in {
      val stream = Stream.range(0, 10) ++
        Stream.range(10, 20).map(_ => throw new Exception("a")) ++
        Stream.range(20, 30)
      countConsecutiveSuccessfulValues(stream) shouldEqual 10
    }
  }
}
