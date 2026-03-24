# aws-lambda-springboot serverless API
The aws-lambda-springboot project, created with [`aws-serverless-java-container`](https://github.com/aws/serverless-java-container).

[Deploy Java Lambda functions with container images](https://docs.aws.amazon.com/lambda/latest/dg/java-image.html).

[Using an alternative base image with the runtime interface client](https://docs.aws.amazon.com/lambda/latest/dg/java-image.html#java-image-clients).

## Build , Test and Deploy

### Build and Create a docker Image
```bash
./mvnw clean install

docker buildx build --platform linux/amd64 --provenance=false -t aws-lambda-springboot .
```

### Run the docker Image locally
```bash
docker run --platform linux/amd64 -p 9000:8080 aws-lambda-springboot

#Endpoint to invoke the api running locally
 curl -s "http://localhost:9000/2015-03-31/functions/function/invocations" \
  -H "Content-Type: application/json" \
  -d '{"resource":"/json-placeholder/user","path":"/json-placeholder/user","httpMethod":"GET","headers":{"Accept":"application/json"},"multiValueHeaders":{},"queryStringParameters":null,"pathParameters":null,"stageVariables":null,"requestContext":{"resourcePath":"/ping","httpMethod":"GET","path":"/ping","stage":"local"},"body":null,"isBase64Encoded":false,"requestContext": {"identity": {}}}' | jq '.body'
```
---