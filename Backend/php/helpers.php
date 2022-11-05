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