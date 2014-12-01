<?php

include_once('model/event.class.php');

include_once('model/mongoconnection.php');

$mongo = new MongoConnection();
$database = $mongo->database();

$event = new Event($database);

$events = $database->event;

$event_id = 1;

$count = count($events->findOne(array('event_id' => (int)$event_id)));

$start_nr = 6;

print_r($event->insert_athelete_in_event($event_id, $count, $start_nr, "Malin Stalin"));

?>