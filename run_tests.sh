#!/usr/bin/env bash -x

SCRIPT_PATH=$(cd "$(dirname "$0")"; pwd)
ALLURE_RESULTS="${SCRIPT_PATH}/allure-results"
ALLURE_REPORT="${SCRIPT_PATH}/allure-report"
LOG_DIR="${SCRIPT_PATH}/logs/"
SELENOID_CONTAINER="aerokube/selenoid:latest-release"

if [[ -d "${ALLURE_RESULTS}" ]]; then
    echo "Removing folder ${ALLURE_RESULTS}"
    rm -rf "${ALLURE_RESULTS}"
fi

if [[ -d "${ALLURE_REPORT}" ]]; then
    echo "Removing folder ${ALLURE_REPORT}"
    rm -rf "${ALLURE_REPORT}"
fi

if ! [[ -d "${LOG_DIR}" ]]; then
    echo "Creating logs folder ${LOG_DIR}"
    mkdir "${LOG_DIR}"
fi

docker_status=$(docker run -d \
               -p 4444:4444 \
               -v /var/run/docker.sock:/var/run/docker.sock \
               -v ${SCRIPT_PATH}/video/:/opt/selenoid/video/ \
               -e OVERRIDE_VIDEO_OUTPUT_DIR=${SCRIPT_PATH}/video/ \
               -v ${SCRIPT_PATH}/:/etc/selenoid/:ro ${SELENOID_CONTAINER})

arr=[125,126,127]
if [[ "${arr[*]}" == *"${docker_status}"* ]]; then
    echo "Docker response code: ${docker_status}"
    echo "Container ${SELENOID_CONTAINER} hasn't started, check issues."
    exit
else
    echo "Container ${SELENOID_CONTAINER} started."
fi


mvn clean test -D"suiteXmlFile=testng-suites/buyTicket-suite.xml" -D"LOG_DIR=${LOG_DIR}"

allure generate "${ALLURE_RESULTS}"

docker stop $(docker ps -q --filter "ancestor=${SELENOID_CONTAINER}")