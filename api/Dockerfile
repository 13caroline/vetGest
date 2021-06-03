FROM ubuntu:20.04

WORKDIR /api 

COPY . .
COPY ./wait-for-it.sh .
RUN chmod +x wait-for-it.sh

RUN apt-get update
RUN apt-get install openjdk-11-jre -y
RUN apt-get install openjdk-11-jdk -y
RUN apt-get install maven -y
RUN apt-get install mysql-client -y
EXPOSE 9090