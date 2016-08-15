#!/bin/sh

### BEGIN INIT INFO
# Provides:          Home Automation Client application instance
# Required-Start:    $all
# Required-Stop:     $all
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: starts instance of hac-client
# Description:       starts instance of hac-client using start-stop-daemon
### END INIT INFO

SERVICE_NAME="Home Automation Controller"
APP_HOME=/usr/share/hac-client/
PATH_TO_JAR=$APP_HOME/client-0.1-SNAPSHOT.jar
PID_PATH_NAME=/tmp/hac-pid
DEFAULT_JVM_OPTS="-Dmqtt.server.address=tcp://192.168.1.5:1883  -Dhacfx.server.address=192.168.1.7 -Dhacfx.server.port=9001"
CLASSPATH=$APP_HOME/client-0.1-SNAPSHOT.jar:$APP_HOME/jersey-client-2.23.jar:$APP_HOME/jersey-media-json-jackson-2.23.jar:$APP_HOME/org.eclipse.paho.client.mqttv3-1.0.2.jar:$APP_HOME/commons-0.1-SNAPSHOT.jar:$APP_HOME/javax.ws.rs-api-2.0.1.jar:$APP_HOME/jersey-common-2.23.jar:$APP_HOME/hk2-api-2.4.0-b34.jar:$APP_HOME/javax.inject-2.4.0-b34.jar:$APP_HOME/hk2-locator-2.4.0-b34.jar:$APP_HOME/jersey-entity-filtering-2.23.jar:$APP_HOME/jackson-jaxrs-base-2.5.4.jar:$APP_HOME/jackson-jaxrs-json-provider-2.5.4.jar:$APP_HOME/jackson-annotations-2.5.4.jar:$APP_HOME/javax.annotation-api-1.2.jar:$APP_HOME/jersey-guava-2.23.jar:$APP_HOME/osgi-resource-locator-1.0.1.jar:$APP_HOME/hk2-utils-2.4.0-b34.jar:$APP_HOME/aopalliance-repackaged-2.4.0-b34.jar:$APP_HOME/javassist-3.18.1-GA.jar:$APP_HOME/jackson-core-2.5.4.jar:$APP_HOME/jackson-databind-2.5.4.jar:$APP_HOME/jackson-module-jaxb-annotations-2.5.4.jar:$APP_HOME/javax.inject-1.jar

case $1 in
    start)
        echo "Starting $SERVICE_NAME ..."
        if [ ! -f $PID_PATH_NAME ]; then
            nohup java -classpath "$CLASSPATH" $DEFAULT_JVM_OPTS com.swehacker.hacfx.App /tmp 2>> /dev/null >> /dev/null &
                        echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is already running ..."
        fi
    ;;
    stop)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stoping ..."
            kill $PID;
            echo "$SERVICE_NAME stopped ..."
            rm $PID_PATH_NAME
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
    restart)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stopping ...";
            kill $PID;
            echo "$SERVICE_NAME stopped ...";
            rm $PID_PATH_NAME
            echo "$SERVICE_NAME starting ..."
            nohup java -jar $PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null &
                        echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
esac