<?php

include("connection.php");


// userid is the user that sent the post (should not send him a notification)
// groupid is the group that the post was sent to

if($_SERVER['REQUEST_METHOD'] === 'GET'){
    $user_id = $_GET['user_id'];
    $query = $connection->prepare("SELECT * FROM notifications WHERE receiver_id = ? AND seen = 0");
    $query->bind_param("i", $user_id);
    $query->execute();
    $result = $query->get_result();
    $notifications = array();
    while($row = $result->fetch_assoc()){
        $notifications[] = $row;
    }

    foreach($notifications as $key => $notification){
        $query = $connection->prepare("SELECT * FROM groups WHERE id = ?");
        $query->bind_param("i", $notification['group_id']);
        $query->execute();
        $result = $query->get_result();
        $group = $result->fetch_assoc();

        // add number of members in the group to the group
        $query = $connection->prepare("SELECT COUNT(*) FROM group_members WHERE group_id = ?");
        $query->bind_param("i", $group['id']);
        $query->execute();
        $result = $query->get_result()->fetch_assoc();
        $group['number_of_members'] = $result["COUNT(*)"];

        $notifications[$key]['group'] = $group;
    }

    $query = $connection->prepare("UPDATE notifications SET seen = 1 WHERE receiver_id = ?");
    $query->bind_param("i", $user_id);
    $query->execute();

    echo json_encode(array("status" => 200, "data" => $notifications));
}
else if($_SERVER['REQUEST_METHOD'] === 'POST'){
    // get the count of the notifications unseen for the user
    $user_id = $_POST['user_id'];
    $query = $connection->prepare("SELECT COUNT(*) FROM notifications WHERE receiver_id = ? AND seen = 0");
    $query->bind_param("i", $user_id);
    $query->execute();
    $result = $query->get_result()->fetch_assoc();
    $count = $result["COUNT(*)"];

    echo json_encode(array("status" => 200, "data" => $count));
}