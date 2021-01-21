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
		<div class="input-group mb-3">
			<input type="text" class="form-control" placeholder="search" v-model="search">
		</div>
		<table id="list" class="table">
			<thead>
				<tr>	
					<th scope="col">ID</th>
					<th scope="col">NAME</th>
					<th scope="col">PRICE</th>
				</tr>
			</thead>
			<tbody id="items">
				<tr v-for="i in filtered">
					<th scope="row">{{i.id}}</th>
					<td>{{i.itemName}}</td>
					<td>{{i.itemPrice | currency}}</td>
				</tr>
			</tbody>
		</table>
	</div>

</body>
<script>
	$( function() {
		Common.fetch('/findAll', '', vueListFiltered);
	});
	
	/* 검색된 데이터를 뿌려줌 */
	function vueListFiltered( resp) {
		console.log(resp);
		
		var model = {
			search : '',
			items : resp
		};
		
		var list = new Vue({
			el : '#example',
			data : model,
			computed : {
				/* 검색 */
				filtered : function() {
					var name = this.search.trim();
					return this.items.filter( function( item, idx) {
						if( item.itemName.indexOf(name) > -1) {
							return true;
						}
					})
				}
			}
		});
	}
</script>
</html>