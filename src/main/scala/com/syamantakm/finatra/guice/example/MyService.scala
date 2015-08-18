package com.syamantakm.finatra.guice.example

import com.google.inject.Inject

/**
 * @author syamantak.
 */
class MyService @Inject() (myDao: MyDao) {
  def greetings(): String = myDao.greetings
}
