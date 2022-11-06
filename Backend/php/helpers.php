<?php

function usernameExists($connection, $username){
    
    $sql = "SELECT username FROM users WHERE username = ?";
    $stmt = mysqli_stmt_init($connection);
    mysqli_stmt_prepare($stmt, $sql);
    mysqli_stmt_bind_param($stmt, "s", $username);
    mysqli_stmt_execute($stmt);

    $results = mysqli_stmt_get_result($stmt);
    
    return mysqli_fetch_assoc($results);

}

function emailExists($connection, $email){
    
    $sql = "SELECT email FROM users WHERE email= ?";
    
    $stmt = mysqli_stmt_init($connection);
    mysqli_stmt_prepare($stmt, $sql);
    mysqli_stmt_bind_param($stmt, "s", $email);
    mysqli_stmt_execute($stmt);
    
    $results = mysqli_stmt_get_result($stmt);
    
    return mysqli_fetch_assoc($results);

}

function passmatch($connection, $pass, $username){
    $sql = "SELECT password FROM users WHERE username = ?";

    $stmt = mysqli_stmt_init($connection);
    mysqli_stmt_prepare($stmt, $sql);
    mysqli_stmt_bind_param($stmt, "s", $username);
    mysqli_stmt_execute($stmt);

    $results = mysqli_stmt_get_result($stmt);
    $row = mysqli_fetch_assoc($results);

    return $row["password"] == $pass;
}

function getuserinfo($connection, $username){
    

    $query = $connection->prepare("SELECT * FROM users WHERE username = ?");
    $query->bind_param("s", $username);
    $query->execute();
    $results = $query->get_result();

    // add the number of groups they are in, and the number of posts they have made
    $user = $results->fetch_assoc();

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

    return $user;
}

function sendNotification($connection, $user_id, $group_id){

    // get all users in the group
    $query = $connection->prepare("SELECT * FROM group_members WHERE group_id = ?");
    $query->bind_param("i", $group_id);
    $query->execute();
    $result = $query->get_result();
    $users = array();
    while($row = $result->fetch_assoc()){
        $users[] = $row;
    }

    // get all users that already have a notification for this group and notification "seen" is false
    $query = $connection->prepare("SELECT * FROM notifications WHERE group_id = ? AND seen = 0");
    $query->bind_param("i", $group_id);
    $query->execute();
    $result = $query->get_result();
    $users_with_notification = array();
    while($row = $result->fetch_assoc()){
        $users_with_notification[] = $row;
    }

    // send a notification to all users in the group except the user that sent the post
    // and except the users that already have a notification for this group and notification "seen" is false

    $query = $connection->prepare("SELECT * FROM groups WHERE id = ?");
    $query->bind_param("i", $group_id);
    $query->execute();
    $result = $query->get_result();
    $group = $result->fetch_assoc();
    
    $query = $connection->prepare("SELECT * FROM users WHERE id = ?");
    $query->bind_param("i", $user_id);
    $query->execute();
    $result = $query->get_result();
    $sender = $result->fetch_assoc();

    foreach($users as $user){
        if($user["user_id"] != $user_id){
            $found = false;
            foreach($users_with_notification as $user_with_notification){
                if($user_with_notification["receiver_id"] == $user["user_id"]){
                    $found = true;
                    break;
                }
            }
            if(!$found){

                $message = $sender["username"] . " sent a message in " . $group["subject"];
                $query = $connection->prepare("INSERT INTO notifications (receiver_id, group_id, message) VALUES (?, ?, ?)");
                $query->bind_param("iis", $user["user_id"], $group_id, $message);
                $query->execute();

            }
        }
    }
}