package com.syamantakm.finatra.guice.example

import com.twitter.finagle.httpx.Status._
import com.twitter.finatra.http.test.EmbeddedHttpServer
import com.twitter.inject.server.FeatureTest

/**
 * @author syamantak.
 */
class MyServerFeatureTest extends FeatureTest {
  override protected def server = new EmbeddedHttpServer(new MyServer {
    override val overrideModules = Seq(integrationTestModule)
  })

  "Say greetings" in {
    server.httpGet(
      path = "/greetings",
      andExpect = Ok,
      withBody = "Hello")
  }
}
