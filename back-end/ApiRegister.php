<?php
header("Content-Type:application/json");

if (isset($_POST['username']) && $_POST['username']!="") {
    include('config.php');
    $username = $_POST['username'];
    $password = $_POST['password'];
    $session = '1';
    $param_password = password_hash($password, PASSWORD_DEFAULT);
    $result = mysqli_query($link, "INSERT INTO user (username, password, session) VALUES ('$username', '$param_password', '$session')");
    response($username, $session);
    mysqli_close($link);
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