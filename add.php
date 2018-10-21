<?php
error_reporting("E_ALL");
$servername = "localhost:3307";
$username = "root";
$password = "";
$dbname = "gscrapper";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


$ip = $_SERVER['REMOTE_ADDR'];
$a = date("Y-m-d h:i:sa");



$kw = $_GET["kw"];
$frm = $_GET["f"];

$sql = "SELECT * from results where isdone=0 and frm='".$frm."'";
$result = $conn->query($sql);


if ($result->num_rows == 0) {

$frm = $a." ".$ip;

$sql = "insert into comments (frm,kword,isdone,isprocessing) 
values ('".$frm."','".$kw."',0,0)";
$conn->query($sql);

echo $frm;
}


else{
	echo "    ongoing";
	
}


$conn->close();




?> 