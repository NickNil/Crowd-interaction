<?php

/*!
* @class Score
* @brief Score is used to add scores from judges in database
*/
class Score
{

	private $database;
	private $event_id;
	private $judges_scores = array();
	private $athlete_id;

	/**
	* @param object $db MongoConnection
	* @param object $id Event MongoId
	* @param array $scores Array of all scores from judges
	* @param string $athlete_id Athlete starting number
	*/
	function __construct($db, $id, $scores, $athlete_id)
	{
		$this->database = $db;
		$this->event_id = $id;
		$this->judges_scores = $scores;
		$this->athlete_id = $athlete_id;
	}


	function insert_new_score()
	{
		$data = new stdClass;
		$judges_scores = array();

		foreach ($this->judges_scores as $judge => $score)
		{
				array_push($judges_scores, array("judge_id" => $judge, "score" => (int)$score));
		}

		$score = $this->database->judgescore;
		$criteria = array('_id' => new MongoId($this->event_id));
   	$result = $score->find($criteria)->limit(1);
		$result = iterator_to_array($result);
		// collection not empty -> Update
		if ($result != NULL)
		{
			$data->score = array("athelete_nr" => $this->athlete_id, "judge_scores" => $judges_scores);
			return $score->update($criteria, array('$push' => $data));
		}
		else
		{
			$data->_id = new MongoId($this->event_id);
			$data->score = array(array("athelete_nr" => $this->athlete_id, "judge_scores" => $judges_scores));
			return $score->insert($data);
		}

	}

}

?>
