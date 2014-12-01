<?php

$event_id = 0;
if (isset($params[2]) && strlen($params[2]) == 24)
{
	$event_id = $params[2];
	$mongo_id = new MongoId($event_id);
	
	$criteria = array('_id' => $mongo_id);
	$fields = array("event_date" => 0, "event_id" => 0); 
	
	$res = $database->event->find($criteria, $fields)->limit(1);
	$event = iterator_to_array($res);
	$event = $event[$mongo_id->{'$id'}];	


	$data = array();
	if ($event['event_live'] == "true")
	{
		$status = array("live" => "1");
		$data[] = $status;
	}
	else
	{
		print_r(json_encode(array("param" => "live", "data" => "404")));
		return;
	}
	
	$athletes = $event['event_data']['event_athelete_list'];

	for ($i = 0; $i < count($athletes); $i++)
	{
		if ($athletes[$i]['live'] == "1")
		{
			$athlete = array(
			"name" => $athletes[$i]['athelete_name'], 
			"starting_number" => $athletes[$i]['athelete_starting_nr'], 
			"nationality" => $athletes[$i]['nationality']);
			
			$athlete_data = array("athlete_data" => $athlete);
			$data[] = $athlete_data;
			break;
		}
	}
	
	$response = array("param" => "live", "data" => $data);
	
	print_r(json_encode(array($response)));
}
else
{
	$criteria = array('event_live' => "true",  "event_data.event_athelete_list.live" => 1);
	$fields = array("event_date" => 0, "event_id" => 0); 
	$res = $database->event->find($criteria, $fields);

	$events = array();
	foreach ($res as $event)
	{	
$event_data = array("_id" => $event['_id']->{'$id'}, "event_name" => $event['event_data']['event_name'], "event_type" => $event['event_type'], "event_gender" => $event['event_data']['event_gender']);
		$current_athlete = NULL;
		foreach ($event['event_data']['event_athelete_list'] as $athlete)
		{
			if ($athlete['live'])
			{
				$current_athlete = $athlete;
				
				$name = $athlete['athelete_name'];
				$number = $athlete['athelete_starting_nr'];
				$nationality = $athlete['nationality'];				
				$current_athlete = array("name" => $name, "number" => $number, "nationality" => $nationality);
				
				$event_data["current_athlete"] = $current_athlete;
				$events[] = $event_data;
				
			}
		}
	}
	
	print_r(json_encode(array(array("param" => "current", "data" => $events))));

}

?>