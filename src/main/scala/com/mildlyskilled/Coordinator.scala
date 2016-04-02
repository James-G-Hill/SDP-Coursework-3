package com.mildlyskilled

import akka.actor.Actor

/**
  * TODO
  * Make this an actor and write a message handler for at least the
  * set method.
  */
object Coordinator extends Actor {
  
  def init(im: Image, of: String) = {
    image = im
    outfile = of
    waiting = im.width * im.height
  }

  // Number of pixels we're waiting for to be set.
  var waiting = 0
  var outfile: String = null
  var image: Image = null

  // TODO: make set a message
  override def receive = {
    case pixel(x, y, c) =>
      set(x, y, c)
  }
  
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

case class pixel(x: Int, y: Int, c: Colour) {}