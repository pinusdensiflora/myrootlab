




document.addEventListener('DOMContentLoaded', () => {
	
	
	

	

});

function reserve(){
	console.log("reserve");
	const type = document.querySelector('input[name="choice1"]:checked').value;
	const sort = document.querySelector('input[name="choice2"]:checked').value;
	const keyword = document.querySelector('input[name="keyword"]').value;
	const timeOption = document.querySelector('input[name="timeOption"]:checked').value;
		
		//timeOption 이 커스텀일때
	 //const form = document.getElementById('customTimeForm');
     //const formData = new FormData(form);
	 //const name = form.querySelector('#name').value;
     //const email = form.querySelector('#email').value;
     //const age = form.querySelector('#age').value;
	
	if(keyword == ""){
		alert("검색어를 입력하세요");
	}
	
	const jobName = type + sort + timeOption + "|" + keyword  ;

	console.log(jobName);
	save(jobName,timeOption);
	
	
}


async function save(jobName, timeOption) {


	const url = `http://localhost:8080/community/quartz/add`;

	/*const data ={
		"taskId": taskName,
  		"cronExpression": timeOption
	}*/
	const formData = new URLSearchParams();
	formData.append("jobName", jobName);
	formData.append("cronExpression", timeOption);
	
	console.log("전송전, ", formData);
	try {

		const response = await fetch(url, {
			method: 'POST',
			headers: {
				//'Content-Type': 'application/json'
				 "Content-Type": "application/x-www-form-urlencoded"
			},

			//body: JSON.stringify(data) // JSON 형식으로 데이터 전송 //json으로 보낼 때는 어노테이션 붙이기
			//또는 폼 데이터를 만들어서 전송해도 됨
			body : formData
		});

		const result = await response;

		if (result.ok) {
			// 응답이 성공적일 경우
			const data = await result;//.json(); // JSON 데이터 파싱ㄴㄴㄴ
			console.log('Success:', data);
			alert("저장되었습니다.");
			
			
		} else {
			// 응답이 실패할 경우
			console.error('Error에러:', result.status, result.statusText);
			
			
		}
		//console.log('Response:', result);

	} catch (error) {
		alert(error);
		console.error('Error:', error);
		
		
	} 


}