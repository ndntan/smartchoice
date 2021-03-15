FROM openjdk:11-jdk
MAINTAINER Nguyen Dang Nhut Tan <ndnhuttan@outlook.com>

ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/product-processor/product-processor.jar

ENTRYPOINT ["java", "-jar", "/usr/share/product-processor/product-processor.jar"]
