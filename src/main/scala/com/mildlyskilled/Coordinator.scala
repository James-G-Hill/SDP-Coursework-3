package com.mildlyskilled

import akka.actor.Actor

/**
  * TODO
  * Make this an actor and write a message handler for at least the
  * set method.
  */
object Coordinator {
  
  def init(im: Image, of: String) = {
    image = im
    outfile = of
    waiting = im.width * im.height
  }

  // Number of pixels we're waiting for to be set.
  var waiting = 0
  var outfile: String = null
  var image: Image = null

  def set(x: Int, y: Int, c: Colour) = {
    image(x, y) = c
    waiting -= 1
    if (waiting < 1) print
  }

  def print = {
    assert(waiting == 0)
    image.print(outfile)
  }
}

/**
 * The Coordinator Actor.
 */
class Coordinator extends Actor {
    override def receive = {
      case Pixel(x, y, c) =>{
        Coordinator.set(x, y, c)
      }
    }
}

/**
 * The Pixel
 * @param x position
 * @param y position
 * @param c Colour
 */
case class Pixel(x: Int, y: Int, c: Colour) {}