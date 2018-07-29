<?php
include "konek.php";
$response = array();
if(isset($_POST["id"]))
{	
	$nomor_dokumen=$_POST["nomor_dokumen"];
	$tgl_masuk =date('Y-m-d H:i:s');
	$pengirim =$_POST["pengirim"];
	$nama_dokumen =$_POST["nama_dokumen"];
	$perihal =$_POST["perihal"];
	$penerima =$_POST["penerima"];
	$id= $_POST['id'];
	$tipe_dok_id =$_POST["tipe_dok_id"];
	$id_user =$_POST["id_user"];

	$sql = mysqli_query($con,"UPDATE t_dokumens SET nomor_dokumen='$nomor_dokumen',nama_dokumen='$nama_dokumen',perihal='$perihal',pengirim='$pengirim', penerima ='$penerima' WHERE id='$id'");

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
	$response["success"]	= "false";
	$response["message"]	= "Data Kosong";
	echo json_encode($response);
}
?>