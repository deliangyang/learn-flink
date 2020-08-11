package com.example.flink;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

public class Words {
    public static final String[] WORDS = new String[]{
        "adfasdfds",
        "bbb",
        "ccc"
    };

    public static DataSet<String> getDefaultTextLineDataSet(ExecutionEnvironment env) {
        return env.fromElements(WORDS);
    }
}
