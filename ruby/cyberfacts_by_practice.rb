require 'rest_client'
require 'json'

# tag id 122930 is 'unauthorized database access'
url = 'https://www.surfwatchlabs.com:443/api/v3/cyberFacts/containingCyberTag/122930'

header = {
  'content_type' => 'application/json',
  'app_key' => ENV['SURFWATCH_ANALYTICS_APP_KEY'],
  'app_id' => ENV['SURFWATCH_ANALYTICS_APP_ID'],
  params: {
    'startDate' => '2015-06-25',
    'endDate' => '2015-06-25'
  }
}
response = RestClient.get(url, header)
results = JSON.parse(response)
puts results