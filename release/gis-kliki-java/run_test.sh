#!/bin/sh

for file in "$@"
do
    echo -n "$file: "
    answer=$(/usr/bin/time -f "%Us" java -cp bin main/Main < $file)
    filename=$(basename "$file")
    extension="${filename##*.}"
    filename="${filename%.*}"
    outfile="${filename}_out.${extension}"
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
