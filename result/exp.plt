
#===================================
reset
set terminal postscript eps enhanced font ",18" color
set size 0.55,0.55

#set style data linespoints
set style fill pattern border -1

set style line 1 lt 1 pt 1  ps 3.0 lw 3 lc rgb "red"
set style line 2 lt 1 pt 8  ps 2.0 lw 3 lc rgb "orange"
set style line 3 lt 1 pt 6  ps 1.5 lw 3 lc rgb "black"
set style line 4 lt 1 pt 2  ps 2.0 lw 3 lc rgb "blue"
set style line 5 lt 1 pt 4  ps 2.0 lw 3 lc rgb "violet"
set style line 6 lt 1 pt 10 ps 2.0 lw 3 lc rgb "dark-green"
set style line 7 lt 1 pt 6  ps 2.0 lw 3 lc rgb "plum"
set style line 8 lt 1 pt 12 ps 3.0 lw 3 lc rgb "cyan"
set style line 9 lt 1 pt 3  ps 3.0 lw 3 lc rgb "dark-green"
set style increment user

set title "" offset 0,-1
set ylabel "" offset 2
set xlabel "" offset 0,0.5
#===================================



load 'result/q1.plt'
