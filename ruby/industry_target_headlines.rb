require 'rest_client'
require 'json'

url = 'https://www.surfwatchanalytics.com:443/v2/summary/headlines/industryTargetHeadlines/daily'

# Get industry targets headlines for June 19th, 2014
header = {
  'content_type' => 'application/json',
  'app_key' => ENV['SURFWATCH_ANALYTICS_APP_KEY'],
  'app_id' => ENV['SURFWATCH_ANALYTICS_APP_ID'],
  params: {
    'startDate' => '2014-06-19', 'endDate' => '2014-06-19'
  }
}
response = RestClient.get(url, header)
results = JSON.parse(response)

# Get industry targets headlines for yesterday for the information technology industry
header[:params] = { 'yesterday' => 'true', 'industryId' => '-7' }
response = RestClient.get(url, header)
results = JSON.parse(response)