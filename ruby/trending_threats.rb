require 'rest_client'
require 'json'

url = 'https://www.surfwatchlabs.com:443/api/v3/summary/tagTrend/monthly'

header = {
  'content_type' => 'application/json',
  'app_key' => ENV['SURFWATCH_ANALYTICS_APP_KEY'],
  'app_id' => ENV['SURFWATCH_ANALYTICS_APP_ID'],
  params: {
    'date' => '2015-06-30',
    'tagSuperTypeId' => 3,
    'feedId' => -3
  }
}
response = RestClient::Request.execute(method: :get, url: url, headers: header, timeout: 300)
results = JSON.parse(response)
puts results