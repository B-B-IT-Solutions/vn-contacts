docker-compose down --remove-orphans	-v
docker container remove -f
docker container prune -f
docker volume prune -a -f
docker-compose up