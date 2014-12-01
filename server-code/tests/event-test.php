<?php

$url = 'http://ci.harnys.net/api/live';

$options = array(
    'http' => array(
        'header'  => "Content-type: application/x-www-form-urlencoded\r\n",
        'method'  => 'POST',
        'content' => http_build_query($data),
    ),
);
$context  = stream_context_create($options);
$result = file_get_contents($url, false, $context);

echo "<pre>";
print_r(json_decode($result, true));
echo "</pre>";
?>