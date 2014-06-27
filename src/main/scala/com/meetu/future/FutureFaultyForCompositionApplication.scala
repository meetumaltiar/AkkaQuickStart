package com.meetu.future

import scala.concurrent.{ ExecutionContext, Future }
import java.util.concurrent.Executors

object FutureFaultyForCompositionApplication extends App {
  val startTime = System.currentTimeMillis()
  val executorService = Executors.newCachedThreadPool()
  implicit val executionContextExecutor = ExecutionContext.fromExecutorService(executorService)

  val composedFuture: Future[Int] = for {
    result1 <- Future { timeTakingIdentityFunction(1) }
    result2 <- Future { timeTakingIdentityFunction(2) }
    result3 <- Future { timeTakingIdentityFunction(3) }
  } yield result1 + result2 + result3

  composedFuture onSuccess {
    case sum =>
      val seconds = (System.currentTimeMillis() - startTime) / 1000
      println("Sum of 1, 2 and 3 is " + sum + " calculated in " + seconds + " seconds")
  }

  def timeTakingIdentityFunction(number: Int): Int = {
    Thread.sleep(3000) // sleep for three seconds
    number
  }
}