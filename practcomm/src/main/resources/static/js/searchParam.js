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
		console.log("페이지 이동");
		const { currkeyword, currpage } = getQueryParams();
		updateContent(currkeyword, currpage);
	});


});



function updateURLAndContent(keyword, page) {
	//히스토리에 파라미터 추가하는 function
	
	if(getQueryParams()['keyword'] == keyword && getQueryParams()['page'] == page){
		console.log("동일파라미터로 히스토리에 추가안함");
		return;
	}

	history.pushState(null, '', `?keyword=${keyword}&page=${page}`);
	updateContent(keyword, page); //리렌더링
}

function updateContent(keyword, page) {
	//const { getkeyword, getpage } = getQueryParams();
	console.log("리렌더링 준비 : ",getQueryParams(), ajaxResult);
}

function getQueryParams() {
	const params = new URLSearchParams(window.location.search);
	return {
		keyword: params.get("keyword"),
		page: params.get("page"),
	};
}


async function search() {
	const keyword = document.getElementById("keyword").value;
	//document.getElementById("keyword").value = ""; //클리어
	if(keyword == ""){
		alert("입력");
		return;
	}
	const page = 1;

	try {
		if (pageableCache[keyword] === undefined) { //pageableCache[keyword] == null 느슨한 비교(null과 undefined 를 둘다 확인)
			
			await getPageable(keyword);
		}
		ajaxResult = await getData(keyword,page); //파라미터를 히스토리에 넣으면서 동시에 리렌더링도 해결됨
	} catch (error) {
		console.error("Error: ", error);
	}

	//getresult();
	//updateContent(keyword,page);

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


function showLoading() {
	document.getElementById("loading").style.display = "flex"; // 로딩 화면 보이기
}

function hideLoading() {
	document.getElementById("loading").style.display = "none"; // 로딩 화면 안보이기
}

async function getData(keyword, page) {
	const url = `http://localhost:8080/community/search-param/api?keyword=${keyword}&page=${page}`;
	// URL에 쿼리 파라미터 추가
	//history.pushState(null, '', `?keyword=${keyword}&page=${page}`);
	updateURLAndContent(keyword, page);


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
	//pageBar(page);
	//console.log(meta, result);
	document.getElementById("resultsection").style.visibility = "visible";
	document.getElementById("nothing").style.display = "none";

	tbody.innerHTML = "";
	//6->0(1번째)  11->0 ... 나머지 *10 
	//7->10(11번째)
	//8->20
	//let lastn = result.length < page * 10 ? result.length : page * 10;
	let startIndex = ((page - 1) % 5) * 10;
	let lastIndex = result.length - 1 < startIndex + 9 ? result.length - 1 : startIndex + 9;
	//console.log(`s: ${startIndex}, l: ${lastIndex}}`);

	for (let i = startIndex; i <= lastIndex; i++) {
		let res = result[i];
		tbody.innerHTML = tbody.innerHTML +
			`<tr>
				<td>${i + 1 + (page - 1) * 50}</td>
				<td><a href = "${res.url}"  target='_blank' class="ellipsis">${res.title}</a></td>
				<td class="ellipsis">${res.contents}</td>
				<td>${res.datetime}</td>
				</tr>`;
	}


}



