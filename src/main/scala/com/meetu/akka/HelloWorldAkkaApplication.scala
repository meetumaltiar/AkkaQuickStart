package com.meetu.akka

import akka.actor._

object HelloWorldAkkaApplication extends App {
  val system = ActorSystem("myfirstApp")
  val myFirstActor: ActorRef = system.actorOf(Props[MyFirstActor])
  myFirstActor ! "Hello World" // equivalent code myFirstActor.!("Hello World")
}

class MyFirstActor extends Actor {
  def receive = {
    case msg: String => println(msg)
    case _ => println("default")
  }
}