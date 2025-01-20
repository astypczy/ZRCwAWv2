# Kolejka SQS: message-queue
resource "aws_sqs_queue" "message_queue" {
  name                      = "message-queue"
  delay_seconds             = 0
  message_retention_seconds = 86400
}

# Kolejka SQS: admin-queue
resource "aws_sqs_queue" "admin_queue" {
  name                      = "admin-queue"
  delay_seconds             = 0
  message_retention_seconds = 86400
}

# Funkcja Lambda
resource "aws_lambda_function" "message_processor" {
  filename         = "lambda_function.zip"                              # Plik ZIP z kodem
  function_name    = "message_processor"
  role             = "arn:aws:iam::637423541704:role/LabRole"    # ARN roli IAM
  handler          = "lambda_function.lambda_handler"            # Handler
  runtime          = "python3.10"                                # Wersja Pythona
  timeout          = 10                                          # Timeout funkcji
  memory_size      = 128                                         # Rozmiar pamięci
  publish          = true

  # Zmienne środowiskowe
  environment {
    variables = {
      QUEUE_URL       = aws_sqs_queue.message_queue.id           # URL message-queue
      ADMIN_QUEUE_URL = aws_sqs_queue.admin_queue.id             # URL admin-queue
    }
  }
}

# Powiązanie Lambdy z kolejką message-queue (trigger)
resource "aws_lambda_event_source_mapping" "sqs_trigger" {
  event_source_arn = aws_sqs_queue.message_queue.arn             # Źródło: message-queue
  function_name    = aws_lambda_function.message_processor.arn   # Funkcja Lambda
  batch_size       = 1                                           # Jedna wiadomość na raz
  enabled          = true
}

data "aws_lambda_function" "lambda_latest_version" {
  function_name = aws_lambda_function.message_processor.function_name
}

# Konfiguracja wywoływania zdarzeń Lambdy
resource "aws_lambda_function_event_invoke_config" "lambda_config" {
  function_name                  = aws_lambda_function.message_processor.function_name
  maximum_retry_attempts         = 2                            # Liczba prób w razie błędu
  maximum_event_age_in_seconds   = 60                           # Maksymalny czas na przetworzenie zdarzenia
}

# Ograniczenie równoczesności Lambdy (do 2 instancji)
resource "aws_lambda_provisioned_concurrency_config" "concurrency" {
  function_name                     = aws_lambda_function.message_processor.function_name
  qualifier                         = data.aws_lambda_function.lambda_latest_version.version # Użycie najnowszej wersji
  provisioned_concurrent_executions = 2
}