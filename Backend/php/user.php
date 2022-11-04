<?php

include("connection.php");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = $_POST["username"];
    $bio = $_POST["bio"];
    $profile_picture = $_POST["profile_photo"];
    $id = $_POST["user_id"];

    $query = $connection->prepare("UPDATE users SET username = ?, profile_photo = ?, bio = ? WHERE id = ?");
    $query->bind_param("sssi", $username, $profile_picture, $bio, $id);
    $query->execute();

    echo json_encode(array("status" => 200, "data" => array("username" => $username, "bio" => $bio, "profile_photo" => $profile_picture)));
}
else if($_SERVER['REQUEST_METHOD'] === 'GET') {
    $group_id = $_GET["group_id"];
    $query = $connection->prepare("SELECT users.id, users.username, users.profile_photo, users.bio FROM users JOIN group_members ON users.id = group_members.user_id WHERE group_members.group_id = ?");
    $query->bind_param("i", $group_id);
    $query->execute();
    $result = $query->get_result();

    $users = array();

    while ($row = $result->fetch_assoc()) {
        $users[] = $row;
    }

    echo json_encode(array("status" => 200, "data" => $users));

}