require 'rest_client'
require 'json'

class MacroTrendDelta
  URL = 'https://www.surfwatchlabs.com:443/api/v3/summary/macroTrendDelta/monthly'
  HEADER = {
      'content_type' => 'application/json',
      'app_key' => ENV['SURFWATCH_ANALYTICS_APP_KEY'],
      'app_id' => ENV['SURFWATCH_ANALYTICS_APP_ID']
  }

  def query start_dt, end_dt
    header = HEADER.merge(params: { 'startDate' => start_dt, 'endDate' => end_dt })
    res = RestClient.get(URL, header)
    JSON.parse(res)
  end

  def macro_results start_dt, end_dt, macro_id
    hash = query(start_dt, end_dt)
    hash.select {|md| md['macro_tag_id'] == macro_id }.first
  end
end

mtd = MacroTrendDelta.new
result = mtd.macro_results('2015-05-01', '2015-05-07', -238)

# query again and add deltas
result['deltas'].concat( mtd.macro_results('2015-05-08', '2015-05-14', -238)['deltas'] )
result['deltas'].concat( mtd.macro_results('2015-05-15', '2015-05-21', -238)['deltas'] )
result['deltas'].concat( mtd.macro_results('2015-05-22', '2015-05-28', -238)['deltas'] )
result['deltas'].concat( mtd.macro_results('2015-05-29', '2015-05-31', -238)['deltas'] )

# create a chart using the json, or serialize to csv and use that
puts result

