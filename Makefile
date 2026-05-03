PORT ?= 8080

.PHONY: run test stop clean

run:
	PORT=$(PORT) docker compose up --build -d

test:
	./gradlew test

stop:
	docker compose down

clean:
	docker compose down -v
	./gradlew clean