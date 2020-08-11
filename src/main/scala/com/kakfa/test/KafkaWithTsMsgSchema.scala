package com.kakfa.test

import org.apache.flink.api.common.serialization.DeserializationSchema
import org.apache.flink.api.common.typeinfo.TypeInformation


class KafkaWithTsMsgSchema extends DeserializationSchema[(String, Int)] {
  override def deserialize(bytes: Array[Byte]): (String, Int) = ???

  override def isEndOfStream(t: (String, Int)): Boolean = ???

  override def getProducedType: TypeInformation[(String, Int)] = ???
}
