<?php
header("Content-Type:application/json");

if (isset($_POST['username']) && $_POST['username']!="") {
    include('config.php');
    $username = $_POST['username'];
    $password = $_POST['password'];
    $param_password = password_hash($password, PASSWORD_DEFAULT);
    $result = mysqli_query($link, "SELECT * FROM `user` WHERE `username` = '$username'");
    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_array($result);
        $name = $row['username'];
        $session = $row['session'];
        if(password_verify($password, $param_password)) {
            response($name, $session);
            mysqli_close($link);
        }   
    } else {
        response(NULL, NULL, 200, "No Record Found");
    }
} else {
    response(NULL, NULL, 400,"Invalid Request");
}
 
function response($name, $session) {
    $response['username'] = $name;
    $response['session'] = $session;
    
    $json_response = json_encode($response);
    echo $json_response;
}

?>