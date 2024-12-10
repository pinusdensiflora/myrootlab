//let keyword = "";
//let page = 1;
let ajaxResult;
let pageableCache = {};


//렌더링시 처리해야할 것
document.addEventListener('DOMContentLoaded', () => {
	const inputField = document.getElementById('keyword');

	// 입력 필드에 포커스 설정(새로고침 시 디폴트)
	inputField.focus();

	// 엔터 키 감지
	document.addEventListener('keydown', (event) => {
		if (document.activeElement === inputField && event.key === 'Enter') {
			//포커스 되어있지 않으면 엔터키를 눌러도 갱신 시키지 않음
			//console.log('엔터 키가 눌렸습니다.');
			search();
		}
	});

	history.pushState(null, '', `?keyword=&page=`);
	//const {keyword, page} = getQueryParams();

	//뒤로가기 앞으로가기 리스너
	window.addEventListener('popstate', () => {
		const params = getQueryParams();
		//console.log("페이지 이동", getQueryParams());
		updateContent(params['keyword'], params['page']);
	});


});

//검색버튼 클릭 시,
async function search() {
	const keyword = document.getElementById("keyword").value;
	//document.getElementById("keyword").value = ""; //클리어
	if (keyword == "") {
		alert("검색어를 입력하세요.");
		return;
	}
	const page = 1;

	try {
		if (pageableCache[keyword] === undefined) { //pageableCache[keyword] == null 느슨한 비교(null과 undefined 를 둘다 확인)

			await getPageable(keyword);
		}
		updateURLAndContent(keyword, page);//에러없이 데이터가 잘 받아와졌으면. 파라미터를 히스토리에 넣으면서 동시에 리렌더링도 해결됨
		//ajaxResult = await getData(keyword,page); 
	} catch (error) {
		console.error("Error: ", error);
	}

	//getresult();
	//updateContent(keyword,page);
}




function updateURLAndContent(keyword, page) {
	//히스토리에 파라미터 추가하고 그 파라미터를 기반으로 렌더링호출 function

	if (getQueryParams()['keyword'] == keyword && getQueryParams()['page'] == page) {
		console.log("동일파라미터로 히스토리에 추가안함", page);
		return;
	}

	history.pushState(null, '', `?keyword=${keyword}&page=${page}`);
	updateContent(keyword, page); //리렌더링
}

async function updateContent(keyword, page) {
	


	//데이터 가져오기
	ajaxResult = await getData(keyword, page);

	//console.log("리렌더링 준비 : ", getQueryParams(), ajaxResult);
	render(keyword, page);
}

function getQueryParams() {
	const params = new URLSearchParams(window.location.search);
	return {
		keyword: params.get("keyword"),
		page: params.get("page"),
	};
}




//로딩 온.오프
function showLoading() {
	document.getElementById("loading").style.display = "flex"; // 로딩 화면 보이기
}

function hideLoading() {
	document.getElementById("loading").style.display = "none"; // 로딩 화면 안보이기
}

async function getPageable(keyword) {
	const url = `http://localhost:8080/community/search-param/api/pageable?keyword=${keyword}`;
	showLoading(); // 데이터를 요청하기 전에 로딩 화면을 표시
	try {
		const response = await fetch(url); // 데이터 요청
		const data = await response.json();
		//console.log("data : ", data);

		pageableCache[keyword] = parseInt(data);
		//return data; // 데이터 반환
	} catch (error) {
		console.error("Error fetching data", error);
	} finally {
		hideLoading(); // 데이터 요청이 끝나면 로딩 화면을 숨기기
	}

}

async function getData(keyword, page) {
	const url = `http://localhost:8080/community/search-param/api?keyword=${keyword}&page=${page}`;

	showLoading(); // 데이터를 요청하기 전에 로딩 화면을 표시
	try {
		const response = await fetch(url); // 데이터 요청
		const data = await response.json();
		//console.log("data : ", data);
		/*end = data["meta"].is_end;
		if (end) {
			console.log("is_end == true");
		}*/
		return data; // 데이터 반환
	} catch (error) {
		console.error("Error fetching data", error);
	} finally {
		hideLoading(); // 데이터 요청이 끝나면 로딩 화면을 숨기기
	}
}








function render(keyword, page) {
	document.getElementById("keyword").value = keyword; //다시 띄우려고



	if (keyword == "") {
		document.getElementById("resultsection").style.visibility = "hidden";
		document.getElementById("nothing").style.display = "block";
		return;
	}

	//const meta = ajaxResult["meta"];
	const result = ajaxResult["documents"];

	let tbody = document.getElementById("tbody");


	if (result.length == 0) {
		tbody.innerHTML = "";
		document.getElementById("resultsection").style.visibility = "hidden";
		document.getElementById("nothing").style.display = "block";
		alert("검색된 내용 없음");

		return;
	}
	rendPageBar(keyword, page);
	//console.log(meta, result);
	document.getElementById("resultsection").style.visibility = "visible";
	document.getElementById("nothing").style.display = "none";

	//console.log("ㅠㅠ", ajaxResult);
	tbody.innerHTML = "";

	for (let i = 0; i <= 9; i++) {
		let res = result[i];
		tbody.innerHTML = tbody.innerHTML +
			`<tr>
				<td>${page * 10 - 9 + i}</td>
				<td><a href = "${res.url}"  target='_blank' class="ellipsis">${res.title}</a></td>
				<td class="ellipsis">${res.contents}</td>
				<td>${res.datetime}</td>
				</tr>`;
	}


}


function rendPageBar(keyword, page) {
	const pageBar = document.getElementById("pageBar");

	let start = (parseInt((page - 1) / 5)) * 5 + 1;
	
	let max = Math.min(Math.ceil(pageableCache[keyword] / 10), start + 4);
	//console.log(pageableCache[keyword], start + 4);
	
	pageBar.innerHTML = `<button type="button" onclick="prevPage('${keyword}', ${page-1})">prev</button>`;
	for (let i = start; i <= max; i++) {

		if (i == page) {
			pageBar.innerHTML += `<button type="button" class="btn btn-primary" onclick="updateURLAndContent('${keyword}', ${i})" value = '${i}' disabled>${i}쪽</button>`

		}
		else {
			pageBar.innerHTML += `<button type="button" class="btn btn-outline-dark" onclick="updateURLAndContent('${keyword}', ${i})" value = '${i}'>${i}쪽</button>`
		}

	}


	pageBar.innerHTML += `<button type="button" onclick="nextPage('${keyword}', ${page+1})">next</button>`;

}


function prevPage(keyword, page){
	const Params = getQueryParams();
	//validation
	if (page < 1) {
		page = 1;
		alert("1페이지 입니다.");
		return;
	}
	updateURLAndContent(keyword, parseInt(Params['page']) - 1);
}
function nextPage(keyword, page){
	const Params = getQueryParams();
	//validation
	if (page > Math.ceil(pageableCache[keyword]/10) ) {
		
		page = Math.ceil(pageableCache[keyword]/10);
		alert(`마지막페이지 입니다.`);
		return;
	}
	updateURLAndContent(keyword, parseInt(Params['page']) + 1);
}