package com.meetu.akka.future

import java.util.concurrent.ForkJoinPool
import scala.concurrent._
import scala.concurrent.duration.DurationInt
import akka.util.Timeout

object FutureDirectlyApp extends App {
  implicit val timeout = Timeout(50 seconds)
  implicit val ec = ExecutionContext.fromExecutor(new ForkJoinPool)

  val future = Future {
    Thread.sleep(1000)
    "Hello" + "World"
  }

  val result = Await.result(future, 100 second)
  println(result)
  println("Thread completes...")
}