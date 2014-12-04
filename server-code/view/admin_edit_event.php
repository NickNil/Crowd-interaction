<?php

$event_id = $params[2];

if ($_POST['edit'])
{
	$starters 	= $_POST['athletes'];
	$name 			= $_POST['name'];
	$date 			= $_POST['datetime'];
	$gender 		= $_POST['gender'];
	$sport 			= $_POST['sport'];
	$latitude		= $_POST['latitude'];
	$longitude	= $_POST['longitude'];

	include_once('model/event.class.php');
	$event = new Event($database);
	$location = array("latitude" => $latitude, "longitude" => $longitude);
	$event->set_event_info($name, $date, $gender, $sport, $location);

	$starters = array_filter($starters);

	for($i = 1; $i <= count($starters); $i++)
	{
		if ($starters[$i] && $starters[$i]['name'])
		{
			$temp_nationality = explode(",", $starters[$i]['nationality']);

			$ioc = $temp_nationality[0];
			$iso = $temp_nationality[1];

			$nationality = array("ioc" => $ioc, "iso" => $iso);

			$event->add_athelete($i, $starters[$i]['name'], $nationality);
		}
	}

	//echo "<pre>";
	//print_r($event->update_event($event_id));

	$all_athletes = $event->atheletes();

	//print_r($all_athletes);
	//echo "</pre>";

	?>
	<table class="table table-dotted" style="width: 35%;">
	<tr>
	<th>
	Number
	</th>
	<th>
	Name
	</th>
	<th>
	IOC
	</th>
	</tr>
	<?php
	for ($i = 0; $i < count($all_athletes); $i++)
	{
		?>
		<tr>
		<td>
		<?php echo $all_athletes[$i]['athelete_starting_nr']; ?>
		</td>
		<td>
		<?php echo $all_athletes[$i]['athelete_name']; ?>
		</td>
		<td>
		<?php echo $all_athletes[$i]['nationality']['ioc']; ?>
		</td>
		</tr>
		<?php
	}
	?>

	</table>

<?php
}

	  	$url = "http://ci.harnys.net/api/ioc";
	  	$countries = json_decode(file_get_contents($url), true);
	  	$countries = $countries[0]['data'];

			$criteria = array("_id" => new MongoId($event_id));
			$fields = array("_id" => false);
			$event = $database->event->find($criteria, $fields)->limit(1);
			$event = iterator_to_array($event)[0];
			//echo "<pre>";
			//print_r($event);
			//echo "</pre>";
?>


	<h2>Edit event</h2>

	<div class="form-group" style="width: 70%; min-width: 700px;">
		<form action="" method="post" name="event_form">
		<label for="name">Event name</label>
	  	<input
			type="text"
			class="form-control" id="name"
			placeholder="Name"
			style="width: 50%"
			name="name"
			value="<?php echo $event['event_data']['event_name']; ?>">

		<br><label>Gender</label><br>

		<?php
		$gender = array("Women", "Men");
		foreach($gender as $g)
		{
			$checked = "";
			if ($g == $event['event_data']['event_gender'])
			{
				$checked = "checked";
			}
			?>
			<input type="radio" name="gender" value="<?php echo $g; ?>" <?php echo $checked; ?>>&nbsp;&nbsp;<?php echo $g; ?><br>
			<?php
		}
		?>

		<br><br><label>Event</label><br>

		<?php
		$event_types = array(
			"figure_skating" => "Figure Skating",
			"snowboard" => "Snowboard",
			"ski_jump" => "Ski Jump"
		);

		foreach($event_types as $key => $value)
		{
			$checked = "";
			if ($key == $event['event_type'])
			{
				$checked = "checked";
			}
			?>

			<input type="radio" name="sport" value="<?php echo $key; ?>" <?php echo $checked; ?>>
			&nbsp;&nbsp;<?php echo $value; ?><br>
			<?php
		}
		?>

	  	<br><br>
	  	<label for="name">Event start date & time</label>

	    <input type="date"
			class="form-control" id="date"
			placeholder="YYYY-MM-DD HH:MM:SS"
			style="width: 50%"
			name="datetime"
			value="<?php echo $event['event_date']; ?>">

			<br>
			<label for="name">Event location</label><br>

			<?php
			$latitude = $event['event_data']['event_location']['latitude'];
			$longitude = $event['event_data']['event_location']['longitude'];
			?>
			<img src="https://maps.googleapis.com/maps/api/staticmap?center=<?php echo $latitude . "," . $longitude; ?>&zoom=13&size=500x300&maptype=roadmap
		&markers=color:green%7Clabel:HERE%7C<?php echo $latitude . "," . $longitude; ?>">
			<br><br>
			<div class="form-inline">
			<input type="number"
			class="form-control"
			placeholder="Latitude"
			style="width: 25%"
			name="latitude"
			value="<?php echo $event['event_data']['event_location']['latitude']; ?>">

			<input type="number"
			class="form-control"
			placeholder="Longitude"
			style="width: 25%"
			name="longitude"
			value="<?php echo $event['event_data']['event_location']['longitude']; ?>">
			</div>
			<br>


	 	 <div class="form-inline" style="min-width: 750px;">
	  	<?php


	  	$athletes = array();
			$current_athletes =  $event['event_data']['event_athelete_list'];
	  	for($i = 1; $i <= 51; $i++)
	  	{
	  		?>
	    <div class="input-group">
	      <div class="input-group-addon"
	      style="background: #0099cc; color: #FFFFFF; font-weight: 900;">
	      <?php if ($i < 10) { echo  "&nbsp;&nbsp;"; }; echo $i; ?></div>
	      <input
	      class="form-control"
	      type="text"
	      placeholder="Athelete name"
	      name="<?php echo 'athletes['.$i.'][name]'; ?>"
	      value="<?php echo $current_athletes[$i-1]['athelete_name']; ?>"
				style="min-width: 100px; width: 180px;">


	    	    <select class="form-control" style="width: 65px;" name="<?php echo 'athletes['.$i.'][nationality]'; ?>">
	    	    <?php
	    	  foreach ($countries as $country)
					{
						$selected = "";
						if ($country['ioc'] == $current_athletes[$i-1]['nationality']['ioc'] ||
						$country['ioc'] == $current_athletes[$i-1]['nationality']['ioc'])
						{
							$selected = "selected";
						}
						?>
						<option
						name="<?php echo 'athletes['.$i.']'; ?>"
							value="<?php echo $country['ioc']. ',' . $country['iso']; ?>" <?php echo $selected; ?>>
							<?php echo $country['ioc'];?>
						</option>
						<?php
					}
	    	  ?>
				</select>
			</div>
		 		<?php
	  	}
	  	?>

		</div>

	    <?php

	    	$button_text = "Edit";
	    	$button_name = "edit";
	      /*
	    	if (count($starters) > 0)
	    	{
		    	$button_text = "Confirm";
		    	$button_name = "confirm";
	    	}*/

	    ?>
			<br><br>
	  <button type="submit"
	  class="btn btn-success"
	  value="submit"
	  name="<?php echo $button_name; ?>"
	  style="color: #FFFFFF; font-weight: 900;"><?php echo $button_text; ?></button>
	</form>
</div>
