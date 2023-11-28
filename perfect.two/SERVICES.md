# Services with Perfect-one and Perfect-two

Create 2 pods:
```shell
kubectl apply -f ./perfect.one/perfect-two-one.yaml
kubectl apply -f ./perfect.one/perfect-two-two.yaml 
```

Expose pods ports to local:
```shell
kubectl port-forward pods/perfect-one 8081:8080
kubectl port-forward pods/perfect-two 8082:8080
```

Expose pods services:
```shell
kubectl expose pod perfect-one 
kubectl expose pod perfect-two

kubectl get service
  NAME          TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)    AGE
  kubernetes    ClusterIP   10.96.0.1        <none>        443/TCP    20d
  perfect-one   ClusterIP   10.110.180.222   <none>        8080/TCP   32s
  perfect-two   ClusterIP   10.105.156.42    <none>        8080/TCP   19s
  
  
kubectl exec -it perfect-one -- ash
  host perfect-one
    perfect-one.default.svc.cluster.local has address 10.110.180.222
  host perfect-two
    perfect-two.default.svc.cluster.local has address 10.105.156.42  
```
