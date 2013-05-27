#!/bin/sh

for file in ../tests/*in.txt
do
    echo -n "$file: "
    answer=$(/usr/bin/time -f "%Us" java -cp bin main/Main < $file)
    outfile="${file%_*}_out.txt"
    if [ ! -e $outfile ]
    then
    	echo "No $outfile file to compare. Maximum clique: $answer"
    else
		if [ $(grep -c "$answer" $outfile) -eq 0 ]
		then
			echo "Fail. This is not maximum clique: $answer"
	   	fi 
   	fi
done
