package com.meetu.akka.application

import java.util.concurrent.CountDownLatch
import akka.actor._
import akka.routing.Broadcast
import akka.routing.RoundRobinPool
import scala.collection.mutable.ListBuffer

class Master(nrOfWorkers: Int, nrOfMessages: Int, nrOfElements: Int, latch: CountDownLatch) extends Actor {
  val results: ListBuffer[Double] = ListBuffer[Double]()
  var start: Long = _

  val router = context.actorOf(RoundRobinPool(nrOfWorkers).props(Props[Worker]), "workers")

  def receive = {
    case Calculate =>
      for (i <- 0 until nrOfMessages) {
        router ! Work(i * nrOfElements, nrOfElements)
      }

    case Result(value) =>
      results += value
      if (results.size == nrOfMessages) context.stop(self)
  }

  override def preStart() = start = System.currentTimeMillis

  override def postStop() {
    println(s"Pi estimate:      ${results.sum}")
    println(s"Calculation time: ${(System.currentTimeMillis - start)} millis")
    latch.countDown()
  }
}