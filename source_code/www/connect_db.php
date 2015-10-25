<?php
$host="127.0.0.1:3306";
$username="root";
$password="krishna";
$db_name="drugdata";

// Connect to server and select databse.
$link=mysql_connect("$host", "$username", "$password")or die("cannot connect");
mysql_select_db("$db_name")or die("cannot select DB");

?>
