package com.meetu.akka.application

import akka.actor.Actor

class Worker extends Actor {

  def receive = {
    case Work(start, nrOfElements) =>
      sender ! Result(calculatePiForElement(start, nrOfElements))
  }

  def calculatePiForElement(start: Int, nrOfElements: Int): Double = {
    (start until (start + nrOfElements)).foldLeft(0.0) { (acc, elem) => acc + calculateElement(elem) }
  }

  def calculateElement(n: Double): Double = 4.0 * (1 - (n % 2) * 2) / (2 * n + 1)

  def calculatePiFor(start: Int, nrOfElements: Int): Double = {
    var acc = 0.0
    for (n <- start until (start + nrOfElements))
      acc += 4.0 * (1 - (n % 2) * 2) / (2 * n + 1)
    acc
  }
}