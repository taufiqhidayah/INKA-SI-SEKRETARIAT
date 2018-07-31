
<?php
$con = mysqli_connect("localhost","root","","sekapi");
// $con = mysqli_connect("localhost","adapasar_adapasar","adapasar_admin","adapasar17*")

// $server ="localhost";
// $username	= "adapasar_adapasar";
// $password	="adapasar17*";
// $database	="adapasar_admin";
if (mysqli_connect_errno()){
	echo "Failed Connect".mysqli_connect_errno();
	exit();

}
// mysqli_connect($server, $username, $password) or die("Koneksi tidak ada");
//mysqli_select_db($database) or die("Database tidak ditemukan");
?>