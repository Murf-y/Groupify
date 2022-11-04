<?php

include("connection.php");

$username = $_POST["username"];
$bio = $_POST["bio"];
$profile_picture = $_POST["profile_photo"];
$id = $_POST["user_id"];

$query = $connection->prepare("UPDATE users SET username = ?, profile_photo = ?, bio = ? WHERE id = ?");
$query->bind_param("sssi", $username, $profile_picture, $bio, $id);
$query->execute();

echo json_encode(array("status" => 200));