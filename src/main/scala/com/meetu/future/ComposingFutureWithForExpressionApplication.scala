package com.meetu.future

import scala.concurrent.{ ExecutionContext, Future }
import java.util.concurrent.Executors

object ComposingFutureWithForExpressionApplication {
  val executorService = Executors.newCachedThreadPool()
  implicit val executionContextExecutor = ExecutionContext.fromExecutorService(executorService)

  val future1 = Future { timeTakingIdentityFunction(1) }
  val future2 = Future { timeTakingIdentityFunction(2) }
  val future3 = Future { timeTakingIdentityFunction(3) }

  val composedFuture: Future[Int] = for {
    result1 <- future1
    result2 <- future2
    result3 <- future3
  } yield result1 + result2 + result3

  def timeTakingIdentityFunction(number: Int): Int = {
    Thread.sleep(3000) // sleep for three seconds
    number
  }
}