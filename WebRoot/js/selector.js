function selectAll()
{
	var checkboxes=document.getElementsByName("id");
	var  size  = checkboxes.length;
	for(var i = 0; i<size; i++)
	{
		checkboxes[i].checked = true;
	}
}

function inverse()
{
	var checkboxes=document.getElementsByName("id");
	var  size  = checkboxes.length;
	for(var i = 0; i<size; i++)
	{
		checkboxes[i].checked = !checkboxes[i].checked;;
	}
}

// 删除所有选中的条目
function deleteAll()
{
	window.document.forms[0].submit();
}


// xxx: function(){
		
// }

// var xxx = function(){
	
// }

// function xxx(){
	
// }
