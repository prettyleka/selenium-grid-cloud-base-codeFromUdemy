#Contents of Dockerfile
#Dockerfile to build an image which supports testing our Qxf2 Page Object Model.
FROM ubuntu
MAINTAINER Qxf2 Services


# Chrome Driver
RUN mkdir -p /opt/selenium \
    && curl http://chromedriver.storage.googleapis.com/2.45/chromedriver_linux64.zip -o /opt/selenium/chromedriver_linux64.zip \
    && cd /opt/selenium; unzip /opt/selenium/chromedriver_linux64.zip; rm -rf chromedriver_linux64.zip; ln -fs /opt/selenium/chromedriver /usr/local/bin/chromedriver;
