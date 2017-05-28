package com.phoenixkahlo.nodeflow

import java.net.{InetSocketAddress, ServerSocket, Socket}

import com.phoenixkahlo.util.QueryIterator
import com.twitter.chill.Kryo
import rx.lang.scala.Observable

/**
  * A service that binds to a port and can form network streams between other instances of this service, through which
  * objects can be exchanged using a kryo service.
  */
class StreamPort(
                  val port: Option[Int],
                  val predicate: (InetSocketAddress, Option[Any]) => Boolean
                ) extends AutoCloseable {

  val kryo: Kryo = new Kryo()

  // bind the server socket
  private val ss = port match {
    case Some(n) => new ServerSocket(n)
    case None => new ServerSocket()
  }
  // observe incoming connections
  

  override def close() = {
    ss.close()
  }

}

object Test {

  def main(args: Array[String]): Unit = {
    println("starting stream port")
    new StreamPort(Some(25565), (_, _) => true)
    Thread.sleep(1000)
    println("connting to...")
    new Socket("localhost", 25565)
    println("...connected")
  }

}
