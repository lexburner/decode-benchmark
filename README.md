### ByteToMessageDecoder

```
  2 threads and 190 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    88.61ms   20.67ms 237.62ms   76.29%
    Req/Sec     1.08k   233.60     1.67k    72.82%
  Latency Distribution
     50%   84.45ms
     75%   99.30ms
     90%  112.40ms
     99%  164.70ms
  128679 requests in 1.00m, 14.13MB read
Requests/sec:   2141.98
Transfer/sec:    240.91KB
--------------------------
Durations:       60.07s
Requests:        128679
Avg RT:          88.61ms
Max RT:          237.622ms
Min RT:          52.268ms
Error requests:  0
Valid requests:  128679
QPS:             2141.98
--------------------------
```


### AbstractBatchDecoder

```
  2 threads and 190 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    81.80ms   14.28ms 182.20ms   66.04%
    Req/Sec     1.17k   209.14     1.80k    69.99%
  Latency Distribution
     50%   79.61ms
     75%   92.00ms
     90%  101.61ms
     99%  117.24ms
  139258 requests in 1.00m, 15.30MB read
Requests/sec:   2318.85
Transfer/sec:    260.83KB
--------------------------
Durations:       60.05s
Requests:        139258
Avg RT:          81.80ms
Max RT:          182.199ms
Min RT:          52.38ms
Error requests:  0
Valid requests:  139258
QPS:             2318.85
--------------------------
```
