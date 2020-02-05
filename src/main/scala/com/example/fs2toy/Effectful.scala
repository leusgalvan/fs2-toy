package com.example.fs2toy

import cats.effect.IO
import com.example.fs2toy.Utils.Logger
import fs2.Stream

object Effectful {
  def createHelloWorldStream(logger: Logger): Stream[IO, Unit] = ???
  def createFailingStream(): Stream[IO, Unit] = ???
  def recoverFromErrorLoggingMessageAndReturning0(stream: Stream[IO, Int], logger: Logger): Stream[IO, Int] = ???
  def collectResultsFromStreamAsList(stream: Stream[IO, String]): List[String] = ???
  def executeEffectsAndIgnoreResultFromStream(stream: Stream[IO, String]): Unit = ???
  def printBeforeAndAfter(logger: Logger, stream: Stream[IO, Int]): Stream[IO, Int] = ???
}
