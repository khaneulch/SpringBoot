
var Common = {

	fetch ( url, param, _callBack) {
		
		let options = {
			method: 'POST',
			mode: 'cors',
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json;charset=UTF-8'
			},
			body: param ? JSON.stringify( param) : null
		};
		

		fetch(url, options)
			.then( response => {
				if( response && response.ok) {
					response.text().then( data => {
						var obj = data ? JSON.parse(data) : {data : true}; 
						
						if( $.isFunction( _callBack)) {
							_callBack( obj);
						}
					})
				}
			});
	}
};


Vue.filter('currency', function (num) {
	if ( isNaN(num)) num = 0;
	return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
});
