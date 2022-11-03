<?php

include("connection.php");

// Create group
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $subject = $_POST["subject"];
    $ownerid = $_POST["user_id"];
    $description= $_POST["description"];
    $icon_base64 = $_POST["group_photo"];

    $query = $connection->prepare("INSERT INTO groups(subject, description, group_photo, owner_id) VALUES (?, ?, ?, ?)");
    $query->bind_param("sssi", $subject, $description, $icon_base64, $ownerid);
    $query->execute();

    $lastid = mysqli_insert_id($connection);

    $query = $connection->prepare("INSERT INTO group_members(group_id, user_id) VALUES(?, ?)");
    $query->bind_param("ii", $lastid, $ownerid);
    $query->execute();

    echo json_encode(array("status" => 200));
}
else if ($_SERVER['REQUEST_METHOD'] === 'GET') {
   // if there is a owner_id parameter, return all groups owned by that user
   // otherwise, return all groups


    if (isset($_GET["owner_id"])) {
        $ownerid = $_GET["owner_id"];
        $query = $connection->prepare("SELECT * FROM groups WHERE owner_id = ?");
        $query->bind_param("i", $ownerid);
        $query->execute();
        $result = $query->get_result();
        $groups = array();

        while ($row = $result->fetch_assoc()) {
            $groups[] = $row;
        }
        
        foreach ($groups as &$group) {
            $groupid = $group["id"];
            $query = $connection->prepare("SELECT COUNT(*) AS number_of_members FROM group_members WHERE group_id = ?");
            $query->bind_param("i", $groupid);
            $query->execute();
            $result = $query->get_result();
            $row = $result->fetch_assoc();
            $group["number_of_members"] = $row["number_of_members"];
        }
        echo json_encode(array("status" => 200, "data" => $groups));
    }
    else {
        $query = $connection->prepare("SELECT * FROM groups");
        $query->execute();
        $result = $query->get_result();
        $groups = array();
        while ($row = $result->fetch_assoc()) {
            $groups[] = $row;
        }
        // in each group, add the number of members (based on group_members table)

        for ($i = 0; $i < count($groups); $i++) {
            $groupid = $groups[$i]["id"];
            $query = $connection->prepare("SELECT COUNT(*) AS number_of_members FROM group_members WHERE group_id = ?");
            $query->bind_param("i", $groupid);
            $query->execute();
            $result = $query->get_result();
            $row = $result->fetch_assoc();
            $groups[$i]["number_of_members"] = $row["number_of_members"];
        }

        
        echo json_encode(array("status" => 200, "data" => $groups));
    }
}