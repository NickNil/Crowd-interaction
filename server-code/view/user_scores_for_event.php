<?php
include_once('app-api/constants.php');

// Fix type-o
$athlete_id = $athelete_id;

if ($athlete_id > 0 || isset($event_id))
{

	if (1 == 2)
	{
		ini_set('display_errors',1);
		ini_set('display_startup_errors',1);
		error_reporting(-1);
	}

	$criteria = array("_id" => new MongoId($event_id));

	$fields = array("_id" => false);
	$scores = iterator_to_array($database->userscore->find($criteria, $fields))[0];
	$scores = $scores['scores'];

	$title = "";
	$criteria = array("_id" => new MongoId($event_id));
	$event_data = iterator_to_array($database->event->find($criteria, $fields)->limit(0));
	$event_data = $event_data[0]["event_data"];
	$title = "<h2>Scores</<h2><h3>". $event_data["event_name"] . " for " . $event_data["event_gender"]. "</h3>";

	//$criteria = array("_id" => new MongoId($event_id), "event_data.event_athelete_list.athelete_starting_nr" => array('$in' => array((string)$athlete_id)));
	//print_r($criteria);
	//$temp_result = iterator_to_array($database->event->find($criteria, $fields)->limit(1));
	//print_r($temp_result);

	if ($athlete_id != 0 && isset($athlete_id))
	{
		foreach ($event_data["event_athelete_list"] as $athlete)
		{
			if ((int)$athlete["athelete_starting_nr"] == (int)$athlete_id)
			{
				$title = "<h2>Scores</<h2><h3>". $athlete["athelete_name"] . " in " . $event_data["event_name"] . " for " . $event_data["event_gender"]. "</h3>";
				break;
			}
		}
	}

	?>

	<?php echo $title; ?>
	 <table class="table table-dotted" style="width: 100%;">
	  <tr>
	    <th style="width: 33%;">Phone</th>
	    <th style="width: 33%;">Points</th>
	    <th style="width: 33%;">Athlete</th>
	  </tr>

	<?php
	if ($athlete_id != 0 && isset($athlete_id))
	{
		foreach($scores as $score)
		{
			if ($athlete_id == $score[STARTING_NUMBER])
			{
			?>
				<tr>
				<td><?php echo $score[PHONE_NUMBER]; ?></td>
				<td><?php echo $score[SCORE]; ?></td>
				<td><?php echo $score[STARTING_NUMBER]; ?></td>
				</tr>
				<?php
			}
		}
	}
	else
	{
		foreach($scores as $score)
		{
			?>
			<tr>
			<td><?php echo $score[PHONE_NUMBER]; ?></td>
			<td><?php echo $score[SCORE]; ?></td>
			<td><?php echo $score[STARTING_NUMBER]; ?></td>
			</tr>
			<?php
		}
	}
	?>
	</table>
	<a href="../scores"><button type="button" class="btn btn-warning">Back</button></a>

<?php
}
else
{
	$criteria = array();
	$fields = array();
	$scores = iterator_to_array($database->userscore->find($criteria, $fields));

	?>
	<h3>Events</h3>
	<table class="table table-dotted" style="width: 100%;">
	<tr>
		<th style="width: 50%;">Date</th>
		<th style="width: 50%;">Name</th>
	</tr>
	<?php

	foreach ($scores as $score)
	{
		$temp_criteria = array("_id" => new MongoId($score['_id']));
		$temp_fields = array();
		$event = $database->event->find($temp_criteria, $temp_fields)->limit(1);

		foreach ($event as $event_data)
		{

			$temp_event_id		  = $event_data['_id']->{'$id'};
			$temp_event_date 		= $event_data['event_date'];
			$temp_event_name 		= $event_data['event_data']['event_name'];
			?>

			<tr onclick="window.location.href = '/scores/<?php echo $temp_event_id ;?>';">

			<?php
			$temp_event_date = date("d.m.Y H:i:s", strtotime($temp_event_date));
			?>

			<td><?php echo $temp_event_date; ?></td><td><?php echo $temp_event_name; ?></td>
			</tr>
		<?php
		}
	}
	?>
	</table>
	<?php
}
