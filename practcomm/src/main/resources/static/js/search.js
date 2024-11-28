var keyword = "";
var page = 1;
var end;


function search() {
	keyword = document.getElementById("keyword").value;
	page = 1;
	getresult();
}


function ajax() {

	return new Promise((resolve, reject) => {


		if (!keyword) {
			alert("검색어를 입력하세요.");
			return;
		}

		var xhr = new XMLHttpRequest();
		xhr.open('POST', `http://localhost:8080/community/search?keyword=${keyword}&page=${page}`, true);
		xhr.responseType = 'json';

		xhr.onload = function() {
			if (xhr.status >= 200 && xhr.status < 300) {
				// 요청이 성공했을 때
				//result = xhr.response["documents"];
				//console.log(xhr.response);
				resolve(xhr.response); //promise 로 반환해줄때는 resolve, reject이용하면됨
				//console.log(resolve(xhr.response));

			} else {
				// 요청이 실패했을 때
				console.error('Request failed with status: ' + xhr.status);
				reject('Request failed with status: ' + xhr.status);//promise
			}
		};

		xhr.onerror = function() {
			// 네트워크 오류가 발생했을 때
			console.error('Network error');
			reject('Network error');
		};

		xhr.send();
	});

}

async function getresult() {

	try {

		const ajaxResult = await ajax();
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