<?php

// echo "<br>E: " . $event_id;
// echo "<br>A: " . $athelete_id;

$scores = $database->score;

$criteria = array("event_id" => $event_id);
try
{
$fields = array();
//"score.judge_scores.judge_id" => true,
//"score.judge_scores.score" => true

$score = $scores->find($criteria, $fields)->limit(1);
}
catch (MongoCursorException $e)
{
	echo "<br>" . $e->getMessage();
}
?>

<table class="table table-dotted">
	<tr>
		<th>
			Judge
		</th>
		<th style="text-align: right;">
			Score
		</th>
	</tr>
<?php

foreach ($score as $score)
{
	foreach ($score['score'] as $result)
	{
		if ($result['athelete_nr'] == $athelete_id)
		{
			foreach ($result['judge_scores'] as $judge_score)
			{
					?>
					<tr>
						<td>
							<?php echo $judge_score['judge_id']; ?>
						</td>
						<td style="text-align: right;">
							<?php echo $judge_score['score']; ?>
						</td>
					</tr>
					<?php
			}
			break;
		}
	}
}

echo "</pre>";
?>
