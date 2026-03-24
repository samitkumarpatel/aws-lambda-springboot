FROM public.ecr.aws/lambda/java:25

COPY target/classes ${LAMBDA_TASK_ROOT}
COPY target/dependency/* ${LAMBDA_TASK_ROOT}/lib/

CMD [ "org.example.StreamLambdaHandler::handleRequest" ]
# docker buildx build --platform linux/amd64 --provenance=false -t aws-lambda-springboot .