mvn clean process-resources \
  -DSERVICE_DB_USER=${SERVICE_DB_USER} \
  -DSERVICE_DB_USER_PASSWORD=${SERVICE_DB_USER_PASSWORD} \
  -DJDBC_URL=${JDBC_URL}