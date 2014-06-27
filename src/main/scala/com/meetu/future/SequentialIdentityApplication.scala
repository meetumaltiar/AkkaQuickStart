package com.meetu.future

object SequentialIdentityApplication extends App {
  val startTime = System.currentTimeMillis()

  val result1 = timeTakingIdentityFunction(1)
  val result2 = timeTakingIdentityFunction(2)
  val result3 = timeTakingIdentityFunction(3)

  val sum = result1 + result2 + result3
  val seconds = (System.currentTimeMillis() - startTime) / 1000
  println("Sum of 1, 2 and 3 is " + sum + " calculated in " + seconds + " seconds")

  def timeTakingIdentityFunction(number: Int): Int = {
    Thread.sleep(3000) // sleep for three seconds
    number
  }
}