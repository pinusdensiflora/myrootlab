




document.addEventListener('DOMContentLoaded', () => {
	
	
	

	

});


function customOnOff(){
	
	const selectedOption = document.querySelector('input[name="timeOption"]:checked').value;
	
	if(selectedOption == 'custom'){
		document.getElementById("customTime").style.display = 'flex';
	}
	else{
		document.getElementById("customTime").style.display = 'none';
	}

}
function reserve(){
	const type = document.querySelector('input[name="choice1"]:checked').value;
	const sort = document.querySelector('input[name="choice2"]:checked').value;
	const keyword = document.querySelector('input[name="keyword"]').value;
	const timeOption = document.querySelector('input[name="timeOption"]:checked').value;
		
		//timeOption 이 커스텀일때
	 const form = document.getElementById('customTimeForm');
     const formData = new FormData(form);
	 //const name = form.querySelector('#name').value;
     //const email = form.querySelector('#email').value;
     //const age = form.querySelector('#age').value;
	
	if(keyword == ""){
		alert("검색어를 입력하세요");
	}
	
	
	console.log(type, sort, keyword, timeOption);
	
	
}