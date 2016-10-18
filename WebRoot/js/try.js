var logout = function(path) {
	var result = window.confirm("是否确认注销？");
	if (result) {
		// 确认注销
		window.location.href = path;
	}
}

//window.alert("欢迎使用登录系统");