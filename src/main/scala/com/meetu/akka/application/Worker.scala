package com.meetu.akka.application

import akka.actor.Actor

class Worker extends Actor {

  def receive = {
    case Work(start, nrOfElements) =>
      sender ! Result(calculatePiFor(start, nrOfElements))
  }

  def calculatePiFor(start: Int, nrOfElements: Int): Double = {
    var acc = 0.0
    for (n <- start until (start + nrOfElements))
      acc += 4.0 * (1 - (n % 2) * 2) / (2 * n + 1)
    acc
  }
}