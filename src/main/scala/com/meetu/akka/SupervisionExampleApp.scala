package com.meetu.akka

import akka.actor._
import akka.actor.SupervisorStrategy._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Await

object SupervisionExampleApp extends App {
  implicit val timeout = Timeout(50000 milliseconds)
  val system = ActorSystem("supervisionExample")
  
  val supervisor = system.actorOf(Props[Supervisor], "supervisor")
  val future = supervisor ? Props[Child]
  val child = Await.result(future, timeout.duration).asInstanceOf[ActorRef]
  
  child ! 42
  println("Normal response " + Await.result(child ? "get", timeout.duration).asInstanceOf[Int])
  child ! new ArithmeticException
  println("Arithmetic Exception response " + Await.result(child ? "get", timeout.duration).asInstanceOf[Int])
  child ! new NullPointerException
  println("Null Pointer response " + Await.result(child ? "get", timeout.duration).asInstanceOf[Int])
}

class Supervisor extends Actor {
  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: ArithmeticException => Resume
    case _: NullPointerException => Restart
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
  }

  def receive = {
    case p: Props => sender ! context.actorOf(p)
  }
}

class Child extends Actor {
  var state = 0
  def receive = {
    case ex: Exception => throw ex
    case x: Int => state = x
    case "get" => sender ! state
  }
}