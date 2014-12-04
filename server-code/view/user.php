
<?php

if ($phone_number > 0)
{
		$users = $database->user;
		$user = $users->find(array('phone_number' => $phone_number))->limit(1);

		if ($user)
		{

			$user_nationality = $user['nationality'][0]['ioc'];

			$user_name = $user['name'];
			?>

			<table class="table table-dotted" style="width: 50%;">
			<tr>
			<th colspan="2">
			User data
			</th>
			</tr>

			<tr>
			<td>
			Name
			</td>
			<td>
			<?php echo $user_name['firstname']; ?> <?php echo $user_name['lastname']; ?>
			</td>
			</tr>

			<tr>
			<td>
			Phone
			</td>
			<td>
			<?php echo $phone_number; ?>
			</td>
			</tr>

			<tr>
			<td>
			Nationality
			</td>
			<td>
			<?php echo $user_nationality; ?>
			</td>
			</tr>

			</table>

			<?php
			/*
			$event_id = 1;
			echo "<pre>";
			for ($i = 0; $i < count($user_scores); $i++)
			{
				if ($user_scores[$i]['event_id'] == $event_id)
				{
					print_r($user_scores[$i]['scores']);
					break;
				}
			}

			echo "</pre>";
			*/

		}
		else
		{
			$response = array("response" => "No user was found");
			echo json_encode($response);
		}
}
else
{
	$response = array("response" => "Phone number must be provided");
	// echo json_encode($response);

			$users = $database->user;
			$all_users = $users->find();

			?>

			<h3>Users</h3>
			<table class="table table-dotted" style="width: 50%;">
			<?php
			foreach ($all_users as $user)
			{
				?>

				<tr>
				<td>
				<?php echo $user['name']['firstname']; ?> <?php echo $user['name']['lastname']; ?>
				</td>

				<td>
				<?php echo $user['phone_number']; ?>
				</td>

				<td>
				<?php
				for ($i = 0; $i < count($user['nationality']); $i++)
				{
					if (isset($user['nationality'][$i]['ioc']))
					{
						echo $user['nationality'][$i]['ioc'];
					}
				}
				?>
				</td>
				</tr>

			<?php
			}
			?>
			</table>

<?php
}
?>
