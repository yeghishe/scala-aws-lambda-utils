package io.github.yeghishe.lambda

import java.io.{ InputStream, OutputStream }

import scala.concurrent.{ Await, ExecutionContext, Future }
import scala.concurrent.duration._

import com.amazonaws.services.lambda.runtime.{ Context, RequestStreamHandler }
import io.circe.{ Decoder, Encoder }
import org.apache.log4j.Logger

abstract class Handler[T, R](implicit decoder: Decoder[T], encoder: Encoder[R]) extends RequestStreamHandler {
  import Encoding._

  protected implicit val logger = Logger.getLogger(this.getClass)

  protected def handler(input: T, context: Context): R
  def handleRequest(is: InputStream, os: OutputStream, context: Context): Unit =
    in(is).flatMap(i => out(handler(i, context), os)).get
}

abstract class FutureHandler[T, R](d: Option[Duration] = None)(
    implicit
    decoder: Decoder[T],
    encoder: Encoder[R],
    ec: ExecutionContext
) extends Handler[T, R] {
  protected def handlerFuture(input: T, context: Context): Future[R]
  protected def handler(input: T, context: Context): R = Await.result(
    handlerFuture(input, context),
    d.getOrElse(Duration(context.getRemainingTimeInMillis().toLong, MILLISECONDS))
  )
}
