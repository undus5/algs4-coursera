#!/usr/bin/env bash

DIR=$(dirname $(realpath $0))
cd ${DIR}

pack() {
    cd $1
    FILE=${1}.zip
    if [[ -f ${FILE} ]]; then
        rm ${FILE}
    fi
    shift
    zip -q ../${FILE} $@
}

case $1 in
    "percolation" | "percolation/")
        pack percolation Percolation.java PercolationStats.java
    ;;
    "queues" | "queues/")
        pack queues Deque.java RandomizedQueue.java Permutation.java
    ;;
    "collinear" | "collinear/")
        pack collinear Point.java BruteCollinearPoints.java FastCollinearPoints.java
    ;;
    *)
        echo "Usage: pack.sh [percolation|queues|collinear]"
    ;;
esac
