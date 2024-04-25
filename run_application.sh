#!/bin/bash

# Set executable permissions for the script on the host machine
chmod +x get_host_ip.sh

# Get the host's IP address using the script
HOST_IP=$(./get_host_ip.sh)

# Pass the IP address as an environment variable to Docker Compose
export HOST_IP

# Run Docker Compose
docker-compose up 
