package com.example.fs2toy.solution

object Utils {
  trait Logger {
    def log(s: String): Unit
  }
  case class SimpleLogger() extends Logger {
    override def log(s: String): Unit = println(s)
  }
}
