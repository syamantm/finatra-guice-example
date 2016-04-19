package com.syamantakm.finagle.client.example

import java.util.concurrent.TimeUnit

import com.twitter.finagle.Http
import com.twitter.finagle.http.{MediaType, Fields, Method, RequestBuilder}
import com.twitter.io.Buf._
import com.twitter.util.{Duration, Await}

/**
 * @author syamantak.
 */
object MyClientMain extends App {
  val getReq = RequestBuilder()
    .url("http://localhost:8888/greetings")
    .buildGet()

  val postReq = RequestBuilder()
    .url("http://localhost:8888/echoPut")
    .addHeader(Fields.ContentType,  MediaType.Json)
    .buildPost(Utf8("""{"name":"bob"}"""))

  val putReq = RequestBuilder()
    .url("http://localhost:8888/echoPut")
    .addHeader(Fields.ContentType,  MediaType.Json)
    .buildPut(Utf8("""{"name":"bob"}"""))

  val patchReq = RequestBuilder()
    .url("http://localhost:8888/echoPatch")
    .addHeader(Fields.ContentType,  MediaType.Json)
    .build(Method.Patch, Some(Utf8("""{"name":"bob"}""")))

  val client = Http.client.newService("localhost:8888")

  val getFuture = client(getReq)

  val getResponse = Await.result(getFuture, Duration(1, TimeUnit.SECONDS))
  println(s"GET : status : ${getResponse.getStatusCode()}, body : ${getResponse.getContentString()}")


  val postFuture = client(putReq)

  val postResponse = Await.result(postFuture, Duration(1, TimeUnit.SECONDS))
  println(s"POST : status : ${postResponse.getStatusCode()}, body : ${postResponse.getContentString()}")

  val putFuture = client(putReq)

  val putResponse = Await.result(putFuture, Duration(1, TimeUnit.SECONDS))
  println(s"PUT : status : ${putResponse.getStatusCode()}, body : ${putResponse.getContentString()}")

  val patchFuture = client(patchReq)

  val patchResponse = Await.result(patchFuture, Duration(1, TimeUnit.SECONDS))
  println(s"PATCH : status : ${patchResponse.getStatusCode()}, body : ${patchResponse.getContentString()}")

}
