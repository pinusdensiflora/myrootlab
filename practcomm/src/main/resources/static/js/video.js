
let keyword = "아이브";
let page = 1;
let is_end = false;

let checked = [];
let checkMap = new Map();

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
		const data = await response.json();
		console.log("data : ", data);
		return data; // 데이터 반환
	} catch (error) {
		console.error("Error fetching data", error);
	} finally {
		hideLoading(); // 데이터 요청이 끝나면 로딩 화면을 숨기기
	}
}


async function getresult() {

	try {

		keyword = document.getElementById("keywordInput").value;

		const ajaxResult = await getData();
		const meta = ajaxResult["meta"];
		const result = ajaxResult["documents"];
		end = meta.is_end;

		console.log(result);
		console.log(meta);
		rend(result);

	} catch (error) {
		console.error("Error: ", error);  // 오류 처리
	}

}

async function rend(result) {
	document.getElementById("titlecontainer").style.display = "flex";
	const section = document.getElementById("section");
	section.innerHTML = "";
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
	
	const url = "http://localhost:8080/community/video/upload"; // 예시 API URL
	
	showLoading(); // POST 요청 전 로딩 화면을 보임
	
	const videoData = {
    "videos": {
        "2": {
            "author": "황인호",
            "datetime": "2022-03-15T23:03:48.000+09:00",
            "play_time": 1134,
            "thumbnail": "https://search4.kakaocdn.net/argon/138x78_80_pr/3ajLZ5JiXiE",
            "title": "카트 인호예요 ㅁㄴㅇㄻㄴㅇㄹ",
            "url": "http://www.youtube.com/watch?v=6_qRzwFKH4E"
        },
        "6": {
            "author": "에코.",
            "datetime": "2021-11-16T19:20:29.000+09:00",
            "play_time": 10022,
            "thumbnail": "https://search1.kakaocdn.net/argon/138x78_80_pr/JP7BY2uV05r",
            "title": "iidx ㅁㄴㅇㄻㄴㅇㄹ",
            "url": "http://www.youtube.com/watch?v=A9bdh-Y8Q3g"
        }
    }
};


	try {
		//그냥 배열로 보내는게 제일 낫다..
		let arr = Array.from(checkMap.values());
		//const nomalObject = Object.fromEntries(checkMap);//일반객체로변환
		//const nomalObject2 = Object.values(nomalObject);//배열로 변환
		console.log("ㅈㅈ ", arr);
		const response = await fetch(url, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			//body: JSON.stringify(checkMap) // JSON 형식으로 데이터 전송 
			body: JSON.stringify(arr) // JSON 형식으로 데이터 전송 
			//또는 폼 데이터를 만들어서 전송해도 됨
			//json으로 보낼 때는 어노테이션 붙이기
		});

		const result = await response;
		console.log('Response:', result);
	} catch (error) {
		console.error('Error:', error);
	} finally {
		hideLoading(); // 요청 후 로딩 화면을 숨김
	}

}



