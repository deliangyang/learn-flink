
### Learn Flink

#### Submit a job

```bash
cd /mnt/flink-1.10.0
bin/flink run /mnt/flink-test/out/artifacts/scala_wordcount_jar/untitled.jar


bin/flink run /mnt/flink-test/out/artifacts/party_api_jar/untitled.jar  \
  --input /tmp/standard-request-2020110205 \
  --output /tmp/a.csv \
  --output2 /tmp/b.csv \
  --output23 /tmp/c.csv
```

#### create a socket and input some words
```bash
nc -lkp 9999
```

## [Flink Dashboard](http://loclahost:8081)