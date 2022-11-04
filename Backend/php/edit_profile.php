<?php

include("connection.php");

$username = $_POST["username"];
$bio = $_POST["bio"];
$password = md5($_POST["password"]);
$email = $_POST["email"];
$profile_picture = $_POST["profile_photo"];
$id = $_POST["user_id"];

$query = $connection->prepare("UPDATE users SET username = ?, profile_photo = ?, bio = ?, password = ?, email = ? WHERE id = ?");
$query->bind_param("sssssi", $username, $profile_picture, $bio, $password, $email, $id);
$query->execute();

echo json_encode(array("status" => 200));