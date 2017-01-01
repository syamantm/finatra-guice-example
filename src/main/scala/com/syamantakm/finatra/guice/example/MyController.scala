package com.syamantakm.finatra.guice.example

import com.google.inject.{Inject, Singleton}
import com.twitter.finagle.http.{Fields, Request}
import com.twitter.finatra.http.Controller
import com.twitter.finatra.http.request.RequestUtils
import com.twitter.finatra.request.{FormParam, QueryParam, RouteParam}

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
    request.headerMap.get(Fields.AcceptLanguage)
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
