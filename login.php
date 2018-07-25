<?php
	/* ===== www.dedykuncoro.com ===== */
	include 'konek.php';
	
	class usr{}
	
	$email = $_POST["nip"];
	//$password = $_POST["password"];
	$password = $_POST['password'];
	// if ((empty($username)) || (empty($password))) { 
	// 	$response = new usr();
	// 	$response->success = 0;
	// 	$response->message = "Kolom tidak boleh kosong"; 
	// 	die(json_encode($response));
	// }
	
	$query = mysqli_query($con,"SELECT * FROM users WHERE nip='$email'");
	
	$row = mysqli_fetch_array($query);
	
	if (!empty($row)){
		if(password_verify($password,$row['password'])){
		$response = new usr();
		$response->success = "true";
		$response->message = "Selamat datang ".$row['email'];
		$response->nip = $row['nip'];
		$response->id = $row['id'];
		$response->name = $row['name'];
		$response->email = $row['email'];
		die(json_encode($response));
	}else{
		$response = new usr();
		$response->success = "false";
		$response->message = " password salah";
		die(json_encode($response));
	}
	} else { 
		$response = new usr();
		$response->success = "false";
		$response->message = "Username atau password salah";
		die(json_encode($response));
	}
	
	mysql_close();


	//=================== KALAU PAKAI MYSQLI YANG ATAS SEMUA DI REMARK, TERUS YANG INI RI UNREMARK ========
	// include_once "conn.php";

	// class usr{}
	
	// $username = $_POST["username"];
	// $password = $_POST["password"];
	
	// if ((empty($username)) || (empty($password))) { 
	// 	$response = new usr();
	// 	$response->success = 0;
	// 	$response->message = "Kolom tidak boleh kosong"; 
	// 	die(json_encode($response));
	// }
	
	// $query = mysqli_query($con, "SELECT * FROM users WHERE username='$username' AND password='$password'");
	
	// $row = mysqli_fetch_array($query);
	
	// if (!empty($row)){
	// 	$response = new usr();
	// 	$response->success = 1;
	// 	$response->message = "Selamat datang ".$row['username'];
	// 	$response->id = $row['id'];
	// 	$response->username = $row['username'];
	// 	die(json_encode($response));
		
	// } else { 
	// 	$response = new usr();
	// 	$response->success = 0;
	// 	$response->message = "Username atau password salah";
	// 	die(json_encode($response));
	// }
	
	// mysqli_close($con);

?>