echo
echo '-PODS-\n'
kubectl get pods -o wide

echo

echo '-SERVICES-\n'
kubectl get svc -o wide

echo 

echo '-INGRESS-\n'
kubectl get ingress -o wide

echo

echo '-NODES-\n'
kubectl get nodes -o wide
echo
