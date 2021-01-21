<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Vue Test</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootswatch/4.5.2/sketchy/bootstrap.min.css"/>
<link rel="stylesheet" href="../css/style.css" />
</head>
<body>
	<div id="example" class="container" v-cloak>
		<h2>total : {{totalElements}}</h2>
		<div class="input-group mb-3">
			<input type="text" class="form-control" placeholder="search" v-model="search">
		</div>
		<table id="list" class="table">
			<colgroup>
				<col width="20%">
				<col width="20%">
				<col width="20%">
				<col width="20%">
				<col width="20%">
			</colgroup>
			<thead>
				<tr>	
					<th scope="col">ID</th>
					<th scope="col">NAME</th>
					<th scope="col">PRICE</th>
					<th scope="col">-</th>
					<th scope="col">-</th>
				</tr>
			</thead>
			<tbody id="items">
				<tr v-for="(i, k) in content">
					<th scope="row">{{i.id}}</th>
					<td>
						<input type="text" class="form-control" placeholder="name" v-model="i.itemName">
					</td>
					<td>
						<input type="text" class="form-control" placeholder="name" v-model.number="i.itemPrice">
					</td>
					<td><button type="button" class="btn btn-primary" @click="updateItem(k)">수정</button></td>
					<td><button type="button" class="btn btn-primary" @click="deleteItem(k)">삭제</button></td>
				</tr>
				<tr v-bind:class="{ none : isActive }">
					<td colspan="2">
						<div class="input-group mb-3">
							<input type="text" class="form-control" placeholder="name" v-model="itemName">
						</div>
					</td>
					<td>
						<div class="input-group mb-3">
							<input type="number" class="form-control" placeholder="price" v-model.number="itemPrice">
						</div>
					</td>
					<td>
						<button type="button" class="btn btn-primary" @click="saveItem">저장</button>
					</td>
					<td>
						<button type="button" class="btn btn-primary" @click="cancelItem">취소</button>
					</td>
				</tr>
			</tbody>
		</table>
		
		<button type="button" class="btn btn-primary" v-bind:class="{none : !isActive}" @click="addClick">추가</button>
		
		<pagination :total="totalPages" :pagenum="number" :link="link"></pagination>
	</div>

</body>

<script type="module" src="./js/listFiltered.js"></script>
</html>