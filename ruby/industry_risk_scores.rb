require 'rest_client'
require 'json'

url = 'https://www.surfwatchanalytics.com:443/v2/industryRiskScores'

# Get industry risk scores from yesterday
header = {
  'content_type' => 'application/json',
  'app_key' => ENV['SURFWATCH_ANALYTICS_APP_KEY'],
  'app_id' => ENV['SURFWATCH_ANALYTICS_APP_ID'],
  :params => { 'yesterday' => 'true' }
}
response = RestClient.get(url, header)
results = JSON.parse(response)

# Get entertainment industry risk scores from June 14th, 2014
header[:params] = {
  'startDate' => '2014-06-14',
  'endDate' => '2014-06-14',
  'industryId' => '-1'
}
response = RestClient.get(url, header)
results = JSON.parse(response)