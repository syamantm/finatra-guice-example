package com.syamantakm.finatra.guice.example


import java.sql.Timestamp

import com.google.inject.{Inject, Singleton}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.exceptions._
import com.twitter.finatra.http.internal.exceptions.json.JsonParseExceptionMapper
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.finatra.json.internal.caseclass.exceptions.CaseClassMappingException
import com.twitter.inject.{Injector, Logging, TwitterModule}

/**
  * Testing finatra exception message override. Not to be used in production in it's current form.
  * @author syamantak.
  */

case class MyExceptionResponse(errorId: String,
                               timestamp: Timestamp,
                               errors: Seq[String])

@Singleton
class MyExceptionMapper @Inject()(
                                   response: ResponseBuilder)
  extends DefaultExceptionMapper
    with Logging {
  override def toResponse(request: Request, throwable: Throwable): Response = {
    warn(s"Exception Caught")
    throwable match {
      case e: HttpException =>
        val builder = response.status(e.statusCode)
        builder.json(MyExceptionResponse("internal", new Timestamp(System.currentTimeMillis()), e.errors))
      case e: HttpResponseException =>
        val builder = response.status(e.response.status)
        builder.json(MyExceptionResponse("internal", new Timestamp(System.currentTimeMillis()), Seq(e.response.contentString)))
      case t => response.internalServerError(MyExceptionResponse("service", new Timestamp(System.currentTimeMillis()), Seq(t.getMessage)))
    }
  }
}

@Singleton
class MyCaseClassExceptionMapper @Inject()(
                                            response: ResponseBuilder)
  extends ExceptionMapper[CaseClassMappingException] {

  override def toResponse(request: Request, e: CaseClassMappingException): Response =
    response.badRequest.json(errorsResponse(e))

  private def errorsResponse(e: CaseClassMappingException): MyExceptionResponse =
    MyExceptionResponse("invalid_json", new Timestamp(System.currentTimeMillis()), e.errors.map(_.getMessage()))
}

object MyExceptionMapperModule extends TwitterModule {
  override def configure() {
    bindSingleton[DefaultExceptionMapper].to[MyExceptionMapper]
  }

  override def singletonStartup(injector: Injector) {
    val manager = injector.instance[ExceptionManager]
    manager.add[JsonParseExceptionMapper]
    manager.add[MyCaseClassExceptionMapper]
  }
}