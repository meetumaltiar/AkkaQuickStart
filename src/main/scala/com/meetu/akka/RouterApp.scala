package com.meetu.akka

import akka.actor._
import akka.routing.RoundRobinPool
import akka.routing.Broadcast

object RouterApp extends App {
  val system = ActorSystem("routerApp")
  val router = system.actorOf(RoundRobinPool(5).props(Props[RouterWorkerActor]), "workers")
  router ! Broadcast("Hello")
}

class RouterWorkerActor extends Actor {
  def receive = {
    case msg => println(s"Message: $msg received in ${self.path}")
  }
}