FROM amazoncorretto:17 as builder

WORKDIR /build

ADD ./*.kts ./
ADD gradle ./gradle

RUN chmod +x ./gradlew \
    && ./gradlew build jar \
    && cp ./build/libs/*.jar boot.jar


FROM amazoncorretto:17-alpine

ENV JAVA_ARGS=""
ENV PROC_ARGS=""

COPY --from=builder /build/boot.jar ./
COPY --from=builder /build/src/ ./src/

CMD ["java", "$JAVA_ARGS", "-jar", "./boot.jar", "$PROC_ARGS"]
