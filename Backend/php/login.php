<?php

include("connection.php");
include("functions.php");

$username = $_POST["username"];
$pass = $_POST["password"];

$hashedpass = md5($pass);

$obj;

if (!usernameExists($connection, $username) || !passmatch($connection, $hashedpass, $username)) {
    $obj = array("status" => 401, "message" => "Wrong credentials");
    echo(json_encode($obj));
    exit();
}

$status = array("status" => 200);
$response = array_merge($status, getuserinfo($connection, $username));
echo(json_encode($response));