package com.kakfa.test

import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.watermark.Watermark


class CustomAssignerWithPeriodicWatermarks extends AssignerWithPeriodicWatermarks[String] {
  override def getCurrentWatermark: Watermark = ???

  override def extractTimestamp(t: String, l: Long): Long = ???
}
