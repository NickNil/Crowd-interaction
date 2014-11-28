<?php
// Fix the type-o
$athlete_id = $athelete_id;

ini_set('display_errors',1);
ini_set('display_startup_errors',1);
error_reporting(E_ALL ^ E_NOTICE);

include_once("score_calculation.php");
include_once("./push/ios/push_score.php");
include_once("./push/android/gmc.php");


function update_user_score($database, $mongo_id, $points)
{
	$criteria = array("_id" => new MongoId($mongo_id));
	$fields = array("highscore" => true, "_id" => false);
	$current_highscore = iterator_to_array($database->user->find($criteria, $fields)->limit(1));
	$current_highscore = $current_highscore[0]['highscore'];

	$current_highscore += $points;

	$update = array('$set' => array("highscore" => $current_highscore));
	$database->user->update($criteria, $update);

	return $current_highscore;
}


function calculate_user_scores($database, $mongo_id, $starting_no)
{
	echo "<pre>";

	$criteria = array("_id" => new MongoId($mongo_id));
	$fields = array("_id" => false, "event_data" => false, "event_date" => false);
	$information = iterator_to_array($database->event->find($criteria, $fields)->limit(1));

	$event_type = $information[0]['event_type'];
	$criteria = array("event_type" => $event_type);
	$fields = array("_id" => false);
	$meta = iterator_to_array($database->meta->find($criteria, $fields)->limit(1));
	$meta_points = $meta[0]['points'];

	$criteria = array("_id" => new MongoId($mongo_id));
	$fields = array("scores" => true, "_id" => false);
	$result = iterator_to_array($database->userscore->find($criteria, $fields));

	$user_scores = $result[0]['scores'];

	for ($i = 0; $i < count($user_scores); $i++)
	{
		if ($user_scores[$i]['start_number'] != $starting_no)
		{
			unset($user_scores[$i]);
		}
	}
	$user_scores = array_values(array_filter($user_scores));

	$criteria = array('_id' => new MongoId($mongo_id), "score.athelete_nr" => array('$in' => array($starting_no)));
	$fields = array("score" => true, "_id" => false);
	$judge = iterator_to_array($database->judgescore->find($criteria, $fields)->limit(1));
	$judge = $judge[0]['score'];

	$judge_scores;
	for ($i = 0; $i < count($judge); $i++)
	{
		if ($judge[$i]['athelete_nr'] == $starting_no)
		{
			$judge_scores = $judge[$i]['judge_scores'];
			break;
		}
	}

	$avg_score = 0;
	for ($i = 0; $i < count($judge_scores); $i++)
	{
		$avg_score += (float)$judge_scores[$i]['score'];
	}

	$avg_score = $avg_score/count($judge_scores);

	$average_score = round($avg_score*2) / 2;

	$scores = $user_scores;
	$judge = $average_score;
	$max_performer = (int)$meta_points[count($meta_points)-1];
	$max_user = 1000; //(int)$meta_points[count($meta_points)-1];

	for ($i = 0; $i < count($scores); $i++)
	{
		$score = $scores[$i];
		//print_r($score);
		$points = round(ScoreCalculation::calculate($score['score'], $judge, $max_performer, $max_user), 1);

		update_user_score($database, $score['user_mongo_id'], round($points));

		notify_user($database, $score['platform'], $score['regid'], $points);
	}
	echo "</pre>";
	
	$criteria = array("_id" => new MongoId($mongo_id));
	$update = array('$set' => array("voting_enabled" => "false"));
	$database->event->update($criteria, $update);
}


function notify_user($database, $platform, $regid, $points)
{

	$message = "The scores are in! You scored " . $points . " points!";

	if ($platform == "ios")
	{
		push_ios_notification($regid, $message);
	}

	if ($platform == "android")
	{
		$msg = array("param" => "s", "score" => $message);
		sendNotification(array($regid), $msg);
	}

	//echo $platform . " - " . $regid . "<br>";
}


