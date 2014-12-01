<?php


// Put your device token here (without spaces):
$deviceToken = $_POST['token'];
$mongo_id = $_POST['mongo_id'];

// Put your private key's passphrase here:
$passphrase = 'Apekatt88';

// Put your alert message here:
$message = '';
////////////////////////////////////////////////////////////////////////////////

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

$url = 'http://ci.harnys.net/api/live/'.$mongo_id;
$athlete = file_get_contents($url);
// print_r($athlete);
// Create the payload body
$body['aps'] = array(
	'alert' => $message,
	'sound' => 'none',
	'athlete' => $athlete
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
