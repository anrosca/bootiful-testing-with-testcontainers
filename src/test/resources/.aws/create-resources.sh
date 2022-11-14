#! /bin/bash

awslocal sqs create-queue --queue-name appointments
aws s3api create-bucket \
    --bucket appointments \
    --region eu-central-1 \
    --create-bucket-configuration LocationConstraint=eu-central-1
