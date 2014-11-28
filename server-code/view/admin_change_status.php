<?php
$criteria = array('_id' => new MongoId($event_id));
$fields = array("_id" => false, "event_id" => false);
$event = $database->event->find($criteria, $fields)->limit(1);
$event = iterator_to_array($event);
$event = $event[0];

if ($event['event_live'])
{
  unset($event['event_live']);

  $athletes = $event['event_data']['event_athelete_list'];

  for ($i = 0; $i < count($athletes); $i++)
  {
      unset($athletes[$i]['live']);
  }

  $event_name = $event['event_data']['event_name'];
  $event_gender = $event['event_data']['event_gender'];
  $event_location = $event['event_data']['event_location'];
  $event['event_data'] = array(
    "event_athelete_list" => $athletes,
    "event_name" => $event_name,
    "event_gender" => $event_gender,
    "event_location" => $event_location);
}
else
{
  $event['event_live'] = "true";
  $event['event_data']['event_athelete_list'][0]['live'] = 1;
}
$database->event->update($criteria, $event);
header('location: ../../event/'.$event_id);

?>
