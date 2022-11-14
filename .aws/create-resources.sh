#! /bin/bash
awslocal s3 mb s3://appointments
awslocal sqs create-queue --queue-name appointments
