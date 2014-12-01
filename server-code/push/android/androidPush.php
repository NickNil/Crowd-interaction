<?php
	include_once './gmc.php';
	//$gcm = new 
	//if (isset($_POST[msg]))
	//{
		
		//$msg = $_POST['msg'];
		//$regid = $_POST['regid'];
		
		$msg = "1";
		$regid = "APA91bFv_6fAq_SuyFubPrJcDxfktd8YmlzX4gxEEhOahUlOVmKvIu8Y60PDFBuosZiducUxAQva0JWLGxDFXKHOb2bhRlFCILB1jvCIPC6Vz9E5knOmELJQ5M3sQXqX0HOtc8uVWEbtGLme7E4-W2fBKXzdC1th76HJXOezTd5dJB13rGuzRuQ";
		$registatoin_ids = array($regid);
		
		//param s for score!
		$message = array("param" => "s", "score" => $msg);
		$result = /*$gcm->*/sendNotification($registatoin_ids, $message);
		//echo $result;
	//}6
?>