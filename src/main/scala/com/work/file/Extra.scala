package com.work.file

import java.beans.BeanProperty

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}


@JsonIgnoreProperties(ignoreUnknown = true)
case class
Extra(
       @JsonProperty("errno") @BeanProperty errno: Int,
       @JsonProperty("executionTime") @BeanProperty executionTime: Int,
       @JsonProperty("upstreamCost") @BeanProperty upstreamCost: Int,
     )

