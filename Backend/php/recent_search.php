<?php

include("connection.php");

if($_SERVER['REQUEST_METHOD'] === 'GET'){
    $user_id = $_GET["user_id"];

    $query = $connection->prepare("SELECT * FROM recent_searches WHERE user_id = ? ORDER BY id DESC LIMIT 5");
    $query->bind_param("i", $user_id);
    $query->execute();
    $result = $query->get_result();
    $recent_searches = array();
    
    while ($row = $result->fetch_assoc()) {
        $recent_searches[] = $row;
    }

    echo json_encode(array("status" => 200, "data" => $recent_searches));
}
else if($_SERVER['REQUEST_METHOD'] === 'POST'){
    $user_id = $_POST["user_id"];
    $search_term = $_POST["search_term"];

    $query = $connection->prepare("INSERT INTO recent_searches (user_id, search_term) VALUES (?, ?)");
    $query->bind_param("is", $user_id, $search_term);
    $query->execute();

    echo json_encode(array("status" => 200));
}