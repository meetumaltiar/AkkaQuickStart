package com.meetu.akka

import akka.actor._

object ActorPathApp extends App {
  val system = ActorSystem("actorPathApp")

  // create a parent and create its child
  val parent = system.actorOf(Props[ParentActor], "parent")
  parent ! CreateChild

  // the path of child is akka://actorPathApp/user/parent/child
  system.actorFor("akka://actorPathApp/user/parent/child") ! ""
}

class ParentActor extends Actor {
  def receive = {
    case CreateChild =>
      val child = context.actorOf(Props[ChildActor], "child")
      println(s"Child created with path: ${child.path}")
  }
}

class ChildActor extends Actor {
  def receive = {
    case msg => println("In Child")
  }
}

case object CreateChild