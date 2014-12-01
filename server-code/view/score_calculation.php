<?php

/*!
* @class ScoreCalculation
* @brief ScoreCalculation is used to calculate scores from users
*/
class ScoreCalculation
{
	/**
	* Returns the final score of a users guess
	*
	* @param float $user The guess from a user
	* @param float $jugde The final score from the jugde
	* @param float $maxPerformer Maximum score for the performer
	* @param float $maxUser Maximum score for the user
	* @return float
	*/
	public static function calculate($user, $jugde, $maxPerformer, $maxUser)
	{
		// Find out how much percentage the user missed
		$diff = self::getDiff($user, $jugde, $maxPerformer);
		if ($diff)
		{
			// The percentage of minus points the user get
			$minus = $diff + pow(1.05, $diff);
			// The percentage of how close the user where to the correct answer
			$precision = 100 - $minus;
			// Convert percentage
			$rate = $precision/100;
			// Final score
			$score = $maxUser * $rate;
		}
		else
		{
			$score = $maxUser;
		}
		return $score;
	}

	/**
	* Returns the difference in percentage
	*/
	private static function getDiff($user, $jugde, $max)
	{
		$percentage = 100/(float) $max;
		$offset = abs((float) $jugde - (float) $user);
		return $percentage*$offset;
	}
}
?>
