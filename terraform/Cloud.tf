resource "aws_cloudwatch_metric_alarm" "sqs_messages_alarm" {
  alarm_name          = "SQSHighMessageCount"
  comparison_operator = "GreaterThanThreshold"
  evaluation_periods  = 1
  metric_name         = "ApproximateNumberOfMessagesDelayed"
  namespace           = "AWS/SQS"
  period              = 300
  statistic           = "Sum"
  threshold           = 10

  dimensions = {
    QueueName = aws_sqs_queue.messages_queue.name
  }
}
resource "aws_cloudtrail" "cloudtrail" {
  name                          = "my-cloudtrail"
  s3_bucket_name                = aws_s3_bucket.cloudtrail_bucket.id
  include_global_service_events = true
  is_multi_region_trail         = true
}
