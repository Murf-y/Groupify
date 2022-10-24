<?php

include("connection.php");
include("functions.php");

$username =  $_POST["username"];
$email =  $_POST["email"];
$pass =  $_POST["password"];
$profile_picture = $_POST["profile_photo"];
$bio = "Hey there! I am using Groupify.";

$obj;


if(usernameExists($connection, $username)){
    $obj = array("status" => 403, "message" => "username already taken");
    echo(json_encode($obj));
    exit();
}

if(emailExists($connection, $email)){
    $obj = array("status" => 403, "message" => "email already taken");
    echo(json_encode($obj));
    exit();
}

$sql = "INSERT INTO users(username, password, email, bio, profile_photo) VALUES (?, ?, ?, ?, ?)";

$stmt = mysqli_stmt_init($connection);
mysqli_stmt_prepare($stmt, $sql);

$hashedpass = password_hash($pass, PASSWORD_DEFAULT);

mysqli_stmt_bind_param($stmt, "sssss", $username, $hashedpass, $email, $bio, $profile_picture);
mysqli_stmt_execute($stmt);
mysqli_stmt_close($stmt);

$obj = array("status" => 200, "message"=>"Signed in");
echo (json_encode($obj));