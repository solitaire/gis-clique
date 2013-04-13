#!/bin/sh

if [ ! -f "gis" ]
then
	echo "Missing 'gis' binary file. Type 'make'."
	exit 1
fi

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/lib

for file in tests/*in.txt
do
    echo -n "$file: "
    your=$(/usr/bin/time -f "%Us" ./gis $file | wc -w)
    ideal=$(cat "${file%_*}_out.txt" | wc -w)
    if [ $your -ne $ideal ]
    then
    	echo "Fail. Your maximum clique has size "$your", when it should be "$ideal"."
    fi
done
