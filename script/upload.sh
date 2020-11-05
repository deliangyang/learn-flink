#!/usr/bin/env bash

for f in $(find out -name untitled.jar); do
  filename="/mnt/flink-test/$f"
  dir=$(dirname "$filename")
  ssh root@ydl "mkdir -p $dir"
  scp -r "$f" "root@ydl:$filename"
done
