
let name;
let age;
let gender;
let score;
let status;

let personData;

const btn = document.getElementById("submitBtn");
const tbody = document.getElementById("tbody");


btn.onclick = async function() {
	if(!validation()){
		return;
	} 
	
	if (window.confirm(`정말 제출하시겠습니까?\n\n성명: ${name}\n나이: ${age}\n성별: ${gender}\n점수: ${score}\n상태: ${status}`)) {
		sendData({
			name: name,
			age: age,
			gender: gender,
			score: score,
			status: status
		});
	}
	else {
		await getData();
		console.log("현재 데이터 : ",personData)
		return;
	}

	document.getElementById("personForm").reset();

};


function validation() {
	
	name = document.getElementById("name").value;
	age = document.getElementById("age").value;
	gender = document.querySelector('input[name="gender"]:checked').value;
	score = document.getElementById("score").value;
	status = document.querySelector('input[name="status"]:checked').value;
	console.log(`${name} ${age} ${gender} ${score} ${status}`);

	if (!name || !age || !score) {
		alert("모든 항목을 작성해 주십시오");
		return false;
	}
	if (age < 1 || age > 130) {
		alert("올바른 나이를 입력하세요 (1-130)");
		return false;
	}
	if (score > 100 || score < 0) {
		alert("올바른 점수를 입력하세요 (0-100)");
		return false;
	}
	return true;

}
//then 체인 말고 async await로
async function sendData(person) {
	//async await 
	console.log(person);
	
	try {
        const response = await fetch("/db/api", {
            method: 'POST', // POST 요청 방식
            headers: {
                'Content-Type': 'application/json', // 데이터 형식 지정
            },
            body: JSON.stringify(person), // 데이터를 JSON 문자열로 변환
        });
        // 응답이 성공적인지 확인
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const responseData = await response//백엔드에선 json으로 응답하지않음 //response.json(); // JSON 응답 데이터 파싱
        return responseData; // 파싱된 데이터 반환
    } catch (error) {
        console.error('Error:', error); // 에러 처리
    }
}

async function getData() {
	//async await 
	
	try {
        const response = await fetch('/db/api/list');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json(); // JSON 데이터를 파싱
        console.log('Data:', data); // 파싱된 데이터 사용

        personData = data;//
    } catch (error) {
        console.error('Error:', error); // 에러 처리
    }

}
function rend() {
	


}
function clear() {
	document.getElementById("personForm").reset();
	name = document.getElementById("name").value;
	age = document.getElementById("age").value;
	gender = document.querySelector('input[name="gender"]:checked').value;
	score = document.getElementById("score").value;
	status = document.querySelector('input[name="status"]:checked').value;
}