# Perfect-two app

## Build docker image

```shell
docker build -t mswinarski/perfect-two:latest -t mswinarski/perfect-two:$(git log -1 --pretty=%h) .
```

### Push docker image

```shell
docker push mswinarski/perfect-two:latest 
```

## Run app from docker image - docker

```shell
docker run --rm --name perfect-two -p 8080:8080 mswinarski/perfect-two:latest
```

## k8s

```shell
kubectl apply -f ./perfect-two-pod.yaml
kubectl delete -f ./perfect-two-pod.yaml
kubectl describe pods perfect-two
kubectl port-forward perfect-two 8080:8080
kubectl logs -f perfect-two
kubectl exec perfect-two -- jps
kubectl exec -it perfect-two -- ash
```

## k8s deployment and labels

```shell
kubectl create deployment  perfect-two-prod --image mswinarski/perfect-two:latest --replicas=3       
kubectl label deployment --overwrite perfect-two-prod ver=1 app=perfect-two env=prod  
kubectl get deployment --show-labels  

kubectl create deployment  perfect-two-test --image mswinarski/perfect-two:latest --replicas=1
kubectl label deployment --overwrite perfect-two-test ver=1 app=perfect-two env=test
kubectl get deployment --show-labels

  NAME               READY   UP-TO-DATE   AVAILABLE   AGE     LABELS
  perfect-two-prod   3/3     3            3           4m25s   app=perfect-two,env=prod,ver=1
  perfect-two-test   1/1     1            1           22s     app=perfect-two,env=test,ver=1   
```

```shell
kubectl get deployment --selector "env in (prod)"

  NAME               READY   UP-TO-DATE   AVAILABLE   AGE
  perfect-two-prod   3/3     3            3           7m53s
```   

```shell
kubectl delete deployment --all

deployment.apps "perfect-two-prod" deleted
deployment.apps "perfect-two-test" deleted
``` 

## k8s services:
```shell
kubectl create deployment  perfect-two-prod --image mswinarski/perfect-two:latest --replicas=3 --port=8080       
kubectl label deployment --overwrite perfect-two-prod ver=1 app=perfect-two env=prod   
kubectl expose deployment perfect-two-prod

kubectl create deployment  perfect-two-test --image mswinarski/perfect-two:latest --replicas=1 --port=8080
kubectl label deployment --overwrite perfect-two-test ver=1 app=perfect-two env=test
kubectl expose deployment perfect-two-test

kubectl get services -o wide

  NAME               TYPE        CLUSTER-IP    EXTERNAL-IP   PORT(S)    AGE     SELECTOR
  kubernetes         ClusterIP   10.96.0.1     <none>        443/TCP    6d19h   <none>
  perfect-two-prod   ClusterIP   10.96.73.63   <none>        8080/TCP   13s     app=perfect-two-prod
  perfect-two-test   ClusterIP   10.99.160.2   <none>        8080/TCP   6s      app=perfect-two-test


kubectl exec -it perfect-two-prod-54d47b6888-7dgn2 -- ash
apk add bind-tools
host perfect-two-prod
  perfect-two-prod.default.svc.cluster.local has address 10.96.73.63
  
apk add busybox-extras


telnet perfect-two-prod:8080
GET / HTTP/1.1
Host: perfect-two-prod

  HTTP/1.1 200 
  Content-Type: text/plain;charset=UTF-8
  Content-Length: 74
  Date: Tue, 14 Nov 2023 11:13:18 GMT

  Hello from Perfect-Two app! running on: perfect-two-prod-54d47b6888-7dgn2  

kubectl get services
  NAME               TYPE        CLUSTER-IP    EXTERNAL-IP   PORT(S)    AGE
  kubernetes         ClusterIP   10.96.0.1     <none>        443/TCP    6d20h
  perfect-two-prod   ClusterIP   10.96.73.63   <none>        8080/TCP   36m
  perfect-two-test   ClusterIP   10.99.160.2   <none>        8080/TCP   36m

host 10.96.73.63
  63.73.96.10.in-addr.arpa domain name pointer perfect-two-prod.default.svc.cluster.local.
 
host 10.99.160.2
  2.160.99.10.in-addr.arpa domain name pointer perfect-two-test.default.svc.cluster.local. 
  
```

## endpoints
```shell
kubectl get endpoints perfect-two-prod
  NAME               ENDPOINTS                                           AGE
  perfect-two-prod   10.244.0.6:8080,10.244.1.13:8080,10.244.1.14:8080   48m
  
kubectl get endpoints
  NAME               ENDPOINTS                                           AGE
  kubernetes         192.168.49.2:8443                                   6d20h
  perfect-two-prod   10.244.0.6:8080,10.244.1.13:8080,10.244.1.14:8080   49m
  perfect-two-test   10.244.1.15:8080                                    49m
```
## NodePort

Allows to reach a service via any node in the cluster.

```shell
kubectl get service 
  NAME               TYPE        CLUSTER-IP    EXTERNAL-IP   PORT(S)          AGE
  kubernetes         ClusterIP   10.96.0.1     <none>        443/TCP          14d
  perfect-two-prod   NodePort    10.96.73.63   <none>        8080:31866/TCP   8d
  perfect-two-test   ClusterIP   10.99.160.2   <none>        8080/TCP         8d
  
kubectl describe service perfect-two-prod 
  Name:                     perfect-two-prod
  Namespace:                default
  Labels:                   app=perfect-two
                            env=prod
                            ver=1
  Annotations:              <none>
  Selector:                 app=perfect-two-prod
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