package com.work.file

import java.beans.BeanProperty

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}


@JsonIgnoreProperties(ignoreUnknown = true)
case class
Extra(
       @JsonProperty("errno") @BeanProperty errno: Long,
       @JsonProperty("executionTime") @BeanProperty executionTime: Long,
       @JsonProperty("upstreamCost") @BeanProperty upstreamCost: Long,
     )

