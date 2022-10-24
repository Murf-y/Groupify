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

    return $row = mysql_fetch_array($result) && $row["password"] == $pass;
}