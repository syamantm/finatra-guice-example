package com.syamantakm.finatra.guice.example

import com.google.inject.Inject

/**
 * @author syamantak.
 */
trait MyService {
  def greetings(): String
}

class MyServiceImpl @Inject() (myDao: MyDao) extends MyService{
  override def greetings(): String = myDao.greetings
}
