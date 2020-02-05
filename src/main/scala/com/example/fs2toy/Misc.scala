package com.example.fs2toy

import java.io.IOException

import cats.effect.{ContextShift, IO, Timer}
import fs2.Stream

import scala.concurrent.duration._

object Misc {
  def emitTemperaturesEvery100Millis(temperature: IO[Int]): Stream[IO, Int] = ???

  def stopAfter1Second[A](stream: Stream[IO, A]): Stream[IO, A] = ???

  def retryApiCall(apiCall: IO[String]): Stream[IO, String] = ???
}
