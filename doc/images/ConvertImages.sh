#!/bin/bash
FILES="*"
for f in $FILES
do
    if [[ "$f" == *"_small"* ]]; then
        echo "Skipping $f (contains '_small')"
        rm $f
    else
        echo "processing $f"
        convert "$f" -resize 80% -sharpen 0x2 "${f%.*}_small.${f##*.}"
    fi
done
