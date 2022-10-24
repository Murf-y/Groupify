<?php

$db_host = "localhost";
$db_user = "root";
$db_pass = null;
$db_name = "groupifydb";

$connection = new mysqli($db_host, $db_user, $db_pass, $db_name);

if ($connection->connect_error) {
    die("Connection failed: " . $connection->connect_error);
}



$connection->query("CREATE TABLE IF NOT EXISTS users (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    password VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    bio VARCHAR(100) NOT NULL,
    profile_photo TEXT NOT NULL
)");

$connection->query("CREATE TABLE IF NOT EXISTS groups (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    subject VARCHAR(30) NOT NULL,
    description VARCHAR(100),
    group_photo TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    owner_id INT(6) UNSIGNED NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users(id)
)");

$connection->query("CREATE TABLE IF NOT EXISTS group_members (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    group_id INT(6) UNSIGNED NOT NULL,
    user_id INT(6) UNSIGNED NOT NULL,
    FOREIGN KEY (group_id) REFERENCES groups(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
)");


// POSTABLE is a message or a post, if it have a photo & title => post, otherwise a message
$connection->query("CREATE TABLE IF NOT EXISTS postables (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    group_id INT(6) UNSIGNED NOT NULL,
    user_id INT(6) UNSIGNED NOT NULL,
    title VARCHAR(30),
    content VARCHAR(150) NOT NULL,
    post_photo TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (group_id) REFERENCES groups(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
)");

$connection->query("CREATE TABLE IF NOT EXISTS notifications (
    id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    receiver_id INT(6) UNSIGNED NOT NULL,
    seen BOOLEAN DEFAULT FALSE,
    message VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (receiver_id) REFERENCES users(id)
)");

?>