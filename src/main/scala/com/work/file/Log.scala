package com.work.file

import java.beans.BeanProperty

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}


@JsonIgnoreProperties(ignoreUnknown = true)
case class
Log(
     @JsonProperty("ip") @BeanProperty ip: String,
     @JsonProperty("user") @BeanProperty user: String,
     @JsonProperty("path") @BeanProperty path: String,
     @JsonProperty("extra") @BeanProperty extra: Extra,
     @JsonProperty("method") @BeanProperty method: String
   )
