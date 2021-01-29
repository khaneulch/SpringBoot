
<template id="pagination-template">
	<div id="pagination">
		<nav aria-label="Page navigation example">
			<ul class="pagination">
				<li class="page-item" v-for="i in pagearr" v-bind:key="i" v-bind:class="{active : i == number + 1}"><a class="page-link" @click="pageChange(i)">{{i}}</a></li>				
			</ul>
		</nav>
	</div>
</template>

<script>
	import Common from '../../main/resources/static/js/common';

	export default {
		template	: '#pagination-template',
		props		: ['totalPages', 'number', 'link'],
		data() {
			return {
				pagearr : []
			}
		},
		watch : {
			number() {
				this.pageDataRender();
			},
			totalPages() {
				this.pageDataRender();
			}
		},
		methods : {
			pageChange( page) {
				var _arr = this.pagearr;
				var _vue = this;
				
				if( page == 'prev') page = _arr[1] - 1;
				if( page == 'next') page = _arr[_arr.length - 2] + 1;
				
				Common.fetch('/api/findAllPage', {page : String(page - 1)}, function( data) {
					_vue.$emit('childs-event', data.content, data.number);
				});
			},
			pageDataRender() {
				this.pagearr = [];
				var _total = this.totalPages;
				var _number = this.number;
				if( _total < 10) {
					for(var i = 1; i <= _total; i ++) {  
						this.pagearr.push(i);
					}
				} else if( _total >= 10) {
					if( _number < 5) {
						for( var i = 1; i <= _number + 4; i++) {
							this.pagearr.push(i);
						}
					} else {
						if( _number + 4 > _total) {
							for( var i = _number - 4; i <= _total; i++) {
								this.pagearr.push(i);
							}
						} else if( _number + 4 <= _total) {
							for( var i = _number - 4; i <= _number + 4; i++) {
								this.pagearr.push(i);
							}
						}
					}
				}
				
				if( this.pagearr[0] > 1) {
					this.pagearr.splice( 0, 0, 'prev' );
				}
				
				if( this.pagearr[this.pagearr.length - 1] < _total) {
					this.pagearr.splice( this.pagearr.length, 0, 'next' );
				}
			}
		}
	}
</script>