package com.example.fs2toy

import org.specs2.mutable.Specification
import Pipes._
import fs2.{Chunk, Pure, Stream}

class PipesSpec extends Specification {
  "Pipes" should {
    "return the list of chunks of a stream" in {
      val stream = Stream(1, 2, 3) ++ Stream(4, 5, 6)
      getListOfChunks(stream) shouldEqual List(Chunk(1, 2, 3), Chunk(4, 5, 6))
    }

    "return the maximum value of each chunk" in {
      val stream = Stream(2, 3, 1) ++ Stream(8, 5, 6) ++ Stream(7, 8, 9)
      getUnsafeMaxOfEachChunk(stream) shouldEqual List(3, 8, 9)
    }

    "run a given stream through two pipes" in {
      val source = Stream("hola", "que", "tal")
      runThroughPipes(source, fs2.text.utf8Encode, fs2.text.utf8Decode).toList shouldEqual List("hola", "que", "tal")
    }

    "build a pipe that drops the given number of elements from the source stream" in {
      val source = Stream(1, 2, 3) ++ Stream(4) ++ Stream(5, 6)
      source.through(pipeTakeWhile(_ < 1)).toList shouldEqual List()
      source.through(pipeTakeWhile(_ < 2)).toList shouldEqual List(1)
      source.through(pipeTakeWhile(_ < 3)).toList shouldEqual List(1, 2)
      source.through(pipeTakeWhile(_ < 4)).toList shouldEqual List(1, 2, 3)
      source.through(pipeTakeWhile(_ < 5)).toList shouldEqual List(1, 2, 3, 4)
      source.through(pipeTakeWhile(_ < 6)).toList shouldEqual List(1, 2, 3, 4, 5)
      source.through(pipeTakeWhile(_ < 7)).toList shouldEqual List(1, 2, 3, 4, 5, 6)
    }
  }
}
