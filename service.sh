package() {
  mvn clean package -Dmaven.test.skip=true
}

case "$1" in
  clean)
    mvn clean
    ;;
  package)
    package
    ;;
  war)
    mvn clean package -U -Dmaven.test.skip=true tomcat7:run
    ;;
  jar)
    package
    java -jar -Xmx256m user-web/target/user-web-v1-SNAPSHOT-war-exec.jar
    ;;
  *)
    echo "clean | package | war | jar"
    ;;
esac
