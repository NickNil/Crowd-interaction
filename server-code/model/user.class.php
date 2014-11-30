<?php

/*!
* @class User
* @brief User is used to create a user and stores it in database
*/
class User {

	private $phone_number;
	private $name;
	private $nationality;
	private $passcode;
	private $database;
	private $regid;

	/**
	* @param object $database MongoConnection
	*/
	function __construct($database)
	{
		$this->database = $database;
	}

	/**
	* @param string $phone_number Phone number
	* @param string $passocde Passcode/password
	* @param string $firstname Firstname
	* @param string $lastname Lastname
	* @param array $nationality Array with ioc and iso nationalities
	* @return bool true if ok, else !ok
	*/
	public function set_user_data($phone_number, $passcode, $firstname, $lastname, $nationality, $regid)
	{
		if ((strlen($phone_number) == 0) ||
		(strlen($passcode) == 0) ||
		(strlen($firstname) == 0) ||
		(strlen($lastname) == 0) ||
		(strlen($regid) == 0))
		{
			return 0;
		}

		if (!isset($phone_number) || !isset($passcode) || !isset($firstname) || !isset($lastname) || !isset($nationality) || !isset($regid))
		{
			return 0;
		}

		$this->phone_number 		= $phone_number;
		$this->passcode 			= $passcode;
		$this->name 				= array("firstname" => $firstname, "lastname" => $lastname);
		$this->lastname 			= $lastname;
		$this->nationality 			= $nationality;
		$this->regid 				= $regid;
		return 1;
	}

	/**
	* @param string $phone_number Phone number
	* @param string $passcode Passcode/password
	* @return bool true if ok, else !ok
	*/
	public function login($phone_number, $passcode)
	{
		$this->phone_number 		= $phone_number;
		$this->passcode 			= $passcode;

		$result = $this->database->user->findOne(array("phone_number" => $this->phone_number, "passcode" => $this->passcode),
		array("_id" => 1, "name" => 1, "nationality" => 1, "regid" => 1, "highscore" => 1));
		if (isset($result['_id']))
		{
			return $result;
		}
		else
		{
			return 0;
		}
	}



	/**
	* Method stores user in database
	* @return int 1 = ok, 0 = !ok, 11000 = duplicate phone number
	*/
	public function store_in_database()
	{

		$user = new stdClass();
		$user->phone_number			= $this->phone_number;
		$user->passcode 				= $this->passcode;
		$temp_nationality = $this->nationality;

		$user->nationality 	= array("iso" => $temp_nationality['iso'], "ioc" => $temp_nationality['ioc']);
		$user->name 						= $this->name;
		$user->highscore				= 0;
		$user->regid =				$this->regid;
		$user->scores						= array();

		try
		{
			$this->database->user->insert($user);
			$find = $this->database->user->findOne(array("phone_number" => $this->phone_number), array());
			$id = $find['_id'];
			return $id->{'$id'};
		}
		catch(MongoCursorException $e)
		{
			return $e->getCode();
		}

	}

	/**
	* Method print_r a user for debugging purposes
	*/
	public function to_string()
	{

		$user = new stdClass();
		$user->phone_number			= $this->phone_number;
		$user->passcode 				= $this->passcode;
		$user->nationality 			= $this->nationality;
		$user->name 						= $this->name;
		$user->highscore				= 0;
		$user->scores						= array();

		echo "<pre>";
		print_r($user);
		echo "</pre>";

	}


}








?>
