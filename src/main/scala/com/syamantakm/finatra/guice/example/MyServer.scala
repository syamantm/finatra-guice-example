package com.syamantakm.finatra.guice.example

import com.twitter.finagle.httpx.{Response, Request}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.logging.filter.LoggingMDCFilter

/**
 * @author syamantak.
 */

object MyServerMain extends MyServer

class MyServer extends HttpServer  {

  override val modules = Seq(MyModule)

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[MyController]
  }
}
