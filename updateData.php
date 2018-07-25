<?php
include "konek.php";
$response = array();
if(isset($_POST["id"]))
{	
	$id = $_POST['id'];
	$nomor_dokumen=$_POST["nomor_dokumen"];
	// $nomor_referensi =$_POST["nomor_referensi"];
	$nama_dokumen =$_POST["nama_dokumen"];
	$perihal =$_POST["perihal"];
	$pengirim =$_POST["pengirim"];
	$tgl_dok_referensi =date('Y-m-d H:i:s');
	$penerima =$_POST["penerima"];

	$tgl_masuk =date('Y-m-d H:i:s');
	$tgl_keluar =date('Y-m-d H:i:s');
	$tgl_kembali=date('Y-m-d H:i:s');
	$created_at=date('Y-m-d H:i:s');

	$sql = mysqli_query($con,"UPDATE t_dokumens SET nomor_dokumen='$nomor_dokumen',nama_dokumen='$nama_dokumen',perihal='$perihal',pengirim='$pengirim' ,penerima='$penerima'WHERE id='$id'");

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