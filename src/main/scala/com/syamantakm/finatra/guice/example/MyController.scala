package com.syamantakm.finatra.guice.example

import com.google.inject.{Singleton, Inject}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

/**
 * @author syamantak.
 */
@Singleton
class MyController @Inject()(myService: MyService) extends Controller {

  get("/greetings") { request: Request =>
    myService.greetings
  }
}
