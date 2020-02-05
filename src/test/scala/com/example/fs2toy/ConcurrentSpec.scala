package com.example.fs2toy

import cats.effect.IO
import org.specs2.mutable.Specification
import fs2.Stream
import Concurrent._

class ConcurrentSpec extends Specification {
  def sleepRandom(): Unit = Thread.sleep((math.random() * 1000).toLong)

  "Concurrent" should {
    "interleave quotes coming from two sources" in {
      val quotes1 = IO {List("a1", "a2", "a3")}
      val quotes2 = IO {List("b1", "b2", "b3")}
      val allQuotes = Set("a1", "a2", "a3", "b1", "b2", "b3")
      interleaveQuotes(quotes1, quotes2).compile.toList.unsafeRunSync.toSet shouldEqual allQuotes
    }

    "get teams from various cities concurrently" in {
      def createTeams(teams: String*): Stream[IO, String] =
        Stream.emits(teams).flatMap(team => Stream.eval(IO {sleepRandom(); team}))
      val teamsByCity = Map(
        "Rosario" -> createTeams("Newells", "Central"),
        "Buenos Aires" -> createTeams("River", "Boca", "San Lorenzo"),
        "Cordoba" -> createTeams("Talleres")
      )
      val cities = teamsByCity.keySet.toList
      val allTeams = Set("Newells", "Central", "River", "Boca", "San Lorenzo", "Talleres")
      getTeamsFromCitiesConcurrently(cities, teamsByCity).toSet shouldEqual(allTeams)
    }
  }
}
