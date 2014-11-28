<?php

function check_api_key($database, $api_key)
{
  if (isset($api_key))
  {
    $test_key = $api_key;
    $criteria = array("key" => $test_key);
    $fields = array("owner" => false, "key" => false);
    $api_key_checker = $database->apikeys->findOne($criteria, $fields);

    if (strlen($api_key_checker['_id']) == 24)
    {
      return $api_key_checker['_id'];
    }
    else
    {
      return 0;
    }
  }
  else
  {
    return 0;
  }
}

?>
