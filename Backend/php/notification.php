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

foreach ($users as $key => $user) {
    if ($user["user_id"] != $user_id) {

        $query = $connection -> prepare("SELECT * FROM notifications WHERE user_id = ?, group_id = ?, seen = False");
        $query->bind_param("i", $group_id);
        $query->execute();
        $result = $query->get_result();

        if($result->fetch_assoc() != null){
            $query = $connection->prepare("INSERT INTO notifications(receiver_id, message) VALUES(?, ?)");
            $query->bind_param("is", $user["user_id"], $message);
            $query->execute();

            return json_encode(array("status" => 200));
        }
    }
}