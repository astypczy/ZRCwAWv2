# 1. Tworzenie kolejki SQS
resource "aws_sqs_queue" "messages_queue" {
  name                        = "messages-queue"
  visibility_timeout_seconds  = 10  
  message_retention_seconds   = 86400  
  max_message_size            = 262144  
  receive_wait_time_seconds   = 0
  redrive_policy = jsonencode({
    deadLetterTargetArn = aws_sqs_queue.dlq_queue.arn
    maxReceiveCount     = 5
  })
}

resource "aws_sqs_queue" "dlq_queue" {
  name = "messages-dlq"
}

resource "aws_sqs_queue" "admin_queue" {
  name = "admin-queue"
}

resource "aws_iam_role" "lambda_execution_role" {
  name = "lambda_execution_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Action = "sts:AssumeRole",
        Effect = "Allow",
        Principal = {
          Service = "lambda.amazonaws.com"
        }
      }
    ]
  })
}

# Dodanie polityk do roli Lambda
resource "aws_iam_policy_attachment" "lambda_execution_policy" {
  name       = "lambda_execution_policy"
  roles      = [aws_iam_role.lambda_execution_role.name]
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole"
}

resource "aws_iam_role_policy" "lambda_sqs_access" {
  name = "lambda_sqs_access"

  role = aws_iam_role.lambda_execution_role.id
  policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        Effect = "Allow",
        Action = [
          "sqs:ReceiveMessage",
          "sqs:DeleteMessage",
          "sqs:GetQueueAttributes",
          "sqs:SendMessage"
        ],
        Resource = [
          aws_sqs_queue.messages_queue.arn,
          aws_sqs_queue.dlq_queue.arn
        ]
      }
    ]
  })
}

# 3. Funkcja Lambda
resource "aws_lambda_function" "process_messages" {
  filename         = "lambda_function.zip" # Plik ZIP zawierający kod Lambdy
  function_name    = "process_messages_lambda"
  role             = aws_iam_role.lambda_execution_role.arn
  handler          = "lambda_function.lambda_handler"
  runtime          = "python3.9"
  timeout          = 15  # Maksymalny czas wykonania Lambdy (w sekundach)
  memory_size      = 128
  reserved_concurrent_executions = 2  # Maksymalna liczba równoczesnych instancji

  environment {
    variables = {
      SOURCE_QUEUE_URL = aws_sqs_queue.messages_queue.id
      ADMIN_QUEUE_URL  = aws_sqs_queue.admin_queue.id
    }
  }
}

# 4. Wyzwalacz SQS dla Lambdy
resource "aws_lambda_event_source_mapping" "sqs_trigger" {
  event_source_arn  = aws_sqs_queue.messages_queue.arn
  function_name     = aws_lambda_function.process_messages.arn
  batch_size        = 1  # Maksymalnie jedna wiadomość na raz
  enabled           = true
}