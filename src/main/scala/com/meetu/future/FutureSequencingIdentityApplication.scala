package com.meetu.future

import scala.concurrent.{ ExecutionContext, Future }
import java.util.concurrent.Executors

object FutureSequencingIdentityApplication extends App {
  val startTime = System.currentTimeMillis()
  val executorService = Executors.newCachedThreadPool()
  implicit val executionContextExecutor = ExecutionContext.fromExecutorService(executorService)

  val futures = List(1, 2, 3) map { case number => Future { timeTakingIdentityFunction(number) } }
  val composedFuture = Future.sequence(futures)

  composedFuture onSuccess {
    case results =>
      val seconds = (System.currentTimeMillis() - startTime) / 1000
      println("Sum of 1, 2 and 3 is " + results.sum + " calculated in " + seconds + " seconds")
  }

  def timeTakingIdentityFunction(number: Int): Int = {
    Thread.sleep(3000) // sleep for three seconds
    number
  }
}