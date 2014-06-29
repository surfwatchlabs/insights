require 'rest_client'
require 'json'

url = "https://www.surfwatchanalytics.com:443/v2/industryTargetSummaries"
header = {
  'content_type' => 'application/json',
  'app_key' => ENV['SURFWATCH_ANALYTICS_APP_KEY'],
  'app_id' => ENV['SURFWATCH_ANALYTICS_APP_ID'],
  params: {
    'yesterday' => true,
    'market' => 'NYSE'
  }
}
response = RestClient.get(url, header)
results = JSON.parse(response)