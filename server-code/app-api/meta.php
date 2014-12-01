<?php
$meta = $database->meta;
$criteria = array();
$fields = array("_id" => false);
$metas = $meta->find($criteria, $fields);
$metas = iterator_to_array($metas);

print_r(json_encode(array(array("param" => "meta", "data" => $metas))));
?>