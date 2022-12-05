FROM payara/micro:5.2022.4-jdk11

COPY ./target/cavelinkerserver/WEB-INF/lib/mysql-connector-j-8.0.31.jar $DEPLOY_DIR
COPY ./target/cavelinkerserver.war $DEPLOY_DIR
CMD ["--deploymentDir", "/opt/payara/deployments", "--contextroot", "ROOT"]

