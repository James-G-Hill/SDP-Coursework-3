package com.mildlyskilled

/**
  * Created by nana.admin on 02/04/2016.
  */

import akka.actor.Actor

/**
  * Created by nana.admin on 02/04/2016.
  */
class RowCalculator (t:Trace,s:Scene,y:Int,eye:Vector)extends Actor{

  def calculator(width:Int,height:Int,sinf:Double,cosf:Double): Unit ={
    val ss =t.AntiAliasingFactor
    for (x <- 0 until width) {

      // This loop body can be sequential.
      var colour = Colour.black

      for (dx <- 0 until ss) {
        for (dy <- 0 until ss) {

          // Create a vector to the pixel on the view plane formed when
          // the eye is at the origin and the normal is the Z-axis.
          val dir = Vector(
            (sinf * 2 * ((x + dx.toFloat / ss) / width - .5)).toFloat,
            (sinf * 2 * (height.toFloat / width) * (.5 - (y + dy.toFloat / ss) / height)).toFloat,
            cosf.toFloat).normalized

          val c = s.trace(Ray(eye, dir)) / (ss * ss)
          colour += c
        }
      }

      if (Vector(colour.r, colour.g, colour.b).norm < 1)
        t.darkCount += 1
      if (Vector(colour.r, colour.g, colour.b).norm > 1)
        t.lightCount += 1

      val p = new Pixel(x,y,colour)
      context.actorSelection("*/coordinator") ! p
    }
  }

  override def receive: Unit ={

 }

}