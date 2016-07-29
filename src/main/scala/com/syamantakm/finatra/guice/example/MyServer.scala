package com.syamantakm.finatra.guice.example

import com.twitter.finagle.http.{Response, Request}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{LoggingMDCFilter, CommonFilters}
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.logging.modules.Slf4jBridgeModule

/**
 * @author syamantak.
 */

object MyServerMain extends MyServer

class MyServer extends HttpServer  {

  override val modules = Seq(Slf4jBridgeModule, MyModule)

  override def exceptionMapperModule = MyExceptionMapperModule

  override def configureHttp(router: HttpRouter): Unit = {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[MyController]
  }
}
