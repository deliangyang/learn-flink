package com.work.file

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonParser.Feature

import scala.beans.BeanProperty

object JsonParse {

  @JsonIgnoreProperties(ignoreUnknown = true)
  case class Message(@JsonProperty("errno") @BeanProperty errno: String, @JsonProperty("error") @BeanProperty error: String)

  case class Item(@JsonProperty("bw") @BeanProperty bw: String, @JsonProperty("cdn") @BeanProperty cdn: Long, @JsonProperty("ct") @BeanProperty ct: Long)

  case class Outer(@JsonProperty("video_bandwidths") @BeanProperty items: Array[Item])


  def main(args: Array[String]): Unit = {
    val data = "{\"errno\": \"0\",\"error\": \"\",\"cc\": 123}"
    val mapper = new ObjectMapper
    val jsonMap = mapper.readValue(data, classOf[Message]) //转换为HashMap对象
    println(jsonMap.errno)
  }
}
