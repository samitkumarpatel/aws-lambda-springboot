FROM public.ecr.aws/amazoncorretto/amazoncorretto:25

WORKDIR /src

COPY target/dependency/*.jar ./
COPY target/*.jar ./

# Set runtime interface client as default command for the container runtime
ENTRYPOINT [ "/usr/bin/java", "-cp", "./*", "com.amazonaws.services.lambda.runtime.api.client.AWSLambda" ]
# Pass the name of the function handler as an argument to the runtime
CMD [ "org.example.StreamLambdaHandler::handleRequest" ]