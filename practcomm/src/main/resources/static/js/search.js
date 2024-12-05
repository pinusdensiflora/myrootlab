var keyword = "";
var page = 1;
var end;
let ajaxResult;
let requestPage = 1;


async function search() {
	keyword = document.getElementById("keyword").value;
	requestPage = 1;
	page = 1;
	start = 0;

	try {
		ajaxResult = await getData();
		//50개의 데이터가
	} catch (error) {
		console.error("Error: ", error);  // 오류 처리
	}
	getresult();

}


function showLoading() {
	document.getElementById("loading").style.display = "flex"; // 로딩 화면 보이기
}

function hideLoading() {
	document.getElementById("loading").style.display = "none"; // 로딩 화면 안보이기
}

async function getData() {
	showLoading(); // 데이터를 요청하기 전에 로딩 화면을 표시
	try {
		const response = await fetch(`http://localhost:8080/community/search?keyword=${keyword}&page=${requestPage}`); // 데이터 요청
		const data = await response.json();
		console.log("data : ", data);
		end = data["meta"].is_end;
		if(end){
			console.log("is_end == true");
		}
		return data; // 데이터 반환
	} catch (error) {
		console.error("Error fetching data", error);
	} finally {
		hideLoading(); // 데이터 요청이 끝나면 로딩 화면을 숨기기
	}
}


function getresult() {
	
	//const meta = ajaxResult["meta"];
	const result = ajaxResult["documents"];
	//end = meta.is_end;
	let tbody = document.getElementById("tbody");


	if (result.length == 0) {
		tbody.innerHTML = "";
		document.getElementById("resultsection").style.visibility = "hidden";
		document.getElementById("nothing").style.display = "block";
		alert("검색된 내용 없음");

		return;
	}
	pageBar(page);
	//console.log(meta, result);
	document.getElementById("resultsection").style.visibility = "visible";
	document.getElementById("nothing").style.display = "none";

	tbody.innerHTML = "";
//6->0(1번째)  11->0 ... 나머지 *10 
//7->10(11번째)
//8->20
	//let lastn = result.length < page * 10 ? result.length : page * 10;
	let startIndex = ((page-1)%5) * 10;
	let lastIndex = result.length-1 < startIndex + 9 ? result.length-1 :startIndex + 9;
	console.log(`s: ${startIndex}, l: ${lastIndex}}`);
	
	for (let i = startIndex ; i <= lastIndex ; i++) {
		let res = result[i];
		tbody.innerHTML = tbody.innerHTML +
			`<tr>
				<td>${i + 1}</td>
				<td><a href = "${res.url}"  target='_blank' >${res.title}</a></td>
				<td>체크</td>
				<td>체크</td>
				</tr>`;
	}


}
function pageSet(btn) {
	//console.log(btn);
	//console.log(btn.value);
	page = btn.value;
	getresult();


}
async function nextPage() {
	if(end){
		alert("더 이상 검색 내용이 없습니다.");
		return;
	}
	requestPage += 1;

	try {
		const temp = await getData();
		
		ajaxResult = temp;
		page = (requestPage - 1) * 5 + 1;
		
		
		
	} catch (error) {
		console.error("Error: ", error);  // 오류 처리
	}
	
	getresult();

}
async function prevPage() {
	if(requestPage == 1){
		alert("이전페이지 없음");
		return;
	}
	requestPage -= 1;

	try {
		const temp = await getData();
		
		ajaxResult = temp;
		page = (requestPage - 1) * 5 + 5 ;
		console.log(ajaxResult, page);
		
		
	} catch (error) {
		console.error("Error: ", error);  // 오류 처리
	}
	
	getresult();

}

function pageBar(index) {
	let start = (requestPage - 1) * 5 + 1;

	//일단 5쪽까지만
	//4쪽이 끝일 경우 5쪽은 내용은 동일하고 왼쪽 값만 변경되므로 끝 확인이 가능하면 지워야함
	const pageBar = document.getElementById("pageBar");


	//pageBar.innerHTML = start == 1 ? "" : `<button type="button" onclick="prevPage()">이전</button>`;
	pageBar.innerHTML = `<button type="button" onclick="prevPage()">이전</button>`;

	let i;
	let pageCount = 4;
	
	if(end){
		pageCount = parseInt((ajaxResult["documents"].length-1)/10);
		
	}
	
	
	for (i = 0; i <= pageCount; i++) {
		/*
		if(parseInt((i-1)%5)+1 >= parseInt((ajaxResult["documents"].length-1)/10)){ // 27개(20) : 6 7 8 9 10 => 1 2 3 4 0  // 5 6 7 8 9 => 0 1 2 3 4 +1
			console.log(`${i} 쪽 페이지는 없음.`)
			i = -1;
			break;//이후 페이지가 없다면
		
		}*/
	


		if (i+start == index) {
			pageBar.innerHTML += `<button type="button" class="btn btn-outline-dark active" onclick="pageSet(this)" value = '${i+start}'>${i+start}쪽</button>`

		}
		else {
			pageBar.innerHTML += `<button type="button" class="btn btn-outline-dark" onclick="pageSet(this)" value = '${i+start}'>${i+start}쪽</button>`
		}

	}

	if (i == -1) {
		return;
	}


	pageBar.innerHTML += `<button type="button" onclick="nextPage()">다음</button>`;



}
