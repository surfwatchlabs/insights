require 'rest_client'
require 'json'

actor_id = '1103' # Anonymous
url = "https://www.surfwatchanalytics.com:443/v2/cyberTags/currentAnalytics/actorITTs/#{ actor_id }"

# Tags about the hacktivist group "Anonymous" from June 13th and 14th
header = {
  'content_type' => 'application/json',
  'app_key' => ENV['SURFWATCH_ANALYTICS_APP_KEY'],
  'app_id' => ENV['SURFWATCH_ANALYTICS_APP_ID'],
  params: {
    'startDate' => '2014-06-13',
    'endDate' => '2014-06-14'
  }
}
response = RestClient.get(url, header)
results = JSON.parse(response)