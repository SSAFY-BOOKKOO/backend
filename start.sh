#!/bin/bash
echo "Checking Docker version..."
docker --version


echo "Checking Docker Compose version..."
docker-compose --version

echo "Starting Docker Compose services..."
docker-compose up -d