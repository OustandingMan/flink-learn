package org.apache.flink;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.state.api.functions.KeyedStateBootstrapFunction;

public class AccountJavaKeyedStateBootstrapFunction extends KeyedStateBootstrapFunction<String, WordCountPoJo> {
    ValueState<WordCountPoJo> lastState;

    @Override
    public void open(Configuration parameters) {
        ValueStateDescriptor<WordCountPoJo> descriptor =
                new ValueStateDescriptor<WordCountPoJo>("wordcountState", WordCountPoJo.class);
        lastState = getRuntimeContext().getState(descriptor);
    }

    @Override
    public void processElement(WordCountPoJo value, Context ctx) throws Exception {
        lastState.update(value);
    }
}
