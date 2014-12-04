<?php
	
	$mongo_id = $params[2];	
	
	$criteria = array("_id" => new MongoId($mongo_id));
	$fields = array("highscore" => true, "_id" => false);
	$result = $database->user->find($criteria, $fields)->limit(1);
	
	$highscore = iterator_to_array($result)[0];
	
	$response = $highscore;
		
	print_r(json_encode(array(array("param" => "us", "data" => $response))));
	


?>