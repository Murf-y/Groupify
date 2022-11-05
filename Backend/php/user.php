<?php

include("connection.php");
include("helpers.php");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $username = $_POST["username"];
    $bio = $_POST["bio"];
    $profile_picture = $_POST["profile_photo"];
    $id = $_POST["user_id"];

    if (usernameExists($connection, $username)) {
        echo json_encode(array("status" => 403, "message" => "Username already taken"));
        return;
    }


    $query = $connection->prepare("UPDATE users SET username = ?, profile_photo = ?, bio = ? WHERE id = ?");
    $query->bind_param("sssi", $username, $profile_picture, $bio, $id);
    $query->execute();

    $query = $connection->prepare("SELECT * FROM users WHERE id = ?");
    $query->bind_param("i", $id);
    $query->execute();
    $result = $query->get_result();
    $user = $result->fetch_assoc();

    $query = $connection->prepare("SELECT COUNT(*) FROM group_members WHERE user_id = ?");
    $query->bind_param("i", $user["id"]);
    $query->execute();
    $result = $query->get_result()->fetch_assoc();
    $user["num_groups"] = $result["COUNT(*)"];

    $query = $connection->prepare("SELECT COUNT(*) FROM postables WHERE user_id = ?");
    $query->bind_param("i", $user["id"]);
    $query->execute();
    $result = $query->get_result()->fetch_assoc();
    $user["num_posts"] = $result["COUNT(*)"];

    echo json_encode(array("status" => 200, "data" => $user));
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

    foreach ($users as &$user) {
        $query = $connection->prepare("SELECT COUNT(*) FROM group_members WHERE user_id = ?");
        $query->bind_param("i", $user["id"]);
        $query->execute();
        $result = $query->get_result()->fetch_assoc();
        $user["num_groups"] = $result["COUNT(*)"];

        $query = $connection->prepare("SELECT COUNT(*) FROM postables WHERE user_id = ?");
        $query->bind_param("i", $user["id"]);
        $query->execute();
        $result = $query->get_result()->fetch_assoc();
        $user["num_posts"] = $result["COUNT(*)"];
    }

    echo json_encode(array("status" => 200, "data" => $users));

}