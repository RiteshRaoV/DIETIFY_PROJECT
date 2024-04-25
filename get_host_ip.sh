#!/bin/bash

# Get the IP address of the host machine
HOST_IP=$(ip route | awk 'NR==1 {print $3}')

# Export the IP address as an environment variable
export HOST_IP
