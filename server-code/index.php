<?php
if (1 == 2)
{
	ini_set('display_errors',1);
	ini_set('display_startup_errors',1);
	error_reporting(E_ALL & ~E_NOTICE);
}



include_once('model/mongoconnection.php');
$mongo = new MongoConnection();
$database = $mongo->database();

include_once('api_keys.php');

$_POST['api_key'] = "93D0HZy0RNXorlbW2nzVy46kw72d3Wy2";
$api_key = check_api_key($database, $_POST['api_key']);

$request  		= $_SERVER['REQUEST_URI'];
//$request  		= str_replace("/ci", "", $_SERVER['REQUEST_URI']);
$request 			= substr($request, 1);
$request 			= trim($request);
$params     	= explode("/", $request);

if ($params[0] !== "api")
{
	include_once('style.html');
}

$phone_number = 0;
$event_id = 0;
$athelete_id = 0;

$safe_pages = array("event", "user", "leaderboard", "score", "scores", "test", "admin", "api");

if(in_array($params[0], $safe_pages))
{
	if ($params[0] === "user")
	{
		if (isset($params[1]))
		{
			$phone_number = $params[1];
		}

		include_once('view/user.php');
	}

	if ($params[0] === "event" && !isset($params[1]))
	{
		$event_id = 0;
	}

	if ($params[0] === "event" && isset($params[1]))
	{
		$event_id = $params[1];
	}

	if ($params[0] === "event")
	{
		include_once("view/event.php");
	}

	if ($params[0] === "test")
	{

		if ($params[1] === "unit")
		{
			include_once("phpunittests/index.php");
		}
		else if (strlen($params[1]) > 1)
		{
			include_once("tests/". $params[1].".php");
		}
	}

	if ($params[0] === "admin" && $params[1] === "new")
	{
		include_once("view/admin_new_event.php");
	}
	if ($params[0] === "admin" && $params[1] === "edit")
	{
		include_once("view/admin_edit_event.php");
	}
	if ($params[0] === "admin" && $params[1] === "next")
	{
		$event_id = $params[2];
		include_once("view/admin_next_athlete.php");
	}
	if ($params[0] === "admin" && $params[1] === "status")
	{
		$event_id = $params[2];
		include_once("view/admin_change_status.php");
	}
	if ($params[0] === "score")
	{
		if ($params[1] !== "view")
		{
			$athelete_id = $params[2];
			$event_id = $params[1];
			include_once("view/score.php");
		}
	}

	if ($params[0] === "score" && (strlen($params[1]) > 0) && !isset($params[2]))
	{
		if ($params[1] !== "view")
		{
			?>
			<div class="alert alert-danger" role="alert"><b>Error!</b> Athelete starting number missing</div>
			<?php
		}
	}

	if ($params[0] === "scores")
	{
		$event_id = $params[1];
		$athelete_id = $params[2];
		include_once("view/user_scores_for_event.php");
	}
}
else
{
	echo "404";
}


if ($api_key != 0 && in_array($params[0], $safe_pages))
{
	if ($params[0] === "api")
	{
		header('Content-Type: application/json; charset=utf-8', true,200);
		if ($params[1] === "register")
		{
			include_once("app-api/register.php");
		}
		if ($params[1] === "register_android")
		{
			include_once("app-api/register-new.php");
		}
		if ($params[1] === "login")
		{
			include_once("app-api/login.php");
		}

		if ($params[1] === "leaderboard")
		{
			include_once("app-api/leaderboard.php");
		}

		if ($params[1] === "add_user_score")
		{
			include_once("app-api/add_user_score.php");
		}
		if ($params[1] === "events")
		{
			include_once("app-api/events.php");
		}
		if ($params[1] === "ioc")
		{
			include_once("app-api/countries.php");
		}
		if ($params[1] === "live")
		{
			include_once("app-api/current_athlete.php");
		}
		if ($params[1] === "meta")
		{
			include_once("app-api/meta.php");
		}
		if ($params[1] === "userscore")
		{
			include_once("app-api/user_score.php");
		}
		if ($params[1] === "push_live_ios")
		{
			include_once("push/ios/live.php");
		}
		if ($params[1] === "keys")
		{
			include_once("app-api/keys.php");
		}
	}

	// IF NOT ACCEPTED PARAM
}
else if ($api_key != 0 && in_array($params[0], $safe_pages))
{
	if ($params[1] === "keys")
	{
		include_once("app-api/keys.php");
	}
}
else
{
	// NO ENTER HERE
}
// IF NOT VALID API KEY

?>
