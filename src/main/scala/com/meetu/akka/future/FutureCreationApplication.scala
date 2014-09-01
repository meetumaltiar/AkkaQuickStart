package com.meetu.akka.future

import scala.concurrent.{ ExecutionContext, Future }
import java.util.concurrent.Executors

object FutureCreationApplication extends App {
  val executorService = Executors.newCachedThreadPool()
  implicit val executionContextExecutor = ExecutionContext.fromExecutorService(executorService)

  val future1 = Future { timeTakingIdentityFunction(1) }
  val future2 = Future { timeTakingIdentityFunction(2) }
  val future3 = Future { timeTakingIdentityFunction(3) }

  def timeTakingIdentityFunction(number: Int): Int = {
    Thread.sleep(3000) // sleep for three seconds
    number
  }
}