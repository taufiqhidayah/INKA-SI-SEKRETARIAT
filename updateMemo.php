<?php
include "konek.php";
$response = array();
if(isset($_POST["id"]))
{	
	// $nomor_dokumen=$_POST["nomor_dokumen"];
	// $tgl_masuk =date('Y-m-d H:i:s');
	// $pengirim =$_POST["pengirim"];
	// $nama_dokumen =$_POST["nama_dokumen"];
	// $perihal =$_POST["perihal"];
	// $penerima =$_POST["penerima"];
	$id= $_POST['id'];
	// $tipe_dok_id =$_POST["tipe_dok_id"];
	// $id_user =$_POST["id_user"];
$nomor_dokumen=$_POST["nomor_dokumen"];
	$id_user =$_POST["id_user"];
	$perihal =$_POST["perihal"];
	$tipe_dok_id =$_POST["tipe_dok_id"];
	$pengirim =$_POST["pengirim"];
	$created_at=date('Y-m-d H:i:s');

	// $sql = mysqli_query($con,"UPDATE t_dokumens SET nomor_dokumen='$nomor_dokumen',nama_dokumen='$nama_dokumen',perihal='$perihal',pengirim='$pengirim' WHERE id='$id'");


$query =mysqli_query($con, "UPDATE t_dokumens SET
 
  t_dokumens.tipe_dok_id='$tipe_dok_id',
  t_dokumens.nomor_dokumen='$nomor_dokumen',
t_dokumens.perihal='$perihal',
  t_dokumens.pengirim='$pengirim' WHERE t_dokumens.id='$id'");

	if($query)
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