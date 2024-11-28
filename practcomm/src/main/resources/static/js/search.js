var keyword = "";
var page = 1;
var end;


function search() {
	keyword = document.getElementById("keyword").value;
	page = 1;
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
        const response = await fetch(`http://localhost:8080/community/search?keyword=${keyword}&page=${page}`); // 데이터 요청
        const data = await response.json();
        console.error("data : ",data);
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
		let tbody = document.getElementById("tbody");
		
		if(result.length == 0){
			tbody.innerHTML = "";
			document.getElementById("resultsection").style.visibility = "hidden";
			document.getElementById("nothing").style.display = "block";
			alert("검색된 내용 없음");
			
			return;
		}
		//console.log(meta, result);
		document.getElementById("resultsection").style.visibility = "visible";
		document.getElementById("nothing").style.display = "none";
		
		tbody.innerHTML = "";
		for (let i = 0; i < result.length; i++) {
			let res = result[i];
			//console.log(res);
			tbody.innerHTML = tbody.innerHTML + 
				`<tr>
				<td>${page*10 + i - 9}</td>
				<td><a href = "${res.url}"  target='_blank' >${res.title}</a></td>
				<td>체크</td>
				<td>체크</td>
				</tr>`;
		}





	} catch (error) {
		console.error("Error: ", error);  // 오류 처리
	}

}