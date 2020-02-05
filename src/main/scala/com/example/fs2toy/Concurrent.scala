package com.example.fs2toy

import cats.effect.{ContextShift, IO}
import fs2.Stream
import cats.implicits._

object Concurrent {
  def interleaveQuotes(quotes1: IO[List[String]], quotes2: IO[List[String]]): Stream[IO, String] = {
    implicit val ioContextShift: ContextShift[IO] = IO.contextShift(scala.concurrent.ExecutionContext.Implicits.global)
    Stream.evals(quotes1).merge(Stream.evals(quotes2))
  }

  def getTeamsFromCitiesConcurrently(
      cities: List[String],
      teamsPerCity: String => Stream[IO, String]
  ): List[String] = {
    implicit val ioContextShift: ContextShift[IO] = IO.contextShift(scala.concurrent.ExecutionContext.Implicits.global)
    Stream(cities.map(teamsPerCity):_*).parJoinUnbounded.compile.toList.unsafeRunSync
  }
}
