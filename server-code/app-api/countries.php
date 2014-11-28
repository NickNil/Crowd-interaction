<?php
ini_set('display_errors',1);
ini_set('display_startup_errors',1);
error_reporting(-1);

$json = file_get_contents('app-api/country-codes.json');
$countries = json_decode($json, true);
$count = count($countries);

$countries_result = array();
for ($i = 0; $i < $count; $i++)
{
	if (strlen($countries[$i]['IOC']) == 3)
	{
		$array = array("ioc" => $countries[$i]['IOC'], "iso" => $countries[$i]['ISO3166-1-Alpha-2'], "name" => $countries[$i]['name']);
		$countries_result[] = $array;
	}
}

print_r(json_encode(array(array("param" => "ioc", "data" => $countries_result))));
?>