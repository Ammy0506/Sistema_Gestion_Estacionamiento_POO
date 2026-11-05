#!/usr/bin/env sh
set -eu
ROOT="$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)"
cd "$ROOT"
mkdir -p target/classes
find src/main/java -name '*.java' > target/sources.txt
javac --release 17 -encoding UTF-8 -d target/classes @target/sources.txt
java -cp target/classes estacionamiento.main.Main
