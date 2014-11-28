<?php

class Event {

	private $database;
	private $event_id;
	private $atheletes = array();
	private $event_name;
	private $event_date;
	private $gender;
	private $event_type;
	private $event_location;


	function __construct($db)
	{
		$this->database = $db;
		$this->event_id = 0;
	}


	function set_event_info($name, $date, $gender, $type, $location)
	{

	  if (self::validate_date($date))
	  {
			$this->event_date 	= $date;
			$this->event_name 	= $name;
			$this->gender 			= $gender;
			$this->event_type 	= $type;
			$this->location 		= $location;
		}
		else
		{
			?>
			<div class="alert alert-danger" role="alert"><b>Error!</b> Invalid date and/or time</div>
			<?php
		}
	}


	function add_athelete($id, $name, $nationality)
	{
		array_push($this->atheletes, array("athelete_starting_nr" => $id, "athelete_name" => $name, "nationality" => $nationality));
	}


	function atheletes()
	{
			return $this->atheletes;
	}


	private function insert_athelete_in_event($event_id, $count, $starting_nr, $name)
	{
	  $events = $this->database->event;

		$start_number_exists = $events->findOne(array("event_data.event_athelete_list.athelete_starting_nr" => (int)$starting_nr));

		if ($start_number_exists != NULL)
		{
			echo "Athelete start number already exists";
			return;
		}

		$count = $count+1;

		$athelete = array('$set' =>
		array("event_data.event_athelete_list."  . $count . ".athelete_name" => $name,
					"event_data.event_athelete_list." . $count . ".athelete_starting_nr" => (int)$starting_nr));

		try
		{
    	//$this->database->event->update(array("event_id" => (int)$event_id), $athelete);
		}
		catch(MongoCursorException $e)
		{
    		echo "Starting number is already taken";
		}



	}


	function update_event($id)
	{

		$data = new stdClass;
		$data->event_date = $this->event_date;
		$data->event_type = $this->event_type;

		$data->event_data = array(
		"event_name" => $this->event_name,
		"event_gender" => $this->gender,
		"event_athelete_list" => $this->atheletes,
		"event_location" => $this->location);

		$event = $this->database->event;

		$event->update(array("_id" => new MongoId($id)), $data);

		return $this->database->lastError();
	}


	function insert_event()
	{
		$data = new stdClass;

		$data->event_date = $this->event_date;
		$data->event_type = $this->event_type;

		$data->event_data = array(
		"event_name" => $this->event_name,
		"event_gender" => $this->gender,
		"event_athelete_list" => $this->atheletes,
		"event_location" => $this->location);

		$event = $this->database->event;
		$event->insert($data);
	}



	private function validate_date($date, $format = 'Y-m-d H:i:s')
	{
    	$d = DateTime::createFromFormat($format, $date);
    	return $d && $d->format($format) == $date;
	}

}


?>
