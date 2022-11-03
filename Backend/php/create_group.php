<?php

include("connection.php");

$subject = $_POST["subject"];
$ownerid = $_POST["user_id"];
$description= $_POST["description"];
$icon_base64 = $_POST["group_photo"]

$query = $connection->prepare("INSERT INTO groups(subject, description, group_photo, owner_id) VALUES (?, ?, ?, ?)");
$query->bind_param("sssi", $subject, $description, $icon_base64, $ownerid);
$query->execute();

$lastid = mysqli_insert_id($connection);

$query = $connection->prepare("INSERT INTO group_members(group_id, user_id) VALUES(?, ?)");
$query->bind_param("ii", $lastid, $ownerid);
$query->execute();

echo json_encode(array("status" => 200));