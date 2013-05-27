#!/bin/sh

for file in "$@"
do
    echo -n "$file: "
    answer=$(/usr/bin/time -f "%Us" java -cp bin main/Main < $file)
    echo "Maximum clique: $answer"
done
