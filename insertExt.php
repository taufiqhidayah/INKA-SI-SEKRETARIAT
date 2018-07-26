<?php
include "konek.php";
date_default_timezone_set('Asia/Jakarta');
$response = array();

if( isset($_POST["nama_dokumen"]))
{
	$sq= mysqli_query($con,"SELECT MAX(id) FROM t_dokumens");
	$row = mysqli_fetch_array($sq);
// Lock tables t_dokumens write;

// $max = SELECT MAX( id ) FROM t_dokumens;
	$id= $row[0]++;
	$tgl_masuk =date('Y-m-d H:i:s');
	$pengirim =$_POST["pengirim"];
	$tgl_dok_referensi =date('Y-m-d H:i:s');
	$nomor_dokumen=$_POST["nomor_dokumen"];
	$nama_dokumen =$_POST["nama_dokumen"];
	$perihal =$_POST["perihal"];
	$penerima =$_POST["penerima"];


	$tipe_dok_id =$_POST["tipe_dok_id"];
	// $nomor_referensi =$_POST["nomor_referensi"];
	$tgl_keluar =date('Y-m-d H:i:s');
	$tgl_kembali=date('Y-m-d H:i:s');
	$id_user =$_POST["id_user"];

	$created_at=date('Y-m-d H:i:s');

	$sql = mysqli_query($con,"INSERT INTO   t_dokumens(
t_dokumens.id,
t_dokumens.tipe_dok_id,
t_dokumens.tgl_masuk,
t_dokumens.nomor_dokumen,
t_dokumens.nomor_referensi,
t_dokumens.nama_dokumen,
t_dokumens.perihal,
t_dokumens.pengirim,
t_dokumens.tgl_dok_referensi,
t_dokumens.penerima,
t_dokumens.tgl_keluar,
t_dokumens.tgl_kembali,
t_dokumens.is_circular,
t_dokumens.is_closed,
t_dokumens.id_user,
t_dokumens.created_at,
t_dokumens.updated_at,
t_dokumens.deleted_at) VALUES 
		('$row[0]','2','$tgl_masuk','$nomor_dokumen','','$nama_dokumen','$perihal','$pengirim','$tgl_dok_referensi','$penerima','','','','','$id_user','$created_at','','')");

// unlock tables;
	if($sql)
	{
		$response["success"]	= "true";
		$response["message"]	= "Data berhasil di Tambah";
		echo json_encode($response);
	}else{

		// $response["ko"]				=$sq;
		$response["success"]	= "false";
		$response["message"]	= "Data gagal di Tambah";
		echo json_encode($response);
	}
}else{

		// $response["ko"]				=$sq;
	$response["success"]	= 0;
	$response["message"]	= "Data Kosong";
	echo json_encode($response);
}
?>