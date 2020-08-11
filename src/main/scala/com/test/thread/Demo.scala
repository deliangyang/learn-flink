package com.test.thread

import java.net.{ServerSocket, Socket}

object Demo {
  def main(args: Array[String]): Unit = {
    new NetworkService(2020, 2).run()
  }
}


class Handler(sock: Socket) extends Runnable {

  def message = (Thread.currentThread.getName() + "\n").getBytes

  def run(): Unit = {
    val buf = sock.getInputStream.readAllBytes()
    println(buf)
    sock.getOutputStream.write(message)
    sock.getOutputStream.close()
  }
}

class NetworkService(port: Int, poolSize: Int) extends Runnable {
  val serverSocket = new ServerSocket(port)

  def run(): Unit = {
    while (true) {
      val socket = serverSocket.accept()
      (new Handler(socket)).run()
    }
  }
}