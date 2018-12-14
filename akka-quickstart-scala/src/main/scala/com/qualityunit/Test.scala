package com.qualityunit

import java.util.concurrent.TimeUnit

import akka.pattern.ask
import akka.actor.{ActorRef, ActorSystem}
import akka.util.Timeout
import com.qualityunit.FileService.{CreateFileCommand, GetFileCommand}

import scala.concurrent.Await

object Test extends App {

    implicit val timeout = Timeout(5, TimeUnit.SECONDS)

    val system: ActorSystem = ActorSystem("test")
    val fileService: ActorRef = system.actorOf(FileService.props, "fileService")

    val fileId = FileId("f2")

    //fileService ! CreateFileCommand(fileId, "f2")

    val fileRef = Await.result(
        fileService ask GetFileCommand(fileId), Timeout(5, TimeUnit.SECONDS).duration)
      .asInstanceOf[ActorRef]
    val result = Await.result(
        fileRef ask File.GetState, Timeout(5, TimeUnit.SECONDS).duration)
      .asInstanceOf[FileState]
    println("File " + result.fileName + " exists " +result.exists)

    Thread.sleep(1000)
    system.terminate()
}
