<?php 

include('connection.php');
include('helpers.php');

// if get request is sent from the client with group id, return the posts in that group ordered by date created 
if($_SERVER['REQUEST_METHOD'] === 'GET'){
    $group_id = $_GET['group_id'];
    $query = $connection->prepare("SELECT * FROM postables WHERE group_id = ? ORDER BY created_at ASC");
    $query->bind_param("i", $group_id);
    $query->execute();
    $result = $query->get_result();
    $posts = array();
    while($row = $result->fetch_assoc()){
        $posts[] = $row;
    }
    foreach($posts as $key => $post){
        $query = $connection->prepare("SELECT * FROM users WHERE id = ?");
        $query->bind_param("i", $post['user_id']);
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
        $posts[$key]['sender'] = $user;
    }

    echo json_encode(array("status" => 200, "data" => $posts));
}
else if($_SERVER['REQUEST_METHOD'] === 'POST'){
    $group_id = $_POST['group_id'];
    $user_id = $_POST['user_id'];
    $title = $_POST['title'];
    $content = $_POST['content'];
    $post_photo = $_POST['post_photo'];
    $query = $connection->prepare("INSERT INTO postables (group_id, user_id, title, content, post_photo) VALUES (?, ?, ?, ?, ?)");
    $query->bind_param("iisss", $group_id, $user_id, $title, $content, $post_photo);
    $query->execute();

    sendNotification($connection, $user_id, $group_id);
    echo json_encode(array("status" => 200));
}