if ($athlete_id > 0)
{
	if ($_POST['scores'])
	{
		$scores = $_POST['scores'];

		$post_event_id = $_POST['event_id'];
		$post_athlete_id = $_POST['athlete_id'];

		$result_string = "";
		foreach ($scores as $judge => $score)
		{
			if (!is_numeric($score))
			{
				?>
				<div class="alert alert-danger" role="alert"><b>Error!</b> <?php echo $score . " is not a valid score!";?></div>
				<?php
				break;
			}

 			$result_string .= $judge . " - " . $score. "<br>";
		}

		if ((strlen($result_string) > 0) && $post_event_id && $post_athlete_id)
		{
			$result_string .=  "<br>#" . $post_athlete_id;
		}

		if (strlen($result_string) > 0)
		{
			?>
			<div class="alert alert-success" role="alert"><?php echo $result_string; ?></div>
			<?php

			include_once('model/score.class.php');

			$score = new Score($database, $post_event_id, $scores, $post_athlete_id);

			//echo "<pre>";
			$score->insert_new_score();
			//echo "</pre>";

			calculate_user_scores($database, $post_event_id, $post_athlete_id);
		}
	}


	$events = $database->event;
	$event = $events->find(array('_id' => new MongoId($event_id)), array("_id" => false))->limit(1);
	$event = iterator_to_array($event)[0];
	//print_r($event);

	$athletes = $event['event_data']['event_athelete_list'];

	$athlete;
	foreach ($athletes as $temp)
	{
		if ((int)$temp['athelete_starting_nr'] == (int)$athlete_id)
		{
			$athlete = $temp;
			break;
		}
	}

	?>
	<h3>Score for <?php echo $athlete['athelete_name']; ?> (#<?php echo $athlete_id; ?>) in <?php echo $event['event_data']['event_name']; ?></h3>

 	<?php
 	$judges = array(
 	array("Country" => "1"),
 	array("Country" => "2"),
 	array("Country" => "3"),
 	array("Country" => "4"),
 	array("Country" => "5"));


 	?>
 		<form class="form-inline" role="form" action="" method="post">
 		<div class="form-group" style="width: 100%;">
 	<?php

 	for ($i = 0; $i < count($judges); $i++)
 	{
 		?>

		    <div class="input-group">
		      <div class="input-group-addon" style="width: 40px;"><?php echo $judges[$i]['Country']; ?></div>
		      <input
		      style="width: 80px;"
		      class="form-control"
		      type="text"
		      placeholder="Score"
		      name="<?php echo 'scores['.$judges[$i]['Country'].']'; ?>"
		      value="">
			  </div>

 		<?php
 	}

 	?>
<br>
	<input type="hidden" name="athlete_id" value="<?php echo $athlete_id ;?>">
	<input type="hidden" name="event_id" value="<?php echo $event_id ;?>">
  <button type="submit" class="btn btn-default" style="margin-top: 20px;">Add Score</button>
 </div>
</form>
<br><br>
<a href="../../event/<?php echo $event_id;?>">
	<button type="submit" class="btn btn-warning" style="margin-top: 20px;">Back</button></a>
<?php
}
else
{
?>
	<?php

	$events = $database->event;

	//$start = date("2014-09-29 00:00:01");
	date_default_timezone_set("Europe/Oslo");
	$start = date("Y-m-d H:i:s");
	$end = date("2014-12-31 23:59:59");

	$condition = array('event_date' => array('$gt'=> $start, '$lt' => $end));

	$event = $events->find($condition)->sort(array('event_date' => 1));
	/*
	echo "<pre>";
	foreach ($event as $event_data)
	{
		//print_r($event_data);
	}
	echo "</pre>";
	*/
	?>
	<h3>Events</h3>
 	<table class="table table-dotted" style="width: 50%;">
  <tr>
    <th style="width: 50%;">Date</th>
    <th style="width: 50%;">Name</th>
  </tr>

	<?php
	foreach ($event as $event_data)
	{
		$temp_event_id		  = $event_data['event_id'];
		$temp_event_date 		= $event_data['event_data']['event_date'];
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
