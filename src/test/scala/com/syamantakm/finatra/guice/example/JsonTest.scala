package com.syamantakm.finatra.guice.example

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, PropertyNamingStrategy}
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.scalatest.{FlatSpec, Matchers}


case class MyTestClass(id: Long,
                       @JsonInclude
                       name: Option[String] = None,
                       description: Option[String] = None
                      )

/**
  * @author syamantak.
  */
class JsonTest extends FlatSpec with Matchers {

  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.registerModule(new JavaTimeModule())
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
  mapper.setSerializationInclusion(Include.NON_NULL)

  "Json" should "include null field" in {
    val myTestNonNull = MyTestClass(1, Some("Bob"), Some("Foo bar"))
    val myTestNull = MyTestClass(2)

    val json1 = mapper.writeValueAsString(myTestNonNull)
    val json2 = mapper.writeValueAsString(myTestNull)

    json1 shouldBe """{"id":1,"name":"Bob","description":"Foo bar"}"""
    json2 shouldBe """{"id":2,"name":null}"""
  }

}
