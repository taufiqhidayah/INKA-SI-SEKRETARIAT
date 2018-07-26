<?php
include "konek.php";
$response = array();
if(isset($_POST["id"]))
{	
	$id = $_POST['id'];
	$sql = mysqli_query($con,"delete from t_dokumens WHERE t_dokumens.id ='$id'");

	if($sql)
	{
		$response["success"]	= "true";
		$response["message"]	= "Data berhasil di Update";
		echo json_encode($response);
	}else{
		$response["success"]	= "false";
		$response["message"]	= "Data gagal di Update";
		echo json_encode($response);
	}
}else{
	$response["success"]	= 0;
	$response["message"]	= "Data Kosong";
	echo json_encode($response);
}
?>



