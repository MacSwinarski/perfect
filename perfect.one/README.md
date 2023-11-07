# Perfect-one app

## Build docker image

```shell
docker build -t mswinarski/perfect-one:latest -t mswinarski/perfect-one:$(git log -1 --pretty=%h) .
```

### Push docker image

```shell
docker push mswinarski/perfect-one:latest 
```

## Run app from docker image - docker

```shell
docker run --rm --name perfect-one -p 8080:8080 mswinarski/perfect-one:latest
```

## k8s

```shell
kubectl apply -f ./perfect-one-pod.yaml
kubectl delete -f ./perfect-one-pod.yaml
kubectl describe pods perfect-one
kubectl port-forward perfect-one 8080:8080
kubectl logs -f perfect-one
kubectl exec perfect-one -- jps
kubectl exec -it perfect-one -- ash
```

