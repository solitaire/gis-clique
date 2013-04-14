#!/bin/sh

if [ ! -f "gis" ]
then
	echo "Missing 'gis' binary file. Type 'make'."
	exit 1
fi

export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/lib

for file in ../tests/*in.txt
do
    echo "$file: "
    /usr/bin/time -f "%Us" ./gis $file > "${file%_*}_out.txt"
done
