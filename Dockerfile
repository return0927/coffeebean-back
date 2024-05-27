FROM amazoncorretto:17 as builder

WORKDIR /build

COPY ./*.kts ./
COPY gradle/ ./gradle/
COPY gradlew .

RUN chmod +x ./gradlew \
    && ./gradlew build --no-daemon 2>/dev/null || true

COPY . /build/
RUN chmod +x ./gradlew \
    && ./gradlew build jar --no-daemon \
    && rm -rf build/libs/*-plain.jar \
    && export JAR=build/libs/*.jar \
    && cp $JAR boot.jar


FROM amazoncorretto:17-alpine

ENV JAVA_ARGS=""
ENV PROC_ARGS=""

COPY --from=builder /build/boot.jar ./
COPY --from=builder /build/src/ ./src/

CMD ["sh", "-c", "java $JAVA_ARGS -jar boot.jar $PROC_ARGS"]

