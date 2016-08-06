flyway migrate -url="jdbc:postgresql://`docker-machine ip`/postgres" -user=postgres -locations=filesystem:../migrations
