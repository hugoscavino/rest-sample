#!/usr/bin/env bash
# Create and start project containers, only if containers do not exist
if [[ $(docker ps) != *"product_postgres_db"* ]]; then
  if [[ $(docker volume ls) != *"product_pgvolume"* ]]; then
    printf "No containers or persistent volumes exist, attempting full compose.\n\n"
    docker-compose up -d
    sleep 10
    printf "Creating initial Product database\n"
    docker exec -i product_postgres_db psql -U postgres -c "CREATE DATABASE product;"
  else
    printf "Containers are stopped but database and message queue persistent volumes already exist.\nAttempting start...\n\n"
    sh ./docker-start.sh
  fi
else
  printf "Existing containers are running and appear to be in a good state.\n"
fi