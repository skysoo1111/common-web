# common-web
API 호출 공통 규격 처리

- [x] Health Check
- [x] Exception Handler 구현
- [x] ResponseBodyAdvice 공통 Response 구현

## health check
~~~http request
GET http://localhost:8080/healthcheck/_check

HTTP/1.1 200 
{
  "code": "0000",
  "message": "Success",
  "data": "_check"
}
~~~