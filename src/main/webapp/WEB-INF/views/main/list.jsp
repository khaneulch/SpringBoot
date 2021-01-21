<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Vue Test</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootswatch/4.5.2/sketchy/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha256-4+XzXVhsDmqanXGHaHvgh1gMQKX40OUvDEBTu8JcmNs=" crossorigin="anonymous"></script>
<script src="../js/common.js"></script>
</head>
<body>
	<div id="example" class="container">
		<table id="list" class="table">
			<thead>
				<tr>	
					<th scope="col">ID</th>
					<th scope="col">NAME</th>
					<th scope="col">PRICE</th>
				</tr>
			</thead>
			<tbody id="items">
				<tr v-for="i in items">
					<th scope="row">{{i.id}}</th>
					<td>{{i.itemName}}</td>
					<td>{{i.itemPrice}}</td>
				</tr>
			</tbody>
		</table>
	</div>

</body>
<script>
	$( function() {
		Common.fetch('/findAll', '', vueListBasic);
	});
	
	/* 기본 리스트 전체 데이터를 뿌려줌 */
	function vueListBasic( resp) {
		console.log(resp);
		
		var model = {
			items : resp
		};
		
		var list = new Vue({
			el : '#example',
			data : model
		});
	}
</script>
</html>