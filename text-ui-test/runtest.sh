#!/usr/bin/env bash

# Change to project root
cd "$(dirname "$0")/.."

# Delete output from previous run
if [ -e "text-ui-test/ACTUAL.TXT" ]
then
    rm text-ui-test/ACTUAL.TXT
fi

# Use fresh data file for deterministic test output
mkdir -p data
> data/groot.txt

# Run the program with I/O redirection
./gradlew -q runText < text-ui-test/input.txt > text-ui-test/ACTUAL.TXT 2>&1

# Compare actual vs expected
cd text-ui-test

# Convert to Unix format if dos2unix is available (handles line ending differences)
if command -v dos2unix &> /dev/null
then
    cp EXPECTED.TXT EXPECTED-UNIX.TXT
    dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT 2>/dev/null || true
    EXPECTED_FILE=EXPECTED-UNIX.TXT
else
    EXPECTED_FILE=EXPECTED.TXT
fi

# Compare actual vs expected
diff ACTUAL.TXT "$EXPECTED_FILE"
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi
