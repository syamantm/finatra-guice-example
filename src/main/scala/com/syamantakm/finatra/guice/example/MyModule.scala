package com.syamantakm.finatra.guice.example

import com.google.inject.{Provides, Singleton}
import com.twitter.inject.{Injector, TwitterModule}

/**
 * @author syamantak.
 */
object MyModule extends TwitterModule {

  @Singleton
  @Provides
  def provideDao: MyDao = new MyDao


  @Singleton
  @Provides
  def provideService(injector: Injector): MyService = {
    val myDao = injector.instance[MyDao]
    new MyService(myDao)
  }
}
