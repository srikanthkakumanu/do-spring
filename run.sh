#! /bin/bash

USAGE_PROMPT="[./run.sh up|down|remove|restart]"
ARGS_COUNT=$#
PROJECT_DIRS=("api" "books-service" "todo-service" "videos-service")
ARTIFACTORY_PREFIX="srik1980"

up() {
  clear
  echo "Building Projects..."
  for dir_name in "${PROJECT_DIRS[@]}"; do
    [ -d "/$dir_name/app/build" ] && ./gradlew clean build || ./gradlew build
  done

  echo "Running Docker Compose script..."
  docker compose up -d
  echo "Containers are Up Now..."
  return
}

down() {
  clear
  echo "Shutting down Containers..."
  docker compose down
  echo "Shutdown completed."
  return
}

remove() {
  clear
  echo "Removing service containers..."
  for dir_name in "${PROJECT_DIRS[@]}"; do
    echo "Removing $dir_name Image..."
    docker rmi $(docker images | grep -E $ARTIFACTORY_PREFIX/$dir_name | awk '{print $3}')
  done
  echo "Removed service containers..."
  return
}

restart() {
  down
  remove
  up
  return
}

if [ $ARGS_COUNT == 0 ]; then
  echo "Usage: $USAGE_PROMPT"
  exit 1
elif [ $ARGS_COUNT -gt 1 ]; then
  echo "ERROR: Invalid Arguments - Argument Count must be 1 and correct usage: $USAGE_PROMPT"
  exit 1
fi

if [ "$1" = "up" ]; then
  up
  exit 0
elif [ "$1" = "down" ]; then
  down
  exit 0
elif [ "$1" = "remove" ]; then
  down
  remove
  exit 0
elif [ "$1" = "restart" ]; then
  restart
  exit 0
else
  echo "Wrong Argument. It should be: $USAGE_PROMPT"
  exit 1
fi

