<?php
include "konek.php";
date_default_timezone_set('Asia/Jakarta');
$response = array();

if( isset($_POST["perihal"]))
{
	$max= mysqli_query($con,"SELECT MAX(id) FROM t_dokumens");
	$row = mysqli_fetch_array($max);
// Lock tables t_dokumens write;

// $max = SELECT MAX( id ) FROM t_dokumens;
	$id= $row[0]+1;
	// $tipe_dok_id =$_POST["tipe_dok_id"];
	$nomor_dokumen=$_POST["nomor_dokumen"];
	$id_user =$_POST["id_user"];
	$perihal =$_POST["perihal"];
	$pengirim =$_POST["pengirim"];
	$created_at=$_POST['tgl_masuk'];

	$tgl_masuk =date('Y-m-d H:i:s');

	$urutan_ke=$_POST['urutan_ke'];
	$dest_direksi_id=$_POST['dest_direksi_id'];

$query =mysqli_query($con, "INSERT INTO t_dokumens(

  t_dokumens.id,
  t_dokumens.tipe_dok_id,
  t_dokumens.is_closed,
   t_dokumens.id_user,
  t_dokumens.nomor_dokumen,
t_dokumens.tgl_masuk,
t_dokumens.perihal,
  t_dokumens.pengirim

  )VALUES('$id','1','1','$id_user','$nomor_dokumen','$created_at','$perihal','$pengirim')");
$query =mysqli_query($con, "INSERT INTO t_tujuan_dokumens(	
t_tujuan_dokumens.dokumen_id,
t_tujuan_dokumens.urutan_ke,
t_tujuan_dokumens.dest_direksi_id,
t_tujuan_dokumens.created_at,
t_tujuan_dokumens.updated_at,
t_tujuan_dokumens.deleted_at
)VALUES('$id','$urutan_ke','$dest_direksi_id','$tgl_masuk','','')");


if ($query==TRUE) {
	
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