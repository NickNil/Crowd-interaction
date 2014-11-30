<?php

/*!
* @class MongoConnection
* @brief MongoConnection is used to connect to the Mongo DB server
*/
class MongoConnection {

	private $database;

	function __construct()
	{
		$uri = "mongodb://andy:Apekatt88@ds033170.mongolab.com:33170/ci";
		$options = array("connectTimeoutMS" => 30000);

		$client = new MongoClient($uri, $options);

		$this->database = $client->ci;
	}

	/*!
	*	@return database object
	*/
	function database()
	{
		return $this->database;
	}




}



?>
