#!/bin/bash

# Script: run-microservices.sh
# Description: Build, push, and deploy all microservices with Docker Compose
# Usage: ./run-microservices.sh [--build] [--start] [--stop] [--clean]

# Configuration
PROJECT_ROOT=$(pwd)
SERVICES=("discovery-server" "auth-service" "task-service" "notification-service" "history-service" "gateway-service")
DOCKER_USER="oussama2003"
NETWORK="microservices-network"

# Functions
build_and_push_services() {
  echo "Building and pushing all microservices..."
  for service in "${SERVICES[@]}"; do
    echo "🔧 Building $service..."
    docker build -t "$DOCKER_USER/$service:latest" -f "$service/Dockerfile" . || exit 1

    echo "📤 Pushing $service to Docker Hub..."
    docker push "$DOCKER_USER/$service:latest" || exit 1
  done
}

start_services() {
  echo "Starting infrastructure services..."
  docker-compose up -d zookeeper kafka redis auth-db task-db history-db notification-db

  echo "Starting Eureka Discovery Server..."
  docker-compose up -d discovery-server

  sleep 10  # Wait for discovery server to initialize

  echo "Starting microservices..."
  docker-compose up -d auth-service task-service notification-service history-service

  sleep 5  # Wait for services to register

  echo "Starting API Gateway..."
  docker-compose up -d gateway-service

  echo "✅ All services started!"
}

stop_services() {
  echo "🛑 Stopping all services..."
  docker-compose down
}

clean_environment() {
  echo "🧹 Cleaning up..."
  docker-compose down --rmi all -v
  docker image prune -af
  docker network prune -f
}

status_check() {
  echo "🔍 Service Status:"
  docker-compose ps

  echo "🌐 Eureka Dashboard:"
  echo "http://localhost:8761"

  echo "🌐 API Gateway:"
  echo "http://localhost:8080"
}

# Main execution
case $1 in
  --build)
    build_and_push_services
    ;;
  --start)
    start_services
    status_check
    ;;
  --stop)
    stop_services
    ;;
  --clean)
    stop_services
    clean_environment
    ;;
  *)
    echo "Usage: $0 [OPTION]"
    echo "Options:"
    echo "  --build   Build & push all Docker images"
    echo "  --start   Start all services"
    echo "  --stop    Stop all services"
    echo "  --clean   Stop and remove all resources"
    exit 1
    ;;
esac

exit 0
