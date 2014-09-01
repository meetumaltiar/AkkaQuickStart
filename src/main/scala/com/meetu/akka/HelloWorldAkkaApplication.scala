package com.meetu.akka

import akka.actor._

object HelloWorldAkkaApplication extends App {
  val system = ActorSystem("myfirstApp")
  val myFirstActor = system.actorOf(Props[MyFirstActor])
  
  myFirstActor ! "Hello World"
  
  myFirstActor.!("Hello World")
}

class MyFirstActor extends Actor {
  def receive = {
    case msg: String => println(msg)
    case _ => println("default")
  }
}