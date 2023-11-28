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

## k8s deployment and labels

```shell
kubectl create deployment  perfect-one-prod --image mswinarski/perfect-one:latest --replicas=3       
kubectl label deployment --overwrite perfect-one-prod ver=1 app=perfect-one env=prod  
kubectl get deployment --show-labels  

kubectl create deployment  perfect-one-test --image mswinarski/perfect-one:latest --replicas=1
kubectl label deployment --overwrite perfect-one-test ver=1 app=perfect-one env=test
kubectl get deployment --show-labels

  NAME               READY   UP-TO-DATE   AVAILABLE   AGE     LABELS
  perfect-one-prod   3/3     3            3           4m25s   app=perfect-one,env=prod,ver=1
  perfect-one-test   1/1     1            1           22s     app=perfect-one,env=test,ver=1   
```

```shell
kubectl get deployment --selector "env in (prod)"

  NAME               READY   UP-TO-DATE   AVAILABLE   AGE
  perfect-one-prod   3/3     3            3           7m53s
```   

```shell
kubectl delete deployment --all

deployment.apps "perfect-one-prod" deleted
deployment.apps "perfect-one-test" deleted
``` 

## k8s services:
```shell
kubectl create deployment  perfect-one-prod --image mswinarski/perfect-one:latest --replicas=3 --port=8080       
kubectl label deployment --overwrite perfect-one-prod ver=1 app=perfect-one env=prod   
kubectl expose deployment perfect-one-prod

kubectl create deployment  perfect-one-test --image mswinarski/perfect-one:latest --replicas=1 --port=8080
kubectl label deployment --overwrite perfect-one-test ver=1 app=perfect-one env=test
kubectl expose deployment perfect-one-test

kubectl get services -o wide

  NAME               TYPE        CLUSTER-IP    EXTERNAL-IP   PORT(S)    AGE     SELECTOR
  kubernetes         ClusterIP   10.96.0.1     <none>        443/TCP    6d19h   <none>
  perfect-one-prod   ClusterIP   10.96.73.63   <none>        8080/TCP   13s     app=perfect-one-prod
  perfect-one-test   ClusterIP   10.99.160.2   <none>        8080/TCP   6s      app=perfect-one-test


kubectl exec -it perfect-one-prod-54d47b6888-7dgn2 -- ash
apk add bind-tools
host perfect-one-prod
  perfect-one-prod.default.svc.cluster.local has address 10.96.73.63
  
apk add busybox-extras


telnet perfect-one-prod:8080
GET / HTTP/1.1
Host: perfect-one-prod

  HTTP/1.1 200 
  Content-Type: text/plain;charset=UTF-8
  Content-Length: 74
  Date: Tue, 14 Nov 2023 11:13:18 GMT

  Hello from Perfect-One app! running on: perfect-one-prod-54d47b6888-7dgn2  

kubectl get services
  NAME               TYPE        CLUSTER-IP    EXTERNAL-IP   PORT(S)    AGE
  kubernetes         ClusterIP   10.96.0.1     <none>        443/TCP    6d20h
  perfect-one-prod   ClusterIP   10.96.73.63   <none>        8080/TCP   36m
  perfect-one-test   ClusterIP   10.99.160.2   <none>        8080/TCP   36m

host 10.96.73.63
  63.73.96.10.in-addr.arpa domain name pointer perfect-one-prod.default.svc.cluster.local.
 
host 10.99.160.2
  2.160.99.10.in-addr.arpa domain name pointer perfect-one-test.default.svc.cluster.local. 
  
```

## endpoints
```shell
kubectl get endpoints perfect-one-prod
  NAME               ENDPOINTS                                           AGE
  perfect-one-prod   10.244.0.6:8080,10.244.1.13:8080,10.244.1.14:8080   48m
  
kubectl get endpoints
  NAME               ENDPOINTS                                           AGE
  kubernetes         192.168.49.2:8443                                   6d20h
  perfect-one-prod   10.244.0.6:8080,10.244.1.13:8080,10.244.1.14:8080   49m
  perfect-one-test   10.244.1.15:8080                                    49m
```
## NodePort

Allows to reach a service via any node in the cluster.

```shell
kubectl get service 
  NAME               TYPE        CLUSTER-IP    EXTERNAL-IP   PORT(S)          AGE
  kubernetes         ClusterIP   10.96.0.1     <none>        443/TCP          14d
  perfect-one-prod   NodePort    10.96.73.63   <none>        8080:31866/TCP   8d
  perfect-one-test   ClusterIP   10.99.160.2   <none>        8080/TCP         8d
  
kubectl describe service perfect-one-prod 
  Name:                     perfect-one-prod
  Namespace:                default
  Labels:                   app=perfect-one
                            env=prod
                            ver=1
  Annotations:              <none>
  Selector:                 app=perfect-one-prod
  Type:                     NodePort
  IP Family Policy:         SingleStack
  IP Families:              IPv4
  IP:                       10.96.73.63
  IPs:                      10.96.73.63
  Port:                     <unset>  8080/TCP
  TargetPort:               8080/TCP
  NodePort:                 <unset>  31866/TCP
  Endpoints:                10.244.0.2:8080,10.244.0.5:8080,10.244.0.6:8080
  Session Affinity:         None
  External Traffic Policy:  Cluster
  Events:                   <none>
```

## LoadBalancer

```shell

```