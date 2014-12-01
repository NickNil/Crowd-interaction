<?php

if (isset($params[2]))
{
	$event_id = $params[2];
}

$events = $database->event;

$event = NULL;

if ($event_id > 0)
{
 	// If not a date, it is an int or mongoId
	if (!strtotime($event_id))
	{
		if (strlen($event_id) == 24)
		{
			$event = $events->find(array('_id' => new MongoId($event_id)), array("event_data.event_athelete_list" => 0))->limit(1);
		}
		else
		{
			$event = $events->find(array('event_id' => (int)$event_id), array("event_data.event_athelete_list" => 0))->limit(1);
		}
		if ($event != null)
		{
			$event = iterator_to_array($event);
		}
	}
	else
	{
		// Date given, search for date
		$event_date = $event_id;
		$event = $events->find(array("event_data.event_date" => new MongoRegex("/^".$event_date."/")), array("event_data.event_athelete_list" => 0, "event_id" => 0));
		if ($event != null)
		{
			$event = iterator_to_array($event);
		}
	}

}
else
{
	$event = $events->find(array(), array("event_id" => 0))->sort(array('event_date' => 1));
	if ($event != null)
	{
		$event = iterator_to_array($event);
	}
}

//echo "<pre>";
//print_r(array("param" => "e", "data" => $event));
//echo "</pre>";

print_r(json_encode(array(array("param" => "e", "data" => $event))));

?>
