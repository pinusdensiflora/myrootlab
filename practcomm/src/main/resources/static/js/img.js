let findImg = [];
let saved_memo = {};
let saved_check = {};
let viewnum = 0;

//최상단 체크박스 눌려있을 때 추가하면 체크된 상태로 추가
//전부 선택후 삭제 시, 최상단 체크박스 해제? <= 아직은 그대로 남음
//찾은 이미지들은 리스트로 받고, 데이터를 삭제해도 인덱스는 변하지 않음 delete로 삭제
//

document.addEventListener('DOMContentLoaded', () => {
	const inputField = document.getElementById('keywordInput');

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

	hideLoading();
	message("데이터가 없습니다.");



});


function showLoading() {
	document.getElementById("loading").style.display = "flex"; // 로딩 화면 보이기
}
function hideLoading() {
	document.getElementById("loading").style.display = "none"; // 로딩 화면 안보이기
}
function message(message) {
	const section = document.getElementById("message");
	section.style.display = "flex";
	section.innerHTML = message;
}


async function getresult() {
	if (viewnum >= 5) {
		alert("최대 5개 까지 제공됩니다.");
		return;
	}
	try {

		keyword = document.getElementById("keywordInput").value;
		if (keyword == "") {
			message("검색어가 없습니다.");
			return
		}

		const ajaxResult = await getData();

		if (ajaxResult == undefined) {
			alert("검색결과 없음");
			return;
		}
		//console.log(ajaxResult);
		findImg.push(ajaxResult);
		rend();

	} catch (error) {
		console.error("Error에러: ", error);  // 오류 처리
		message(`Error ${error}`);
	}

}

async function getData() {
	showLoading(); // 데이터를 요청하기 전에 로딩 화면을 표시
	const sort = document.getElementById("sort").value;

	try {
		const response = await fetch(`http://localhost:8080/community/img/api?keyword=${keyword}&sort=${sort}`); // 데이터 요청

		if (response.ok) {
			// 응답이 성공적일 경우
			const result = await response.json(); // JSON 데이터 파싱
			//console.log('Success:', result);
			return result["documents"][0];//바로 알맹이만 반환함

		} else {
			// 응답이 실패할 경우
			const result = await response.json(); // JSON 데이터 파싱
			console.error('Error에러:', result.status, result.statusText);
			alert(`${result.status} Error ${result.statusText}`);
			return;
		}
	} catch (error) {
		console.error("Error fetching data", error);
	} finally {
		hideLoading(); // 데이터 요청이 끝나면 로딩 화면을 숨기기
	}
}

function rend() {
	
	setMemo();
	setCheck();
	const tbody = document.getElementById("tbody");
	tbody.innerHTML = "";
	let num = 1;
	findImg.forEach(function(item, index) {
		if (item) {//empty가 아닐때

			let memo = `<td><input type="text" name ="memo" id="${index}" placeholder="메모를 입력하세요"></td>`
			if (saved_memo[index]) {
				memo = `<td><input type="text" name ="memo" id='${index}' value="${saved_memo[index]}"></td>`;
				//console.log(memo);
			}

			let check;
			if (saved_check[index] || document.getElementById("checkall").checked) {
				check = `<td><input type="checkbox" name="remove" value="${index}" onchange="check(this)" checked></td>`
				saved_check[index] = true; //바로바로 넣어주기.. 안 그러면 전체 체크켜놓고 검색했을때 가장 마지막꺼는 딕셔너리에 안들어감
			} else {
				check = `<td><input type="checkbox" name="remove" value="${index}" onchange="check(this)"></td>`

			}
			
			tbody.innerHTML +=
				`<tr>
					${check}
            		<td>${num}</td>
            		<td><img src = '${item.thumbnail_url}'></td>
            		<td>${item.doc_url}</td>
            		<td>${item.height} x ${item.width}</td>
            		<td>${item.collection}</td>
            		<td>${item.datetime}</td>
            		${memo}
        		</tr>`;
			num++;
		}


	});

	viewnum = num - 1;
	if (viewnum == 0) {//전체삭제 시 최상단 체크박스 체크해제
		document.getElementById("checkall").checked = false;
	}
	//console.log(num, viewnum);

}
function setMemo() {

	let memos = document.getElementsByName("memo");
	memos.forEach(function(memo) {
		//console.log(memo.id, memo.value);
		saved_memo[memo.id] = memo.value;
	});


}
function setCheck() {

	let checkboxes = document.getElementsByName("remove");
	//console.log(checkboxes);
	checkboxes.forEach(checkbox => {
		
		if (checkbox.checked) {
			saved_check[checkbox.value] = true;
		} else {
			saved_check[checkbox.value] = false;
		}
	});
	
	//console.log("setcheck", saved_check);
}

