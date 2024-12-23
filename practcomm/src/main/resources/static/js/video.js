
let keyword = "";
let page = 1;
//let is_end = false;

let checked = [];
let checkMap = new Map();


document.addEventListener('DOMContentLoaded', () => {
	const inputField = document.getElementById('keywordInput');
	nothing("데이터가 없습니다.");
	// 입력 필드에 포커스 설정(새로고침 시 디폴트)
	inputField.focus();

	// 엔터 키 감지
	document.addEventListener('keydown', (event) => {
		if (document.activeElement === inputField && event.key === 'Enter') {
			//포커스 되어있지 않으면 엔터키를 눌러도 갱신 시키지 않음
			//console.log('엔터 키가 눌렸습니다.');
			getresult();
		}
	});



});



function showLoading() {
	document.getElementById("loading").style.display = "flex"; // 로딩 화면 보이기
}

function hideLoading() {
	document.getElementById("loading").style.display = "none"; // 로딩 화면 안보이기
}

async function getData() {
	showLoading(); // 데이터를 요청하기 전에 로딩 화면을 표시
	try {
		const response = await fetch(`http://localhost:8080/community/video/api?keyword=${keyword}&page=${page}`); // 데이터 요청
		
		if (response.ok) {
			// 응답이 성공적일 경우
			const result = await response.json(); // JSON 데이터 파싱
			console.log('Success:', result);
			return result;
			
		} else {
			// 응답이 실패할 경우
			const result = await response.json(); // JSON 데이터 파싱
			console.error('Error에러:', result.status, result.statusText);
			alert(`${result.status} Error ${result.statusText}`);
			return;
		}
		
		/*const data = await response.json();
		console.log("data : ", data);
		return data; // 데이터 반환*/
		
	} catch (error) {
		console.error("Error fetching data", error);
	} finally {
		hideLoading(); // 데이터 요청이 끝나면 로딩 화면을 숨기기
	}
}


async function getresult() {

	try {

		keyword = document.getElementById("keywordInput").value;
		if (keyword == "") {
			const inputElement = document.getElementById('keywordInput')
			inputElement.style.border = "2px solid red";
			nothing("검색어가 없습니다.");
			return
		}
		document.getElementById('keywordInput').style.border = "2px solid black";
		const ajaxResult = await getData();
		const meta = ajaxResult["meta"];
		const result = ajaxResult["documents"];
		//end = meta.is_end;

		console.log(result);
		console.log(meta);
		rend(result);

	} catch (error) {
		console.error("Error에러: ", error);  // 오류 처리
		nothing(`Error ${error}`);
	}

}

async function rend(result) {
	document.getElementById("titlecontainer").style.display = "flex";
	const section = document.getElementById("section");
	section.innerHTML = "";

	if (result.length == 0) {
		nothing("검색된 데이터가 없습니다.");
		return;
	}

	await result.forEach(function(item, index) {
		//console.log(item);
		section.innerHTML = section.innerHTML
			+ `<div class="col-3"><input type="checkbox" name="save" value = "${index + 1}"><img src="${item.thumbnail}"></div>`;

	});


	let checkboxs = document.getElementsByName("save");
	//checkboxs.addEventListner("click", checkedBoxAll); //element가 모인 nodeList 라서 불가능 하나씩 걸어줘야함
	checkboxs.forEach(function(checkbox) {
		checkbox.addEventListener("click", function() {
			let index = checkbox.value;

			if (checkMap.has(index)) {
				checkMap.delete(index);

			} else {
				//checkMap.set(index, `${result[index-1].title}#${index}.mp4`);
				checkMap.set(index, result[index - 1]);
			}
			//console.log(checkMap);
			rendList(checkMap);
		});
	});



}

function nothing(message) {
	document.getElementById("titlecontainer").style.display = "block";
	const section = document.getElementById("section");
	section.innerHTML = message;
}
function rendList(list) {
	const listSection = document.getElementById("listSection");
	listSection.innerHTML = "";


	console.log(list.keys());

	for (const [key, value] of list.entries()) {
		listSection.innerHTML += `<div class="col-3 box"><input type="checkbox" checked value = "${key}" name = "savelist"> ${value.title}</div>`;
		//console.log(`${key} = ${value}`);
	}


	//제목 리스트 체크박스는 다시 클릭하면 사라짐
	//기존 체크박스의 체크도 해제해야함
	let checkboxs = document.getElementsByName("savelist");
	checkboxs.forEach(function(checkbox) {
		checkbox.addEventListener("click", function() {
			let index = checkbox.value;
			checkMap.delete(index);
			let thumbcheckbox = document.querySelector(`input[type="checkbox"][name="save"][value="${index}"]`);
			thumbcheckbox.checked = false;
			rendList(checkMap);
		});
	});

}


async function save() {

	if (checkMap.size == 0) {
		alert("저장할 비디오가 없습니다.");
		return;
	}

	const result = confirm("저장하시겠습니까?");
	if (result) {
		console.log("확인");
	} else {

		console.log("취소");
		return;
	}


	const url = "http://localhost:8080/community/video/upload";

	showLoading(); // POST 요청 전 로딩 화면을 보임

	let arr = Array.from(checkMap.values());//값만 배열로 변환 //그냥 배열로 보내는게 제일 낫다..
	//키워드 필드 추가 과정
	arr.forEach(function(item) {
		item["keyword"] = keyword;
	})
	console.log("keyword를 추가한 value의 arr : ", arr);
	try {

		const response = await fetch(url, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},

			body: JSON.stringify(arr) // JSON 형식으로 데이터 전송 //json으로 보낼 때는 어노테이션 붙이기
			//또는 폼 데이터를 만들어서 전송해도 됨
		});

		const result = await response;

		if (result.ok) {
			// 응답이 성공적일 경우
			const data = await result;//.json(); // JSON 데이터 파싱ㄴㄴㄴ
			console.log('Success:', data);
			alert("저장되었습니다.");
			clearCheck();
			
		} else {
			// 응답이 실패할 경우
			console.error('Error에러:', result.status, result.statusText);
			document.getElementById('keywordInput').value = "";
			clearCheck();
			nothing(`${result.status} Error ${result.statusText}`);
			alert(`${result.status} Error ${result.statusText}`);
			
		}
		//console.log('Response:', result);

	} catch (error) {
		alert(error);
		console.error('Error:', error);
		nothing(`Error ${error}`);
		
	} finally {
		hideLoading(); // 요청 후 로딩 화면을 숨김
	}



}
function clearCheck() {
	let checkboxes = document.querySelectorAll(`input[type="checkbox"][name="save"]`);
	checkboxes.forEach(checkbox => {
		checkbox.checked = false; // 각 체크박스의 체크 해제
	});
	checkMap.clear();
	rendList(checkMap);
}


function reset(){
	const inputField = document.getElementById('keywordInput');
	inputField.value = "";
	checkMap = new Map();
	rendList(checkMap);
	nothing("데이터가 없습니다.");
	inputField.focus();
}
