wrk -t2 -c60 -d20s -T5 --script=./wrk.lua --latency http://localhost:8080/benchmark
wrk -t2 -c190 -d60s -T5 --script=./wrk.lua --latency http://localhost:8080/benchmark