package com.syamantakm.finatra.guice.example

/**
 * @author syamantak.
 */
trait MyDao {
  def greetings(): String
}

class MyDaoImpl extends MyDao {
  override def greetings(): String = "Hello"
}
