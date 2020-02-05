package com.example.fs2toy

import java.io.IOException

import cats.effect.{IO, Timer}
import org.specs2.mutable.Specification
import Misc._
import fs2.Stream

import scala.concurrent.duration._
import scala.collection.immutable.Queue

class MiscSpec extends Specification {
  sequential

  "Misc" should {
    "emit the temperature every second" in {
      var times = Queue.empty[Long]
      var values = Queue.empty[Int]
      val temperature = IO {
        val x = (math.random * 40).toInt
        times = times.enqueue(System.currentTimeMillis)
        values = values.enqueue(x)
        x
      }

      emitTemperaturesEvery100Millis(temperature).take(10).compile.to(Queue).unsafeRunSync shouldEqual values
      times.tail.zip(times.init).forall{case (x, y) => math.abs(x - y - 100) < 10} should beTrue
    }

    "stop emitting after 1 second" in {
      val value = IO {(math.random * 40).toInt}
      implicit val timer: Timer[IO] = IO.timer(scala.concurrent.ExecutionContext.global)
      val stream = Stream.iterate(0)(_+1).covary[IO].metered(95.millis)
      stopAfter1Second(stream).compile.toList.unsafeRunSync shouldEqual (0 to 9)
    }

    "retry an api call every 100 millis, waiting 50 more millis each time, for a total of 5 attempts, only when it fails with IOException" in {
      var times = Queue.empty[Long]
      var calls = 0
      val apiCall = IO {
        calls += 1
        times = times.enqueue(System.currentTimeMillis)
        if(calls < 5) throw new IOException("boom") else "done"
      }
      retryApiCall(apiCall).compile.toList.unsafeRunSync shouldEqual List("done")
      1.to(4).forall(i => math.abs(times(i) - times(i-1) - 100 - (i -1) * 50) < 10) should beTrue

      val apiCall2 = IO {throw new IllegalArgumentException()}
      retryApiCall(apiCall2).handleErrorWith(_ => Stream("recovered")).compile.toList.unsafeRunSync shouldEqual List("recovered")

      var calls3 = 0
      val apiCall3 = IO {calls3 += 1; if(calls3 < 6) throw new IOException("boom") else "done"}
      retryApiCall(apiCall3).handleErrorWith(_ => Stream("recovered")).compile.toList.unsafeRunSync shouldEqual List("recovered")
    }
  }
}
