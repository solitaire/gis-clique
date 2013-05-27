for FILE in den*; do
gnuplot <<- EOF
        set xlabel "liczba wierzchołków"
        set ylabel "czas [s]"
        set term png
        set output "${FILE}.png"
        set nokey
        plot "${FILE}" with linespoints
EOF
done
