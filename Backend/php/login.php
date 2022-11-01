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

$obj = array("status" => 200);
echo(json_encode($obj));