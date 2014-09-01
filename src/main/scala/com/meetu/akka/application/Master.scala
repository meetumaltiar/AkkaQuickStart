package com.meetu.akka.application

import java.util.concurrent.CountDownLatch
import akka.actor._
import akka.routing.Broadcast
import akka.routing.RoundRobinPool

class Master(nrOfWorkers: Int, nrOfMessages: Int, nrOfElements: Int, latch: CountDownLatch) extends Actor {
  var pi: Double = _
  var nrOfResults: Int = _
  var start: Long = _

  val router = context.actorOf(RoundRobinPool(nrOfWorkers).props(Props[Worker]), "workers")

  def receive = {
    case Calculate =>
      for (i <- 0 until nrOfMessages) {
        router ! Work(i * nrOfElements, nrOfElements)
      }

    case Result(value) =>
      pi += value
      nrOfResults += 1
      if (nrOfResults == nrOfMessages) context.stop(self)
  }

  override def preStart() = start = System.currentTimeMillis

  override def postStop() {
    println(s"Pi estimate:      $pi")
    println(s"Calculation time: ${(System.currentTimeMillis - start)} millis")
    latch.countDown()
  }
}