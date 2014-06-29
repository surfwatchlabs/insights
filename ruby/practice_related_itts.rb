require 'rest_client'
require 'json'

practice_id = '3025' # Distributed denial-of-service
url = "https://www.surfwatchanalytics.com:443/v2/cyberTags/currentAnalytics/practiceITTs/#{ practice_id }"

# Tags about distributed denial-of-service from yesterday
header = {
  'content_type' => 'application/json',
  'app_key' => ENV['SURFWATCH_ANALYTICS_APP_KEY'],
  'app_id' => ENV['SURFWATCH_ANALYTICS_APP_ID'],
  params: {
    'yesterday' => true
  }
}
response = RestClient.get(url, header)
results = JSON.parse(response)