package com.syamantakm.finagle.client.example

import java.util.concurrent.TimeUnit

import com.twitter.finagle.Httpx
import com.twitter.finagle.httpx.{MediaType, Fields, Method, RequestBuilder}
import com.twitter.io.Buf._
import com.twitter.util.{Duration, Await}

/**
 * @author syamantak.
 */
object MyClientMain extends App {

  val putReq = RequestBuilder()
    .url("http://localhost:8888/echoPut")
    .addHeader(Fields.ContentType,  MediaType.Json)
    .buildPut(Utf8("""{"name":"bob"}"""))

  val patchReq = RequestBuilder()
    .url("http://localhost:8888/echoPatch")
    .addHeader(Fields.ContentType,  MediaType.Json)
    .build(Method.Patch, Some(Utf8("""{"name":"bob"}""")))

  val client = Httpx.newService("localhost:8888")

  val putFuture = client(putReq)

  val putResponse = Await.result(putFuture, Duration(1, TimeUnit.SECONDS))
  println(s"PUT : status : ${putResponse.getStatusCode()}, body : ${putResponse.getContentString()}")

  val patchFuture = client(patchReq)

  val patchResponse = Await.result(patchFuture, Duration(1, TimeUnit.SECONDS))
  println(s"PATCH : status : ${patchResponse.getStatusCode()}, body : ${patchResponse.getContentString()}")

}
