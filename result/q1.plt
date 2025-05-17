set output "result/q1.eps"

#set key right top
unset key

set yrange [*:*]
set ytics autofreq
set logscale y 10
set format y "10^{%L}"
set terminal postscript eps enhanced

set ylabel "Latency (ms)" offset 0, 0, 0

set xrange [-0.5:3.5]
# set xtics autofreq
set xtics ("Q1" 0, \
			"Q2" 1, "Q3" 2, "Q4" 3)
# ("Climiate" 0, \
# 	        "Ship" 1, "Bitcoin" 2, \
# 			"Noise" 3)

set xlabel "Queries"

set title "Query optimization performance"

set style data linespoint
set style data histogram
set style histogram cluster gap 2
set style fill solid noborder
set boxwidth 0.9


plot 'result/iotdb.txt' using ($1) title "IoTDB" ls 4 fs pattern 1, \
'result/iotdb-opt.txt' using ($1*0.7) title "IoTDB-v" ls 6 fs pattern 9

unset logscale y
unset format y
#influxdb_perform_Q2_original_scala
