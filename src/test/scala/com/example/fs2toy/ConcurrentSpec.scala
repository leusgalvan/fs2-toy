package com.example.fs2toy

import cats.effect.IO
import org.specs2.mutable.Specification
import fs2.Stream
import Concurrent._

class ConcurrentSpec extends Specification {
  def sleepRandom(): Unit = Thread.sleep((math.random() * 200).toLong)

  "Concurrent" should {
    "get teams from various cities concurrently" in {
      def createTeams(teams: String*): Stream[IO, String] =
        Stream.emits(teams).flatMap(team => Stream.eval(IO {sleepRandom(); team}))
      val teamsByCity = Map(
        "Rosario" -> createTeams("Newells", "Central"),
        "Buenos Aires" -> createTeams("River", "Boca", "San Lorenzo"),
        "Cordoba" -> createTeams("Talleres")
      )
      val cities = teamsByCity.keySet
      val allTeams = Set("Newells", "Central", "River", "Boca", "San Lorenzo", "Talleres")
      getTeamsFromCitiesConcurrently(cities, teamsByCity) shouldEqual(allTeams)
    }
  }
}
