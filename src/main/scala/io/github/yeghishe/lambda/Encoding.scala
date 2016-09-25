package io.github.yeghishe.lambda

import java.io.{ InputStream, OutputStream }

import scala.io.Source

private[lambda] object encoding {
  import io.circe._
  import io.circe.parser._
  import io.circe.syntax._

  def in[T](is: InputStream)(implicit decoder: Decoder[T]): T = try {
    decode[T](Source.fromInputStream(is).mkString).getOrElse(throw new RuntimeException("Can't decode json"))
  } finally {
    is.close()
  }

  def out[T](value: T, os: OutputStream)(implicit encoder: Encoder[T]): Unit = try {
    os.write(value.asJson.noSpaces.getBytes("UTF-8"))
  } finally {
    os.close()
  }
}
