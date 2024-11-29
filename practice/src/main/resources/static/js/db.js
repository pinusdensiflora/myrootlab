
let name;
let age;
let gender;
let score;
let status;

let personData;

const btn = document.getElementById("submitBtn");
const tbody = document.getElementById("tbody");


/* 데이터 새로고침 부분 */
const cbtn = document.getElementById("checkBtn");
cbtn.onclick = async function(){
	await getData(()=>{
		console.log("콜백");
		rend();
	});
	//콜백으로 완료시 rend()가 실행되게
}

/* 데이터 입력으로 이뤄지는 부분 */
btn.onclick = async function() {//getData 부분 처리위해 async
	if(!validation()){
		return;
	} 
	
	if (window.confirm(`정말 제출하시겠습니까?\n\n성명: ${name}\n나이: ${age}\n성별: ${gender}\n점수: ${score}\n상태: ${status}`)) {
		await sendData({
			name: name,
			age: age,
			gender: gender,
			score: score,
			status: status
		});
		
		document.getElementById("personForm").reset();
	
		await getData(()=>{
			console.log("콜백");
			rend();
		});
		//콜백으로 완료시 rend()가 실행되게
	
		
	}
	else {
		//확인창에서 취소
		return;
	}

	
	

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

/* 데이터 가져오기 띄우기 */
async function getData(callback) {//콜백으로 rend()로 연결하려고
	//async await 
	
	try {
        const response = await fetch('/db/api/list');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json(); // JSON 데이터를 파싱
        console.log('Data:', data); // 파싱된 데이터 사용

        personData = data;//
        
        if (callback) {//파라미터에 콜백함수가 있으면
      		callback(); // 콜백 함수 호출 (필요 시)
    	}
        
        
    } catch (error) {
        console.error('Error:', error); // 에러 처리
    }

}
function rend() {
	console.log("현재 데이터 : ",personData);
	tbody.innerHTML = "";

	personData.forEach(function(data){
		//console.log(data.title);
		const datarow = document.createElement('tr');
		
		const name = document.createElement('td');
		const agerange = document.createElement('td');
		const gender = document.createElement('td');
		const grade = document.createElement('td');
		const status = document.createElement('td');
		
		name.textContent = data.name;
		agerange.textContent = data.agegroup;
		gender.textContent = data.gender;
		grade.textContent = data.grade;
		status.textContent = data.status;
		
		datarow.appendChild(name);
		datarow.appendChild(agerange);
		datarow.appendChild(gender);
		datarow.appendChild(grade);
		datarow.appendChild(status);
		
		tbody.appendChild(datarow);
		
	});

	

}
function clear() {
	document.getElementById("personForm").reset();
	name = document.getElementById("name").value;
	age = document.getElementById("age").value;
	gender = document.querySelector('input[name="gender"]:checked').value;
	score = document.getElementById("score").value;
	status = document.querySelector('input[name="status"]:checked').value;
}

//DOMContentLoaded 이벤트 : 페이지가 완전히 로드될 때 일어나기 때문에, 
//즉시 실행함수에서 html객체에 접근 시 null 값이 받아와지는 등의 문제에서 해결될 수 있다.
document.addEventListener("DOMContentLoaded", async function() {
    await firstFarming();
});

async function firstFarming() {
    personData = await getData(() => rend());
}