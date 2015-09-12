package com.syamantakm.finatra.guice.example

import com.google.inject.{Singleton, Inject}
import com.twitter.finagle.httpx.Request
import com.twitter.finatra.http.Controller

/**
 * @author syamantak.
 */

case class Greetings(name: String)

@Singleton
class MyController @Inject()(myService: MyService) extends Controller {

  get("/greetings") { request: Request =>
    myService.greetings
  }

  post("/echoPost") { request: Greetings =>
    request
  }

  put("/echoPut") { request: Greetings =>
    request
  }

  patch("/echoPatch") { request: Greetings =>
    request
  }
}
