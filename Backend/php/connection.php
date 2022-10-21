<?php

$db_host = "localhost";
$db_user = "root";
$db_pass = null;
$db_name = "groupify";

$connection = new mysqli($db_host, $db_user, $db_pass, $db_name);

if ($connection->connect_error) {
    die("Connection failed: " . $connection->connect_error);
}


// TODO: create tables if they don't exist


?>