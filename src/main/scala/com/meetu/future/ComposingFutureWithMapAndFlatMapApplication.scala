package com.meetu.future

import scala.concurrent.{ ExecutionContext, Future }
import java.util.concurrent.Executors

object ComposingFutureWithMapAndFlatMapApplication {
  val executorService = Executors.newCachedThreadPool()
  implicit val executionContextExecutor = ExecutionContext.fromExecutorService(executorService)

  val future1 = Future { timeTakingIdentityFunction(1) }
  val future2 = Future { timeTakingIdentityFunction(2) }
  val future3 = Future { timeTakingIdentityFunction(3) }

  val composedFuture: Future[Int] = future1 flatMap {
    result1 =>
      future2 flatMap {
        result2 =>
          future3 map {
            result3 => result1 + result2 + result3
          }
      }
  }

  def timeTakingIdentityFunction(number: Int): Int = {
    Thread.sleep(3000) // sleep for three seconds
    number
  }
}