function check(checkbox) {
	
	function alreadyCheckAll() {
		let flag = false;
		let count = 0;
		for (let key in saved_check) {
			if (saved_check[key]) {
				count++;
			}
		}
		if (viewnum == count) {
			//보여지고 있는 viewnum
			flag = true;
		}
		return flag;
	}
	
	saved_check[checkbox.value] = checkbox.checked;
	if (alreadyCheckAll()) {
		document.getElementById("checkall").checked = true;
	} else {
		document.getElementById("checkall").checked = false;
	}
}



function checkAll(thumbCheckbox) {

	if (thumbCheckbox.checked) {
		let checkboxes = document.querySelectorAll(`input[type="checkbox"][name="remove"]`);
		checkboxes.forEach(checkbox => {
			checkbox.checked = true; // 각 체크박스의 checked 속성을 true로 설정
		});
	} else {
		let checkboxes = document.querySelectorAll(`input[type="checkbox"][name="remove"]`);
		checkboxes.forEach(checkbox => {
			checkbox.checked = false; // 각 체크박스의 checked 속성을 true로 설정
		});
	}
	setCheck();

}

function remove() {
	setCheck();
	for (let key in saved_check) {
		if (saved_check[key]) {
			delete findImg[key];
			let checkbox = document.querySelector(`input[type="checkbox"][name="remove"][value="${key}"]`);
			checkbox.checked = false;
			//기존의 체크박스의 체크를 풀어주지 않으면 rend() setCheck() 에서 계속 true 로 바뀜
		}
	}
	rend();
}


function reset() {
	findImg = [];
	saved_check = {};
	saved_memo = {};
	document.getElementById("checkall").checked = false;
	
	//console.log("reset", saved_check);
	
	const tbody = document.getElementById("tbody");
	//tbody.innerHTML = "";
	while (tbody.firstChild) {
       tbody.removeChild(tbody.firstChild);
    }//innerHTML은 잔류하는지 자꾸 체크박스가 체크되어있고 그럼.. 
    
	rend();
	document.getElementById("keywordInput").value = "";
	document.getElementById("sort").selectedIndex = 0;
	const inputField = document.getElementById('keywordInput').focus();
	
}


async function save() {
	function matchmemo() {
		findImg.forEach(function(item, index) {
			if(item){
				item.memo = saved_memo[index];//메모 속성 추가
				console.log(item);
			}
		});
	}
	
	matchmemo();
	
	console.log(findImg);
	
	setMemo();//최종검수
	
	
	
	const url = "http://localhost:8080/community/img/upload";

	try {

		const response = await fetch(url, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},

			body: JSON.stringify(findImg) // JSON 형식으로 데이터 전송 //json으로 보낼 때는 어노테이션 붙이기
			//또는 폼 데이터를 만들어서 전송해도 됨
		});

		const result = await response;

		if (result.ok) {
			// 응답이 성공적일 경우
			const data = await result;//.json(); // post JSON 데이터 파싱ㄴㄴㄴ
			console.log('Success:', data);
			alert("저장되었습니다.");
			reset();
		} else {
			// 응답이 실패할 경우
			console.error('Error에러:', result.status, result.statusText);
			document.getElementById('keywordInput').value = "";
			alert(`${result.status} Error ${result.statusText}`);
			
		}
		//console.log('Response:', result);

	} catch (error) {
		alert(error);
		console.error('Error:', error);
		
	} finally {
		hideLoading(); // 요청 후 로딩 화면을 숨김
	}

}


