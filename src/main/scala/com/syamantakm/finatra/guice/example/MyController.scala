package com.syamantakm.finatra.guice.example

import com.google.inject.{Singleton, Inject}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.QueryParam

/**
 * @author syamantak.
 */

case class Greetings(name: String)

case class Item(id: Long)

case class MyRequest(
                      @QueryParam x: Int,
                      @QueryParam y: Int,
                      items: Seq[Item]
                    )

@Singleton
class MyController @Inject()(myService: MyService) extends Controller {

  get("/greetings") { request: Request =>
    debug("received")
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

  post("/do") { request: MyRequest =>
    debug("received")
    response.ok(request)
  }
}
