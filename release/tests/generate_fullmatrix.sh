#!/bin/bash

for file in $1/*in.txt
do
    if [[ "$file" == *in* ]]
    then
		echo $file
    	N=$(cat $file | wc -l)
    	filename=${file/in/fullmatrix}
    	echo "DL N = $N" > $filename
    	echo "Data:" >> $filename
		cat $file >> $filename
    fi
done
