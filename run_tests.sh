#!/usr/bin/env bash -x

SCRIPT_PATH=$(cd "$(dirname "$0")"; pwd)
ALLURE_RESULTS="${SCRIPT_PATH}/allure-results"
ALLURE_REPORT="${SCRIPT_PATH}/allure-report"
LOG_DIR="${SCRIPT_PATH}/logs/"
SELENOID_CONTAINER="aerokube/selenoid:latest-release"
TEST_SUITE_FILE=""

while getopts ":s:" opt; do
    case ${opt} in
        s) TEST_SUITE_FILE="$OPTARG";;
        \?) echo "Invalid option -$OPTARG" >&2; exit 1;;
        :)  echo "Option -$OPTARG requires an argument." >&2; exit 1;;
    esac
done

if [[ "${TEST_SUITE_FILE}" == "" ]];then
    echo "Option -s is mandatory as test suite file path"
    exit 1
fi

if ! [[ -d "${LOG_DIR}" ]]; then
    echo "Creating logs folder ${LOG_DIR}"
    mkdir "${LOG_DIR}"
fi

if [[ -d "${ALLURE_RESULTS}" ]]; then
    echo "Removing folder ${ALLURE_RESULTS}"
    rm -rf "${ALLURE_RESULTS}"
fi

if [[ -d "${ALLURE_REPORT}" ]]; then
    echo "Removing folder ${ALLURE_REPORT}"
    rm -rf "${ALLURE_REPORT}"
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


mvn clean test -D"suiteXmlFile=${TEST_SUITE_FILE}" -D"LOG_DIR=${LOG_DIR}"

allure generate "${ALLURE_RESULTS}"

docker stop $(docker ps -q --filter "ancestor=${SELENOID_CONTAINER}")