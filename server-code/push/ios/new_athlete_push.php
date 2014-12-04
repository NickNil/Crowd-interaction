<?php


function send_new_athlete_notification($deviceToken, $number)
{


// Put your private key's passphrase here:
$passphrase = 'Apekatt88';


$ctx = stream_context_create();
stream_context_set_option($ctx, 'ssl', 'local_cert', '/home/ci/htdoc/push/ios/ck.pem');
stream_context_set_option($ctx, 'ssl', 'passphrase', $passphrase);

// Open a connection to the APNS server
$fp = stream_socket_client(
	'ssl://gateway.sandbox.push.apple.com:2195', $err,
	$errstr, 60, STREAM_CLIENT_CONNECT|STREAM_CLIENT_PERSISTENT, $ctx);

if (!$fp)
	exit("Failed to connect: $err $errstr" . PHP_EOL);

//echo 'Connected to APNS' . PHP_EOL;

// Put your alert message here:
////////////////////////////////////////////////////////////////////////////////
$message = "new_athlete";

// Create the payload body
$body['aps'] = array(
	'alert' => '',
	'sound' => 'none',
	'new_athlete' => $message,
	'athlete_number' => $number
	);

// Encode the payload as JSON
$payload = json_encode($body);

// Build the binary notification
$msg = chr(0) . pack('n', 32) . pack('H*', $deviceToken) . pack('n', strlen($payload)) . $payload;

// Send it to the server
$result = fwrite($fp, $msg, strlen($msg));

if (!$result)
	echo json_encode(array("response" => "0"));
else
	echo json_encode(array("response" => "1"));

// Close the connection to the server
fclose($fp);

}
