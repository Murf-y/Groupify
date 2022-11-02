<?php

include ("connection.php");

$query = $connection->prepare("SELECT * FROM groups ORDER BY created_at DESC");
$query->execute();
$result = $query->get_result();

while($group = $result->fetch_assoc()){
    $results[] = $group;
}

$response = array("status" => 200);
echo json_encode(array_merge($response, $results));