package com.qualityunit

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorLogging, Props}
import akka.util.Timeout
import com.qualityunit.FileService.{CreateFileCommand, GetFileCommand}

object FileService {
  def props: Props = Props[FileService]
  final case class GetFileCommand(fileId: FileId)
  final case class CreateFileCommand(fileId: FileId, fileName: String)
}

class FileService extends Actor with ActorLogging {

  implicit val timeout : Timeout = Timeout(5, TimeUnit.SECONDS)

  def receive: PartialFunction[Any, Unit] = {

    case GetFileCommand(fileId) =>
      log.info("Processing GetFileCommand for : " + fileId.id)
      val file = context.child(fileId.id)
        .getOrElse(context.actorOf(File.props(fileId), fileId.id))
      sender ! file

    case CreateFileCommand(fileId: FileId, fileName: String) =>
      val file = context.child(fileId.id)
        .getOrElse(context.actorOf(File.props(fileId), fileId.id))
      file ! File.CreateFile(fileName)

    case _ =>
      unhandled()
  }
}
