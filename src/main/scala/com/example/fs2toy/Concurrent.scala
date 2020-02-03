package com.example.fs2toy

import cats.effect.{ContextShift, IO}
import fs2.Stream

object Concurrent {
  def getTeamsFromCitiesConcurrently(
      cities: Set[String],
      teamsPerCity: String => Stream[IO, String]
  ): Set[String] = {
    implicit val ioContextShift: ContextShift[IO] = IO.contextShift(scala.concurrent.ExecutionContext.Implicits.global)
    cities.map(teamsPerCity).reduce(_.merge(_)).compile.to(Set).unsafeRunSync
  }
}
