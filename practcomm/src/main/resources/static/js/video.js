
let keyword = "아이브";
let page = 1;
let is_end = false;

let checked = [];

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
	const section = document.getElementById("section");
	section.innerHTML = "";
	await result.forEach(function(item, index) {
		section.innerHTML = section.innerHTML
			+ `<div class="col-3"><input type="checkbox" name="save" value = "${index}"><img src="${item.thumbnail}"></div>`;
	
	});
	

	let checkboxs = document.getElementsByName("save");
	//checkboxs.addEventListner("click", checkedBoxAll); //element가 모인 nodeList 라서 불가능 하나씩 걸어줘야함
	checkboxs.forEach(function(checkbox) {
		checkbox.addEventListener("click", function(){
			let index = checkbox.value;
			if(checked.includes(result[index].title)){
				console.log();
			}else{
				checked.push(`${result[index].title}#${index+1}.mp4`);
			}
			
			rendList(checked);
		});
	});
	
	
	
}


function rendList(list){
	const listSection = document.getElementById("listSection");
	listSection.innerHTML = "";
	
	list.forEach(function(item){
		listSection.innerHTML += `<div class="col-3">${item}</div>`;
		
		
	});
	
}


/*function checkedBoxAll() {
	const query = 'input[name="save"]:checked';
	const selectedEls = document.querySelector(query);


	console.log(selectedEls);
}*/


