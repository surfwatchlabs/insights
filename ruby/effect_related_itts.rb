require 'rest_client'
require 'json'

effect_id = '18786' # Compromised website
url = "https://www.surfwatchanalytics.com:443/v2/cyberTags/currentAnalytics/effectITTs/#{ effect_id }"

# Tags about compromised websites from yesterday
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