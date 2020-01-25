package com.example.fs2toy

import cats.effect.IO
import fs2.Stream

object Effectful {
  trait Logger {
    def log(s: String): Unit
  }
  case class SimpleLogger() extends Logger {
    override def log(s: String): Unit = println(s)
  }

  def createHelloWorldStream(logger: Logger): Stream[IO, Unit] = Stream.eval(IO {logger.log("Hello world")})
  def createFailingStream(): Stream[IO, Unit] = Stream.raiseError[IO](new Exception("oh no"))
  def recoverFromErrorLoggingMessageAndReturning0(stream: Stream[IO, Int], logger: Logger): Stream[IO, Int] =
    stream.handleErrorWith { throwable =>
      logger.log(throwable.getMessage)
      Stream.emit(0)
    }
  def collectResultsFromStreamAsList(stream: Stream[IO, String]): List[String] =
    stream.compile.toList.unsafeRunSync
  def executeEffectsAndIgnoreResultFromStream(stream: Stream[IO, String]): Unit =
    stream.compile.drain.unsafeRunSync
}
