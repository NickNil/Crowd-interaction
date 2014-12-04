<?php
$all_users = $database->user;
$fields = array("name" => true, "highscore" => true, "nationality" => true, "_id" => false);
$users = $all_users->find(array("highscore" => array( '$gt' => 0)))->fields($fields)->sort(array('highscore' => -1));

$standing = 1;
$standings_list = array();
foreach ($users as $user)
{
	$standings = array("standing" => $standing, "user" => $user);
	$standings_list[] = $standings;
	$standing++;
}
//print_r(array(array("param" => "hs", "data" => $standings_list)));
print_r(json_encode(array(array("param" => "hs", "data" => $standings_list))));
?>