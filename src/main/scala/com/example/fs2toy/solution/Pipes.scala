package com.example.fs2toy.solution

import fs2.{Chunk, Pipe, Pure, Stream}

object Pipes {
  def getListOfChunks[A](stream: Stream[Pure, A]): List[Chunk[A]] = {
    stream.chunks.toList
  }

  def getUnsafeMaxOfEachChunk(stream: Stream[Pure, Int]): List[Int] = {
    stream.chunks.map(_.toList.max).toList
  }

  def runThroughPipes[A, B, C](
      source: Stream[Pure, A],
      pipe1: Pipe[Pure, A, B],
      pipe2: Pipe[Pure, B, C]): Stream[Pure, C] = {
    source.through(pipe1).through(pipe2)
  }

  def pipeTakeWhile[F[_], A](p: A => Boolean): Pipe[F, A, A] =
    _.pull.scanChunksOpt[Boolean, A](false) { stop =>
      if (stop) None
      else Some(c => c.indexWhere(a => !p(a)).fold((false, c))(i => (true, c.take(i))))
    }.void.stream
}
