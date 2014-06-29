require 'rest_client'
require 'json'

url = 'https://www.surfwatchanalytics.com:443/v2/topTrendingTags'

# Get all top trending tags
header = {
  'content_type' => 'application/json',
  'app_key' => ENV['SURFWATCH_ANALYTICS_APP_KEY'],
  'app_id' => ENV['SURFWATCH_ANALYTICS_APP_ID']
}
response = RestClient.get(url, header)
results = JSON.parse(response)

# Get trending tags for the information technology industry
header[:params] = { 'industryId' => '-7' }
response = RestClient.get(url, header)
results = JSON.parse(response)