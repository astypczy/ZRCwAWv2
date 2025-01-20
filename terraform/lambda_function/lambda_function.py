import os
import json
import time
import re
import boto3

# Inicjalizacja klienta SQS
sqs = boto3.client('sqs')

# Pobranie URL-i kolejek z zmiennych środowiskowych
QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/637423541704/message-queue"
ADMIN_QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/637423541704/admin-queue"

def lambda_handler(event, context):
    for record in event['Records']:
        message_body = record['body']
        print(f"Received message: {message_body}")

        # Wprowadzenie 5-sekundowego opóźnienia
        time.sleep(5)

        # Sprawdzenie, czy wiadomość zawiera liczbę
        if re.search(r'\d', message_body):
            print(f"Message contains a number. Sending to admin queue.")
            
            # Przesłanie wiadomości do kolejki admina
            sqs.send_message(
                QueueUrl=ADMIN_QUEUE_URL,
                MessageBody=message_body
            )
        else:
            print("Message does not contain a number. Processing complete.")
    return {"statusCode": 200, "body": "Processing complete"}
