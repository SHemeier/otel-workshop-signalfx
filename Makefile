export BACKEND_SERVER_PORT=50000
export FRONTEND_SERVER_PORT=50001

DEFAULT_REDIS_PORT=6379

.DEFAULT_GOAL := compile

.PHONY: list-all-containers
list-all-containers:
	docker container ls --all


.PHONY: run-redis
run-redis:
	docker run -d -p $(DEFAULT_REDIS_PORT):$(DEFAULT_REDIS_PORT) --name redis_workshop \
	redis:5.0.8

.PHONY: stop-redis
stop-redis:
	docker stop redis_workshop
	docker rm redis_workshop

.PHONY: run-otelcol
run-otelcol:
	docker run -d -p 55680:55680 --name otelcol_workshop \
    -v $(shell pwd)/otelcol/otel-collector-config.yaml:/otel-collector-config.yaml \
    --env SIGNALFX_TOKEN \
	otel/opentelemetry-collector-contrib-dev:b18c1ca932cc35f4a69d8992b391e6875c93da1d \
	--config otel-collector-config.yaml

.PHONY: stop-otelcol
stop-otelcol:
	docker stop otelcol_workshop
	docker rm otelcol_workshop

.PHONY: run-backend
run-backend: compile
	OTEL_RESOURCE_ATTRIBUTES="service.name=backend" \
	java -cp ./build/libs/otel-workshop-all-0.1.0.jar backend.BackEnd

.PHONY: run-frontend
run-frontend: compile
	OTEL_RESOURCE_ATTRIBUTES="service.name=frontend" \
	java -cp ./build/libs/otel-workshop-all-0.1.0.jar frontend.FrontEnd

.PHONY: run-loadgenerator
run-loadgenerator: compile
	OTEL_RESOURCE_ATTRIBUTES="service.name=loadgenerator" \
	java -cp ./build/libs/otel-workshop-all-0.1.0.jar loadgenerator.LoadGenerator

.PHONY: compile
compile:
	./gradlew fatJar

.PHONY: format-code
format-code:
	./gradlew goJF
