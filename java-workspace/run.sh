#!/bin/sh

for file in ../tests/*in.txt
do
    echo -n "$file: "
    answer=$(/usr/bin/time -f "%Us" java -cp bin main/Main < $file)
    if [ $(grep -c "$answer" "${file%_*}_out.txt") -eq 0 ]
    then
    	echo "Fail. This is not maximum clique: $answer"
   	fi 
done
