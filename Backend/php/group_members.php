<?php 

include("connection.php");

if ($_SERVER['REQUEST_METHOD'] === 'GET'){
    $user_id = $_GET['user_id'];
    $group_id = $_GET['group_id'];

    $query = $connection->prepare("SELECT * FROM group_members WHERE group_id = ? AND user_id = ?");
    $query->bind_param("ii", $group_id, $user_id);
    $query->execute();
    $result = $query->get_result()->fetch_assoc();
    if($result === null) $result = [];
    else $result = [$result];
    echo(json_encode(array("status" => 200, "data" => $result)));
}
else if ($_SERVER['REQUEST_METHOD'] === 'POST'){
    $user_id = $_POST['user_id'];
    $group_id = $_POST['group_id'];

    $query = $connection->prepare("INSERT INTO group_members(group_id, user_id) VALUES(?, ?)");
    $query->bind_param("ii", $group_id, $user_id);
    $query->execute();

    echo json_encode(array("status" => 200));
}
else if ($_SERVER['REQUEST_METHOD'] === 'DELETE'){
    $user_id = $_POST['user_id'];
    $group_id = $_POST['group_id'];

    $query = $connection->prepare("DELETE FROM group_members WHERE group_id = ? AND user_id = ?");
    $query->bind_param("ii", $group_id, $user_id);
    $query->execute();

    echo json_encode(array("status" => 200));
}