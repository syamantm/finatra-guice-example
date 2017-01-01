package com.syamantakm.finatra.guice.example

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter

/**
  * @author syamantak.
  */

object MyServerMain extends MyServer

class MyServer extends HttpServer {

  override val modules = Seq(MyExceptionMapperModule, MyModule)

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[MyController]
  }
}
