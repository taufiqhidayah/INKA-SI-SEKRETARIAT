<?php
include "konek.php";
$response = array();
if(isset($_POST["id"]))
{	
	$id = $_POST['id'];
	
	// $tipe_dok_id =$_POST["tipe_dok_id"];
	$nomor_dokumen=$_POST["nomor_dokumen"];
	$id_user =$_POST["id_user"];
	$perihal =$_POST["perihal"];
	$tipe_dok_id =$_POST["tipe_dok_id"];
	$pengirim =$_POST["pengirim"];
	$created_at=date('Y-m-d H:i:s');

	// $dokumen_id	=$_POST['dokumen_id'];
	// $urutan_ke=$_POST['urutan_ke'];
	// $dest_direksi_id=$_POST['dest_direksi_id'];

	// $dokumen_id	=$_POST['dokumen_id'];
	// $urutan_ke=$_POST['urutan_ke'];
	// $dest_direksi_id=$_POST['dest_direksi_id'];
	// $sql = mysqli_query($con,"UPDATE t_dokumens SET nomor_dokumen='$nomor_dokumen',nama_dokumen='$nama_dokumen',perihal='$perihal',pengirim='$pengirim' ,penerima='$penerima'WHERE id='$id'");


$query =mysqli_query($con, "UPDATE t_dokumens SET
 
  t_dokumens.tipe_dok_id='$tipe_dok_id',
  t_dokumens.nomor_dokumen='$nomor_dokumen',
t_dokumens.perihal='$perihal',
  t_dokumens.pengirim='$pengirim' WHERE t_dokumens.id='$id'");

// $query =mysqli_query($con, "INSERT INTO t_tujuan_dokumens(	
// t_tujuan_dokumens.dokumen_id,
// t_tujuan_dokumens.urutan_ke,
// t_tujuan_dokumens.dest_direksi_id,
// t_tujuan_dokumens.created_at,
// t_tujuan_dokumens.updated_at,
// t_tujuan_dokumens.deleted_at
// )VALUES('$id','$urutan_ke','$dest_direksi_id','$created_at','','')");




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
	$response["success"]	= 0;
	$response["message"]	= "Data Kosong";
	echo json_encode($response);
}
?>