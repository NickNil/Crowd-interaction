<?php

if ($_POST['new'])
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

	$event->insert_event();

	$all_athletes = $event->atheletes();

	echo "<pre>";
	print_r($all_athletes);
	echo "</pre>";

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
		<?php echo $all_athletes[$i]['nationality']; ?>
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


?>


	<h2>Add new event</h2>
	<div class="form-group" style="width: 70%; min-width: 700px;">
		<form action="" method="post" name="event_form">
		<label for="name">Event name</label>
	  	<input type="text" class="form-control" id="name" placeholder="Name" style="width: 50%" name="name">

		<br><label>Gender</label><br>
		<input type="radio" name="gender" value="Men">&nbsp;&nbsp;Men<br>
		<input type="radio" name="gender" value="Women">&nbsp;&nbsp;Women

		<br><br><label>Event</label><br>
		<input type="radio" name="sport" value="figure_skating">&nbsp;&nbsp;Figure Skating<br>
		<input type="radio" name="sport" value="snowboard">&nbsp;&nbsp;Snowboard<br>
		<input type="radio" name="sport" value="ski_jump">&nbsp;&nbsp;Ski Jump

	  	<br><br>
	  	<label for="name">Event start date & time</label>

	    <input type="date" class="form-control" id="date" placeholder="YYYY-MM-DD HH:MM:SS" style="width: 50%" name="datetime">
	  	<br>

			<label for="name">Event location</label>

			<div class="form-inline">
			<input type="number"
			class="form-control"
			placeholder="Latitude"
			style="width: 25%"
			name="latitude"
			value="">

			<input type="number"
			class="form-control"
			placeholder="Longitude"
			style="width: 25%"
			name="longitude"
			value="">
			</div>
			<br>

	 	 <div class="form-inline" style="min-width: 750px;">
	  	<?php


	  	$athletes = array();
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
	      value=""
				style="min-width: 100px; width: 180px;">


	    	    <select class="form-control" style="width: 65px;" name="<?php echo 'athletes['.$i.'][nationality]'; ?>">
	    	    <?php
	    	  foreach ($countries as $country)
					{
						$selected = "";
						if ($country['ioc'] == "NOR")
						{
							$selected = "selected";
						}
						?>
						<option
						name="<?php echo 'athletes['.$i.']'; ?>"
							value="<?php echo $country['ioc']. ',' . $country['iso']; ?>"
							<?php echo $selected; ?>>
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

	    	$button_text = "Insert";
	    	$button_name = "new";
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
