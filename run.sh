#!/bin/bash
java -jar backend/target/backend-1.0-SNAPSHOT.jar &
java -jar frontend/target/frontend-1.0-SNAPSHOT.jar &
echo 'Started server...'