#!/bin/bash

delete_service()
{
  kubectl delete svc $1
}


output=$(kubectl get svc)

for token in $output
do 
  if [[ $token == *"text-detection"* ]];  
  then 
    delete_service $token
  elif [[ $token == *"template-recognition"* ]];
  then
    delete_service $token
  fi
done
