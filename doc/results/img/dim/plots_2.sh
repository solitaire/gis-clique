for FILE in dim*; do
gnuplot <<- EOF
        set xlabel "gęstość grafu"
        set ylabel "czas [s]"
        set term png
        set output "${FILE}.png"
        set nokey
        plot "${FILE}" with linespoints
EOF
done
