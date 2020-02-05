package com.example.fs2toy

import java.io.IOException

import cats.effect.{ContextShift, IO, Timer}
import fs2.Stream

import scala.concurrent.duration._

object Misc {
  def emitTemperaturesEvery100Millis(temperature: IO[Int]): Stream[IO, Int] = {
    implicit val timer: Timer[IO] = IO.timer(scala.concurrent.ExecutionContext.global)
    Stream.repeatEval(temperature).metered(100.milliseconds)
  }

  def stopAfter1Second[A](stream: Stream[IO, A]): Stream[IO, A] = {
    implicit val timer: Timer[IO] = IO.timer(scala.concurrent.ExecutionContext.global)
    implicit val ioContextShift: ContextShift[IO] = IO.contextShift(scala.concurrent.ExecutionContext.Implicits.global)
    stream.interruptAfter(1.second)
  }

  def retryApiCall(apiCall: IO[String]): Stream[IO, String] = {
    implicit val timer: Timer[IO] = IO.timer(scala.concurrent.ExecutionContext.global)
    Stream.retry(
      apiCall,
      100.milliseconds,
      _ + 50.milliseconds,
      5,
      _.isInstanceOf[IOException]
    )
  }
}
