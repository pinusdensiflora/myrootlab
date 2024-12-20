




document.addEventListener('DOMContentLoaded', () => {


});

function reserve() {
	console.log("reserve");
	const type = document.querySelector('input[name="choice1"]:checked').value;
	const sort = document.querySelector('input[name="choice2"]:checked').value;
	const keyword = document.querySelector('input[name="keyword"]').value;
	let timeOption = document.querySelector('input[name="timeOption"]:checked').value;
	
	//timeOption 이 커스텀일때
	//const form = document.getElementById('customTimeForm');
	//const formData = new FormData(form);
	//const name = form.querySelector('#name').value;
	//const email = form.querySelector('#email').value;
	//const age = form.querySelector('#age').value;

	if (keyword == "") {
		alert("검색어를 입력하세요");
		return;
	}
	if (timeOption == 'custom') {
		const sec = document.getElementById("sec").value == '' ? '*' : document.getElementById("sec").value;
		const min = document.getElementById("min").value == '' ? '*' : document.getElementById("min").value;
		const hr = document.getElementById("hr").value == '' ? '*' : document.getElementById("hr").value;
		const day = document.getElementById("day").value == '' ? '*' : document.getElementById("day").value;
		const mo = document.getElementById("mo").value == '' ? '*' : document.getElementById("mo").value;
		const week = document.getElementById("week").value == '' ? '*' : document.getElementById("week").value;

		// 값을 공백으로 이어붙이기
		let result = `${sec} ${min} ${hr} ${day} ${mo} ${week} `;
		
		console.log("크론식확인",result, cronValid(result), selectedYearSet);
		if (!cronValid(result)) {
			alert("올바르지 않은 크론식 입니다. 다시 확인해 주세요");
			return;
		}
		if(selectedYearSet.size != 0){
			for (let v of selectedYearSet) {
   				result+=v+',';
			}
			result = result.slice(0, -1);//마지막 쉼표 자르기
			
		}else{
			result += '*';
			
		}
		console.log("최종",result);
		timeOption = result;
		
		const jobName = type + sort + timeOption + "|" + keyword;

		console.log(jobName);
		save(jobName, timeOption);
		return;
	}

	const jobName = type + sort + timeOption + "|" + keyword;

	console.log(jobName);
	save(jobName, timeOption);


}

function cronValid(text) {
	return isValidCron(text, { seconds: true, allowBlankDay: true, alias: true });
}


async function save(jobName, timeOption) {


	const url = `http://localhost:8080/community/quartz/add`;

	/*const data ={
		"taskId": taskName,
			"cronExpression": timeOption
	}*/
	const formData = new URLSearchParams(); //정정) 폼데이터 아니고 Params
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
			body: formData
		});

		const result = await response;
		console.log(result);
		if (result.ok) {
			// 응답이 성공적일 경우
			//const data = await result;//.json(); // JSON 데이터 파싱ㄴㄴㄴ
			const data = await result.text(); //body에 String을 담아서 응답하였고 그것을 읽음(.text())
			console.log('Success:', data);
			alert(data);


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


function customOnOff() {

	const selectedOption = document.querySelector('input[name="timeOption"]:checked').value;

	if (selectedOption == 'custom') {
		document.getElementById("customTime").style.display = 'block';
	}
	else {
		document.getElementById("customTime").style.display = 'none';
	}

}