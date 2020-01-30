package com.example.fs2toy

import cats.effect.IO
import org.specs2.mutable.Specification
import org.specs2.mock._
import fs2.Stream

class EffectfulSpec extends Specification with Mockito {
  import Effectful._

  "Effectful" should {
    "create a stream that prints 'Hello, world'" in {
      val logger = mock[Logger]
      createHelloWorldStream(logger).compile.drain.unsafeRunSync
      there was one(logger).log("Hello world")
    }

    "create a stream that fails with an exception 'oh no'" in {
      createFailingStream().compile.drain.unsafeRunSync must throwA(new Exception("oh no"))
    }

    "create a stream that recovers from an exception printing its message and returning 0" in {
      val logger = mock[Logger]
      val failingStream = Stream(1,2,3) ++ Stream.raiseError[IO](new Exception("stream died"))
      recoverFromErrorLoggingMessageAndReturning0(failingStream, logger)
        .compile
        .toList
        .unsafeRunSync shouldEqual List(1, 2, 3, 0)
      there was one(logger).log("stream died")
    }

    "create a List with the values from a stream" in {
      val stream = Stream.eval(IO {
        SimpleLogger().log("Executing stream...")
        "Pablito"}
      ) ++ Stream("clavo", "un", "clavito")
      collectResultsFromStreamAsList(stream) shouldEqual List("Pablito", "clavo", "un", "clavito")
    }

    "execute effects from stream synchronically ignoring results" in {
      val logger = spy(SimpleLogger())
      val stream = Stream.eval(IO {
        logger.log("Executing stream...")
        "Pablito"}
      ) ++ Stream("clavo", "un", "clavito")
      executeEffectsAndIgnoreResultFromStream(stream)
      there was one(logger).log("Executing stream...")
    }

    "print 'Before' before processing and 'After' afterwards even when there are errors" in {
      val logger = mock[Logger]
      val stream = Stream(1, 2) ++ Stream.raiseError[IO](new Exception("Oops"))
      try {
        printBeforeAndAfter(logger, stream).compile.drain.unsafeRunSync()
      } catch { case _: Exception => }

      there was one(logger).log("Starting")
      there was one(logger).log("Finishing")
    }
  }
}
