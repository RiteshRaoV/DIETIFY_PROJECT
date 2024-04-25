#!/bin/bash

HOST_IP=$(ip route | awk 'NR==1 {print $3}')

# Export the IP address as an environment variable
export HOST_IP

# Run Docker Compose
docker-compose up 
