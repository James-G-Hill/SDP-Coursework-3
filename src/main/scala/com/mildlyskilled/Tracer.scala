package com.mildlyskilled

import akka.actor.{ActorSystem, Props}

/**
 * Coursework 3 for SDP by:
 * vdiasf01 - Vasco Horta
 * eveera01 - Ehshan Veerabangsa
 * kmensa01 - Kwabena mensa-Bonsu
 * jhill01 = James Hill
 */
object Tracer extends App {

  val system = ActorSystem.create("RayTracer")
  val (infile, outfile) = ("src/main/resources/input.dat", "output.png")
  val scene = Scene.fromFile(infile)
  val t = new Trace
  render(scene, outfile, t.Width, t.Height)

  println("rays cast " + t.rayCount)
  println("rays hit " + t.hitCount)
  println("light " + t.lightCount)
  println("dark " + t.darkCount)

  def render(scene: Scene, outfile: String, width: Int, height: Int) = {
    
    val image = new Image(width, height)

    // Init the coordinator -- must be done before starting it.
    Coordinator.init(image, outfile)

    // TODO: Start the Coordinator actor.
    system.actorOf(Props[Coordinator], name = "coordinator")

    scene.traceImage(width, height)

    // TODO:
    // This one is tricky--we can't simply send a message here to print
    // the image, since the actors started by traceImage haven't necessarily
    // finished yet.  Maybe print should be called elsewhere?

  }

}