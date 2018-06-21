#!/usr/bin/env bash -x

SCRIPT_PATH=$(cd "$(dirname "$0")"; pwd)
ALLURE_RESULTS="${SCRIPT_PATH}/allure-results"
export ALLURE_RESULTS
ALLURE_REPORT="${SCRIPT_PATH}/allure-report"
LOG_DIR="${SCRIPT_PATH}/logs/"
export LOG_DIR
SELENOID_CONTAINER="aerokube/selenoid:latest-release"
export SELENOID_CONTAINER

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
