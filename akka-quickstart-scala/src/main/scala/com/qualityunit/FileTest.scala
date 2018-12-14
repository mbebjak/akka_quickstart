package com.qualityunit
/*
import java.util.concurrent.TimeUnit

import akka.pattern.ask
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, PoisonPill, Props}
import akka.util.Timeout
import com.qualityunit.FileService.CreateFileLinkCommand


case class FileLinkId(id : String)




case class GetFileCommand(fileId : FileId)

object Files {
  def props: Props = Props[Files]
  final case class GetFileCommand(fileId : FileId)

  final case class FileResponse(fileId : FileId)
}

class Files extends Actor with ActorLogging {
  import Files._
  def receive: PartialFunction[Any, Unit] = {
    case GetFileCommand(fileId) =>
      log.info("File get requested (from " + sender() + "): " + fileId.id)
      val aa = context.

      val file = context.child(fileId.id)
        .getOrElse(context.actorOf(File.props(fileId), fileId.id))
      sender ! FileResponse(fileId)
    case _ =>
      unhandled()
  }
}

object Links {
  def props: Props = Props[FileService]
  final case class CreateLinkCommand(fileId : FileId)
}

class Links extends Actor with ActorLogging {
  import Links._

  implicit val timeout : Timeout = Timeout(5, TimeUnit.SECONDS)

  val files = context.actorOf(Files.props, "files")

  def receive: PartialFunction[Any, Unit] = {
    case CreateLinkCommand(fileId) =>
      log.info("File link requested (from " + sender() + "): " + fileId.id)

      val fileFuture = (files ? GetFileCommand(fileId))
      fileFuture.onComplete({
        case ActorRef =>
          log.info("File get requested (from " + sender() + "): " + fileId.id)
      })()
    case _ =>
      unhandled()
  }
}

object FileTest extends App {

  val system: ActorSystem = ActorSystem("fileTest")
  val fileService: ActorRef = system.actorOf(FileService.props, "fileService")

  fileService ! CreateFileLinkCommand(FileId("f1"))

  fileService ! CreateFileLinkCommand(FileId("f1"))
  fileService ! CreateFileLinkCommand(FileId("f1"))
  fileService ! CreateFileLinkCommand(FileId("f1"))

  fileService ! CreateFileLinkCommand(FileId("f2"))

  fileService.ask(CreateFileLinkCommand(FileId("f2"))(Timeout(4.seconds))).andThen({})
}
*/