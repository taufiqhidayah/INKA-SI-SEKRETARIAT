<?php
include 'konek.php';

$id 			= $_POST['nip'];
$tdk 	= $_POST['tipe_dok_id'];
// $sql = mysqli_query($con,"SELECT orders.*,
// 	markets.name as namapasar
// FROM
// orders
// INNER JOIN markets ON markets.id = orders.market_id ,
// drivers
// WHERE
// orders.`status` = 1 and drivers.status=2 and drivers.id='$id'


// 	")

// $sql = mysqli_query($con,"SELECT
// t_dokumens.id,
// t_dokumens.tipe_dok_id,
// t_dokumens.tgl_masuk,
// t_dokumens.nomor_dokumen,
// t_dokumens.nomor_referensi,
// t_dokumens.nama_dokumen,
// t_dokumens.perihal,
// t_dokumens.pengirim,
// t_dokumens.tgl_dok_referensi,
// t_dokumens.penerima,
// t_dokumens.tgl_keluar,
// t_dokumens.tgl_kembali,
// t_dokumens.is_circular,
// t_dokumens.is_closed,
// t_dokumens.id_user,
// t_dokumens.created_at,
// t_dokumens.updated_at,
// t_dokumens.deleted_at,
// m_tipe_dokumens.description,
// users.nip
// FROM
// t_dokumens
// INNER JOIN m_tipe_dokumens ON t_dokumens.tipe_dok_id = m_tipe_dokumens.id
// INNER JOIN users ON t_dokumens.id_user = users.id
// WHERE users.nip='$id' && t_dokumens.tipe_dok_id ='$tdk'")


$sql = mysqli_query($con,"SELECT
t_dokumens.id,
t_dokumens.nomor_dokumen,
t_dokumens.tgl_masuk,
t_dokumens.pengirim,
t_dokumens.perihal,
m_direksis.jabatan_direksi,
t_tujuan_dokumens.urutan_ke,
m_status_dokumens.description
FROM
t_dokumens
INNER JOIN users ON t_dokumens.id_user = users.id
INNER JOIN t_tujuan_dokumens ON t_tujuan_dokumens.dokumen_id = t_dokumens.id
INNER JOIN m_direksis ON t_tujuan_dokumens.dest_direksi_id = m_direksis.id
INNER JOIN m_tipe_dokumens ON t_dokumens.tipe_dok_id = m_tipe_dokumens.id
INNER JOIN m_status_dokumens ON t_dokumens.is_closed = m_status_dokumens.id
WHERE users.nip='$id' && t_dokumens.tipe_dok_id='$tdk'")
 or die(mysqli_errno());
$response = array();
if(mysqli_num_rows($sql) > 0)
{
	$response["tamu"] = array();
	while ($row = mysqli_fetch_array($sql)) {
		
		$user=array();
		$user["id"]						= $row["id"];
		$user["tgl_masuk"]				= $row["tgl_masuk"];
		$user["nomor_dokumen"]			= $row["nomor_dokumen"];
		// $user["nomor_referensi"]		= $row["nama_dokumen"];
		// $user["nama_dokumen"]			= $row["nama_dokumen"];
		$user["perihal"]				= $row["perihal"];
		$user["urutan_ke"]				= $row["urutan_ke"];
		// $user["tgl_keluar"]				= $row["tgl_keluar"];
		// $user["nip"]					= $row["nip"];
		$user["pengirim"]				= $row["pengirim"];
		$user["jabatan_direksi"]		= $row["jabatan_direksi"];
		$user["description"]			= $row["description"];
		array_push($response["tamu"], $user);
	}
	$response["success"]	= 1;
	$response["message"]	= "Data semua member";
	echo json_encode($response);
}else{
	$response["success"]	= 0;
	$response["message"]	= "Tidak ada data";
	echo json_encode($response);
}

?>


