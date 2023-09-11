#!/usr/bin/env bash
# Stop and remove containers.
docker-compose down
# Remove existing unattached docker volumes. (dangling)
docker volume rm $(docker volume ls -f dangling=true -q)