package org.apache.flink

import java.util.Date

import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.configuration.Configuration
import org.apache.flink.state.api.functions.KeyedStateBootstrapFunction
import org.apache.flink.streaming.api.scala.createTypeInformation
import com.flink.learn.bean.CaseClassUtil.Wordcount

class AccountKeyedStateBootstrapFunction()
    extends KeyedStateBootstrapFunction[String, WordCountPoJo] {
  var lastState: ValueState[WordCountPoJo] = _
  override def open(parameters: Configuration): Unit = {
    val desc = new ValueStateDescriptor[WordCountPoJo](
      "wordcountState",
      createTypeInformation[WordCountPoJo])
    lastState = getRuntimeContext().getState(desc)
  }

  override def processElement(
      value: WordCountPoJo,
      ctx: KeyedStateBootstrapFunction[String, WordCountPoJo]#Context): Unit = {

    value.c = new Date().getTime;
    lastState.update(value)

  }
}
