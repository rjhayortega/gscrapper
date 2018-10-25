<?php

$servername = "localhost";
$username = "root";
$password = "";
$dbname = "gscrapper";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * from results where isdone=0 and frm='".$_GET["f"]."' order by id ASC LIMIT 5";
$result = $conn->query($sql);

$bdy = "";
if ($result->num_rows > 0) {
    // output data of each row
    while($row = $result->fetch_assoc()) {
      $bdy = $row["bdy"];
        $sql1 = "update results set isdone=1 where id=".$row["id"];
		$conn->query($sql1);
    }

echo $bdy;

} 


else {
    echo "  0 results";
}
$conn->close();




?> 