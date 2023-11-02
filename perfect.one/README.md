# Perfect-one app

## Build docker image

```shell
docker build -t mswinarski/perfect-one:latest -t mswinarski/perfect-one:$(git log -1 --pretty=%h) .
```

### Push docker image

```shell
docker push mswinarski/perfect-one:latest 
```

## Run app from docker image

```shell
docker run --rm -p 8080:8080 mswinarski/perfect-one:latest
```
