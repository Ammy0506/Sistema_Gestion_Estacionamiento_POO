#!/usr/bin/env sh
set -eu
ROOT="$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)"
cd "$ROOT"
mkdir -p target/classes target/test-classes
find src/main/java -name '*.java' > target/sources.txt
javac --release 17 -encoding UTF-8 -d target/classes @target/sources.txt
find src/test/java -name '*.java' > target/test-sources.txt
javac --release 17 -encoding UTF-8 -cp target/classes -d target/test-classes @target/test-sources.txt
java -cp target/classes:target/test-classes estacionamiento.PruebasEstacionamiento
