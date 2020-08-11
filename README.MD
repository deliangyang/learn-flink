
### Learn Flink

#### Submit a job

```bash
cd /mnt/flink-1.10.0
bin/flink run /mnt/flink-test/out/artifacts/scala_wordcount_jar/untitled.jar
```

#### create a socket and input some words
```bash
nc -lkp 9999
```
