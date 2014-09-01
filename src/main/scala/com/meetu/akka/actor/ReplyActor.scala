package com.meetu.akka.actor

import akka.actor.Actor

object ReplyActor {

}

class LongWorkingActor extends Actor {
  def receive = {
    case number: Int =>
      sender ! ("Hi I received the " + number)
  }
}