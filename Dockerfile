FROM public.ecr.aws/amazoncorretto/amazoncorretto:25

RUN mkdir -p /function
WORKDIR /function

COPY target/dependency/*.jar ./
COPY target/*.jar ./

ENTRYPOINT [ "/usr/bin/java", "-cp", "./*", "com.amazonaws.services.lambda.runtime.api.client.AWSLambda" ]
CMD [ "org.example.StreamLambdaHandler::handleRequest" ]