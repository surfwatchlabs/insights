require 'rest_client'
require 'json'

url = 'https://www.surfwatchanalytics.com:443/v2/summary/tagTrend/monthly'

# Get a CyberTag trend values, computed on a monthly interval.
header = {
  'content_type' => 'application/json',
  'app_key' => ENV['SURFWATCH_ANALYTICS_APP_KEY'],
  'app_id' => ENV['SURFWATCH_ANALYTICS_APP_ID'],
  params: {
    'month' => '2014-05-10', 'tagSuperTypeId' => 3, 'industryId' => -13
  }
}
response = RestClient.get(url, header)
results = JSON.parse(response)