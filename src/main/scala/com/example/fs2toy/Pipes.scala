package com.example.fs2toy

import fs2.{Chunk, Pipe, Pure, Stream}

object Pipes {
  def getListOfChunks[A](stream: Stream[Pure, A]): List[Chunk[A]] = ???

  def getUnsafeMaxOfEachChunk(stream: Stream[Pure, Int]): List[Int] = ???

  def runThroughPipes[A, B, C](
      source: Stream[Pure, A],
      pipe1: Pipe[Pure, A, B],
      pipe2: Pipe[Pure, B, C]): Stream[Pure, C] = ???

  def pipeTakeWhile[F[_], A](p: A => Boolean): Pipe[F, A, A] = ???
}
