<?php


//include_once('style.html');

//include_once('style.html');

$events = $database->event;

$event = NULL;

if ($event_id > 0)
{
 	// If not a date, it is an int
	$fields = array("_id" => false, "event_id" => false);

	if (!strtotime($event_id))
	{
		$criteria = array('_id' => new MongoId($event_id));
		$event = $events->find($criteria, $fields)->limit(1);
	}
	else
	{
		// Date given, search for date
		$event_date = $event_id;
		$criteria = array("event_date" => new MongoRegex("/^".$event_date."/"));
		$event = $events->find($criteria, $fields)->limit(1);
	}

	$event = iterator_to_array($event);
	$event = $event[0];


	if (strtotime($event_id))
	{
		?>

		<h3>Events on <?php echo date("d.m.Y", strtotime($event_id)); ?></h3>
 		<table class="table table-dotted" style="width: 100%;">
 		<tr>
    	<th style="width: 50%;">Time</th>
    	<th style="width: 50%;">Name</th>
  	</tr>
	<?php
	foreach ($event as $event_data)
	{
		$temp_event_id		  = $event_data['_id']->{'$id'};
		$temp_event_date 		= $event_data['event_date'];
		$temp_event_name 		= $event_data['event_data']['event_name'];
		?>

		<tr onclick="window.location.href = '/event/<?php echo $temp_event_id ;?>';">

		<?php
   	$temp_event_date = date("H:i:s", strtotime($temp_event_date));
		?>

		<td><?php echo $temp_event_date; ?></td><td><?php echo $temp_event_name; ?></td>
  	</tr>
	<?php
	}
	?>
	</table>

	<?php

	}
	else
	{
		?>
		<h3 style="width: 100%;">
		<?php echo $event['event_data']['event_name']; ?> @
		<?php
		$temp_event_date = $event['event_date'];
		$temp_event_date = date("d.m.Y H:i:s", strtotime($temp_event_date));
		echo $temp_event_date;
		?>
		<div style="float: right;">
			<?php
				$status = "Start";

				if ($event['event_live'])
				{
					$status = "Stop";
				}
			?>
		<a href="../admin/status/<?php echo $event_id; ?>"><button type="button" class="btn btn-warning"><?php echo $status ;?></button></a>
		<a href="../admin/edit/<?php echo $event_id; ?>"><button type="button" class="btn btn-info">Edit</button></a>
		<a href="../admin/delete/"><button type="button" class="btn btn-danger">Delete</button></a>
		<?php
		if ($event['event_live'])
		{
			?>
			<a href="../admin/next/<?php echo $event_id; ?>"><button type="button" class="btn btn-success">Start next athlete</button></a>
			<?php
		}
		?>
		</div>
		</h3>


	 	<table class="table table-striped" style="width: 100%;">
	  <tr>
	    <th style="width: 10%;">#</th>
			<th style="width: 30%;">IOC</th>
	    <th style="width: 40%;">Name</th>
	    <th style="width: 10%;">Scored</th>
			<th style="width: 10%;">Live</th>
	  </tr>
		<?php

		$score = $database->judgescore;

	  $athelete_list = $event['event_data']['event_athelete_list'];
	  foreach ($athelete_list as $athelete)
	  {
	  		$criteria = array('_id' => new MongoId($event_id), "score.athelete_nr" => array('$in' => array((string)$athelete['athelete_starting_nr'])));
				$fields = array("_id" => false);
	  		$result = $score->find($criteria, $fields)->limit(1);
				$result = iterator_to_array($result);
				$result = $result[0];

				// IF NOT SCORED LET TR BE CLICKABLE
				if (!$result)
				{
					?>
		  		<tr onclick="window.location.href = '/score/<?php echo $event_id;?>/<?php echo $athelete['athelete_starting_nr'];?>';">
		  		<?php
				}
				else
				{
					?>
					<tr onclick="window.location.href = '/score/view/<?php echo $event_id;?>/<?php echo $athelete['athelete_starting_nr'];?>';">
					<?php
				}
		  		?>
		  		<td><?php echo $athelete['athelete_starting_nr']; ?></td>
					<td>
						<img style="margin-top: -5px;" src="../gfx/flag/<?php echo $athelete['nationality']['iso'];?>.png">
						<?php echo $athelete['nationality']['ioc']; ?>
					</td>
					<td><?php echo $athelete['athelete_name']; ?></td>

		  		<td style="text-align: center; color: #000000">
					<?php
					if ($result)
					{
						?>
						<span class="glyphicon glyphicon-ok"></span>
						<?php
					}
					?>
		  		</td>
					<td>
						<?php
						if ($athelete['live'])
						{
							echo "X";
						}
						?>
					</td>
		  		</tr>
		  <?php
	  }
		?>
		<tr onclick="window.location.href = '/event';"><td colspan="5" style="text-align: center; font-weight: 900;">Back</td></tr>

	  </table>
<?php
	}
}
else
{
	$event = $events->find()->sort(array('event_date' => 1))

?>
	<h3>Events</h3>
 	<table class="table table-dotted" style="width: 60%;">
  <tr>
    <th style="width: 50%;">Date</th>
    <th style="width: 50%;">Name</th>
  </tr>
<?php
	foreach ($event as $event_data)
	{

		$temp_event_id		  = $event_data['_id']->{'$id'};
		$temp_event_date 		= $event_data['event_date'];
		$temp_event_name 		= $event_data['event_data']['event_name'];
		?>

		<tr onclick="window.location.href = '/event/<?php echo $temp_event_id ;?>';">

		<?php
   	$temp_event_date = date("d.m.Y H:i:s", strtotime($temp_event_date));
		?>

		<td><?php echo $temp_event_date; ?></td><td><?php echo $temp_event_name; ?></td>
  	</tr>
	<?php
	}
	?>
	</table>
	<?php

}



?>
