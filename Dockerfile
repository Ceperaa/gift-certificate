##из какого образа будем брать всю информацию
#FROM openjdk:11.0.16
## какие файлы из текущего проэкта мы будем копировать в докер image
## . брать файлы из той же папки , /папка - или путь к папке
#COPY . /javadocker
## рабочая директория которые нужно выполнить при помощи образа
#WORKDIR /javadocker
## выполнит определенные действия перед запуском контейнера
##выполняется при запуске самого образа
##RUN java -jar build\libs\GiftCertificates-1.0-SNAPSHOT.jar
#RUN javac -sourcepath src/main/java/ru/clevertec/ecl/CheckApplication.java
##выполняется каждый раз при запуске контейнера
#CMD ["java","CheckApplication"]

FROM openjdk:11
ARG JAR_FILE=build/libs/GiftCertificates-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} GiftCertificates-1.0-SNAPSHOT.jar
EXPOSE 8008

ENTRYPOINT ["java","-jar","/GiftCertificates-1.0-SNAPSHOT.jar"]