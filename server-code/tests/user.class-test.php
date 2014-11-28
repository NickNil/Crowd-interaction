<?php 

include_once('model/user.class.php');

$phone_number = "41415151";

$passcode = "1337";

$firstname = "Bjarne";

$lastname = "Betjent";

$nationality = "SLK";

$user = new User($database);

if ($user->set_user_data($phone_number, $passcode, $firstname, $lastname, $nationality))
{
	?>
	<div class="alert alert-success" role="alert">Success setting user data</div>	
	<?php
}
else 
{
	?>
	<div class="alert alert-danger" role="alert">Something went wrong setting user data</div>
	<?php
}

$user->to_string();

$result = $user->store_in_database();

if ($result == 1)
{
	?>
	<div class="alert alert-success" role="alert">User stored in database</div>	
	<?php
}
else if ($result == 11000)
{
	?>
	<div class="alert alert-danger" role="alert">Duplicate phone number</div>
	<?php
}


?>