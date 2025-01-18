import boto3
import time
import re
import os

# Inicjalizacja klienta SQS
sqs = boto3.client('sqs')

# Pobranie zmiennych środowiskowych (URL kolejek)
SOURCE_QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/637423541704/messages_queue"  # Kolejka wejściowa
ADMIN_QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/637423541704/admin_queue"    # Kolejka admina

def lambda_handler(event, context):
    # Pobranie wiadomości z kolejki
    for record in event['Records']:
        message_body = record['body']

        # Opóźnienie 5 sekund
        time.sleep(5)

        # Sprawdzenie, czy treść zawiera liczbę
        if re.search(r'\d', message_body):
            # Wysłanie kopii wiadomości do kolejki admina
            sqs.send_message(
                QueueUrl=ADMIN_QUEUE_URL,
                MessageBody=message_body
            )
        print(f"Processed message: {message_body}")

    return {
        'statusCode': 200,
        'body': 'Message processed successfully'
    }

