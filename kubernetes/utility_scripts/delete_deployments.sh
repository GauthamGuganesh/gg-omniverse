#!/bin/bash

delete_deployment()
{
  kubectl delete deployments $1
}


output=$(kubectl get deployments)

for token in $output
do 
  if [[ $token == *"text-detection"* ]];  
  then 
    delete_deployment $token
  elif [[ $token == *"template-recognition"* ]];
  then
    delete_deployment $token
  fi
done
