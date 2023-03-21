#!/bin/bash
FILES="*"
for f in $FILES
do
    if [[ "$f" == *"_small"* ]]; then
        echo "Skipping $f (contains '_small')"
        rm $f
    else
        echo "processing $f"
        convert "$f" -resize 75% -quality 100 "${f%.*}_small.${f##*.}"
    fi
done
