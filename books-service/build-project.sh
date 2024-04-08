#! /bin/bash

ARGS_COUNT=$#

if [ $ARGS_COUNT == 0 ]; then
  echo "Usage: [./build-project.sh up] or [./build-project.sh down]"
  exit 1
elif [ $ARGS_COUNT -gt 1 ]; then
  echo "ERROR: Invalid Arguments - Argument Count must be 1 and it should be 'up' or 'down'"
  echo "Usage: [./build-project.sh up] or [./build-project.sh down]"
  exit 1
fi

if [ "$1" = "up" ]; then
  clear
  echo "Building ${PWD##*/} project..."
  [ -d "/app/build" ] && ./gradlew clean build || ./gradlew build
  echo "Running Docker Compose script..."
  docker compose up -d
  echo "Containers are Up Now..."
  exit 0
elif [ "$1" = "down" ]; then
  clear
  echo "Shutting down Containers..."
  docker compose down
  echo "Removing service containers..."
  docker rmi srik1980/books-service
  docker images srik1980/books-service
  echo "Removed service containers..."
  echo "Shutdown completed."
  exit 0
else
  echo "Wrong Argument Usage: It should be [./build-project.sh up] or [./build-project.sh down]"
  exit 1
fi