#!/bin/bash
BIN=""
if [ "$(uname)" == "Darwin" ]; then
   echo 'MAC'
   BIN=./gradlew
elif [ "$(expr substr $(uname -s) 1 5)" == "Linux" ]; then
   echo 'Linux'
   BIN=./gradlew
elif [ "$(expr substr $(uname -s) 1 10)" == "MINGW32_NT" ]; then
   echo 'Window 32'
   BIN=./gradlew.bat
elif [ "$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" ]; then
    echo 'Window 64'
   BIN=./gradlew.bat
fi
echo "Formatting..."

$BIN ktlintFormat --daemon

echo "Running lint check..."

$BIN ktlintCheck --daemon

status=$?

# return 1 exit code if running checks fails
[ $status -ne 0 ] && exit 1
exit 0