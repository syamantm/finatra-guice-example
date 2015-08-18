package com.syamantakm.finatra.guice.example

import com.twitter.inject.TwitterModule
import com.twitter.inject.app.{App => TwitterApp}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FlatSpec, Matchers => ScalaTestMatchers}
/**
 * @author syamantak.
 */
@RunWith(classOf[JUnitRunner])
class MyServiceTest extends FlatSpec with ScalaTestMatchers with BeforeAndAfter with TwitterApp {

  override protected def modules: Seq[TwitterModule] = Seq(MyModule)

  before {
    main()
  }

  "MyService" should "say greetings" in {
    val myService = injector.instance[MyService]

    myService.greetings() should be("Hello")
  }

}
