# Sample HMAC for spring boot

## run local

```sh
./mvnw clean install
./mvnw spring-boot:run
```

## open

<http://localhost:8080/users>
<http://localhost:8080/users/get>

response will include `X-Signature` header
