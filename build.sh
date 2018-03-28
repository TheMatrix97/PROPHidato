#!/bin/sh -xe
cd src/hidato
javac *.java || exit 1
echo "compilado!"
exit 0
