<?php

include("connection.php");

$user_id = $_POST["user_id"];
$message = $_POST["message"];
$group_id = $_POST["group_id"];

$query = $connection -> prepare("SELECT user_id FROM group_members WHERE group_id = ?");
$query->bind_param("i", $group_id);
$query->execute();
$result = $query->get_result();

while($ele = $result->fetch_assoc()){
    $users[] = $ele;
}
