require 'rest_client'
require 'json'

url = 'https://www.surfwatchlabs.com:443/api/v3/cyberFacts/softwareVulnerabilities'

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