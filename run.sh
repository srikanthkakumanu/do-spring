#! /bin/bash

USAGE_PROMPT="[./run.sh up|down|remove]"
ARGS_COUNT=$#
PROJECT_DIRS=("api" "books-service" "todo-service" "videos-service")
ARTIFACTORY_PREFIX="srik1980"

if [ $ARGS_COUNT == 0 ]; then
  echo "Usage: $USAGE_PROMPT"
  exit 1
elif [ $ARGS_COUNT -gt 1 ]; then
  echo "ERROR: Invalid Arguments - Argument Count must be 1 and correct usage: $USAGE_PROMPT"
  exit 1
fi

if [ "$1" = "up" ]; then
  clear
  echo "Building Projects..."
  for dir_name in "${PROJECT_DIRS[@]}"; do
    [ -d "/$dir_name/app/build" ] && ./gradlew clean build || ./gradlew build
  done

  echo "Running Docker Compose script..."
  docker compose up -d
  echo "Containers are Up Now..."
  exit 0

elif [ "$1" = "down" ]; then
  clear
  echo "Shutting down Containers..."
  docker compose down
  exit 0

elif [ "$1" = "remove" ]; then
  clear
  echo "Shutting down Containers..."
  docker compose down
  echo "Removing service containers..."
  for dir_name in "${PROJECT_DIRS[@]}"; do
    echo "Removing $dir_name Image..."
    docker rmi $(docker images | grep -E $ARTIFACTORY_PREFIX/$dir_name | awk '{print $3}')
  done
  echo "Removed service containers..."
  echo "Shutdown completed."
  exit 0

else
  echo "Wrong Argument. It should be: $USAGE_PROMPT"
  exit 1
fi