package com.qualityunit

import akka.actor.{ActorLogging, Props}
import akka.persistence.PersistentActor

case class FileId(id : String)

object File {
  def props(fileId: FileId): Props = Props(new File(fileId))

  final case class CreateFile(fileName: String)
  final case class GetState()

  final case class FileCreated(fileName: String)
}

final case class FileState(fileId: FileId, fileName: String, exists: Boolean) {
}

class File(fileId: FileId) extends PersistentActor with ActorLogging {
  import File._

  def persistenceId: String = fileId.id

  var state = FileState(fileId, null, false)

  def onFileCreated(event: FileCreated): Unit =
    state = state.copy(fileName = event.fileName, exists = true)

  def receiveCommand: Receive = {
    case GetState =>
      log.info("Processing GetState for : " + fileId.id)
      Thread.sleep(2000)
      sender ! state
    case CreateFile(fileName) =>
      if (state.exists) {
        throw new Exception("File already exists")
      }
      persist(FileCreated(fileName)) {
        evt => onFileCreated(evt)
      }
  }

  def receiveRecover: Receive = {
    case evt: FileCreated =>
        onFileCreated(evt)
      }
}
