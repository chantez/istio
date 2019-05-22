
git clone https://github.com/chantez/istio.git

cd service1
mvn clean install -Dmaven.test.skip=true

docker build -t terraformchantez/service1-istio:3.0 .

docker image list
	REPOSITORY                        TAG                 IMAGE ID            CREATED             SIZE
	terraformchantez/service1-istio   3.0                 a4c2489513f7        2 seconds ago       101MB

docker tag terraformchantez/service1-istio:3.0 gcr.io/terraformchantez/service1-istio:3.0
docker push gcr.io/terraformchantez/service1-istio:3.0


If you don't have automatic sidecar injection set in your cluster you will need to manually inject it to the services:
/home/hector_poblete/istio/istio-1.1.5/bin/istioctl kube-inject -f service1.yaml -o service1-istio.yaml

kubectl create -f service1-istio.yaml
kubectl create -f service1-gateway.yaml


export INGRESS_HOST=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.status.loadBalancer.ingress[0].ip}')
cat $INGRESS_HOST
 export INGRESS_PORT=$(kubectl -n istio-system get service istio-ingressgateway -o jsonpath='{.spec.ports[?(@.name=="http2")].port}')
 cat $INGRESS_PORT
 

gcloud compute firewall-rules create allow-gateway-http --allow tcp:$INGRESS_PORT


export GATEWAY_URL=$INGRESS_HOST:$INGRESS_PORT
curl http://$GATEWAY_URL/caller/ping
curl http://$GATEWAY_URL/caller/ping2
curl http://$GATEWAY_URL/caller/ping3  # will failt. Set up hello world. 

---------------------------------------------------------

cd helloworld

/home/hector_poblete/istio/istio-1.1.5/bin/istioctl kube-inject -f helloworld.yaml -o helloworld-istio.yaml

kubectl create -f helloworld-istio.yaml
kubectl create -f helloworld-gateway.yaml # usar solamente en caso que necesite llegar desde afuera.


curl http://$GATEWAY_URL/hello  # tiene que fallar, al menos que corra helloworld-gateway.yaml
curl http://$GATEWAY_URL/caller/ping3