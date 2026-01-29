#!/bin/bash

JAVA_VERSION_EXPECTED=21
if type -p java; then
    echo "Using java executable in PATH"
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    echo "Using java executable in JAVA_HOME"
    _java="$JAVA_HOME/bin/java"
else
    echo "no java"
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo "Java version ${version}"
    if [[ ! ${version/.*} -ge ${JAVA_VERSION_EXPECTED} ]]; then
        echo "Version is lower than ${JAVA_VERSION_EXPECTED}"
        exit 1
    fi
fi

exit 0
