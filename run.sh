#!/bin/sh
git pull && mvn package && docker-compose up --build --force-recreate