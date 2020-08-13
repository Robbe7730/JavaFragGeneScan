#!/bin/bash

diff <(sort < meta/output_pre) <(./meta/runFGS -t train/454_10 -p 16 -w 0 < /home/robbe/Documents/2019-2020/semester2/ComBio/Project/FragGeneScanPlusPlus/example/NC_000913-454.fna | sort) --report-identical-files
