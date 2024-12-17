let tbody;


async function pause(id, jobname) {
	console.log(id, "- 중지");
	const Params = new URLSearchParams();
	Params.append("jobName", jobname);
	Params.append("id", id);
	const url = "http://localhost:8080/community/quartz/pause"
	
	try {

		const response = await fetch(url, {
			method: 'POST',
			headers: {
				//'Content-Type': 'application/json',
				'Content-Type': 'application/x-www-form-urlencoded',
			},
			//body: JSON.stringify({ id: id, jobName : jobname})//DTO 안만드려고
			body: Params.toString(),
		});

		if (!response.ok) {
			console.log(response.status);
        	throw new Error('Network response was not ok');
    	}
    	
    	 const responseMessage = await response.text(); 
    	 return responseMessage;
	}
	catch(error) {
		
	
	}finally{
		rend();
	}
	
}
async function resume(id, jobname){
	console.log(id, "- 재가동");
	const Params = new URLSearchParams();
	Params.append("jobName", jobname);
	Params.append("id", id);
	const url = "http://localhost:8080/community/quartz/resume"
	try {

		const response = await fetch(url, {
			method: 'POST',
			headers: {
				//'Content-Type': 'application/json',
				'Content-Type': 'application/x-www-form-urlencoded',
			},
			//body: JSON.stringify({ id: id, jobName : jobname})//DTO 안만드려고
			body: Params.toString(),
		});

		if (!response.ok) {
			console.log(response.status);
        	throw new Error('Network response was not ok');
    	}
    	
    	 const responseMessage = await response.text(); 
    	 return responseMessage;
	}
	catch(error) {
		
	
	}finally{
		rend();
	}
	
}
async function remove(id, jobname) {
	console.log(id, "- 삭제");

    const Params = new URLSearchParams();
	Params.append("jobName", jobname);
	Params.append("id", id);
	const url = "http://localhost:8080/community/quartz/remove"
	
	try {

		const response = await fetch(url, {
			method: 'DELETE',
			headers: {
				//'Content-Type': 'application/json',
				'Content-Type': 'application/x-www-form-urlencoded',
			},
			//body: JSON.stringify({ id: id, jobName : jobname})//DTO 안만드려고
			body: Params.toString(),
		});

		if (!response.ok) {
			console.log(response.status);
        	throw new Error('Network response was not ok');
    	}
    	
    	 const responseMessage = await response.text(); 
    	 return responseMessage;
	}
	catch(error) {
		
	
	}finally{
		rend();
	}

}


document.addEventListener('DOMContentLoaded', () => {
	rend();
});


async function getList() {

	const url = `http://localhost:8080/community/api/reservation`;
	// URL에 쿼리 파라미터 추가
	//history.pushState(null, '', `?keyword=${keyword}&page=${requestPage}`);

	//showLoading(); // 데이터를 요청하기 전에 로딩 화면을 표시
	try {
		const response = await fetch(url); // 데이터 요청
		const data = await response.json();
		console.log("data : ", data);
		return data; // 데이터 반환
	} catch (error) {
		console.error("Error fetching data", error);
	} finally {
		//hideLoading(); // 데이터 요청이 끝나면 로딩 화면을 숨기기
	}

}

async function rend() {
	const list = await getList();
	
	const tbody = document.getElementById("tbody");
	tbody.innerHTML = "";
	list.forEach(function(item, index) {
		let customBtn;
		if(item.status == "실행 중"){
			customBtn = `<button type="btn" onclick="pause(${item.id}, '${item.jobname}')">중지</button>`;
		}else{
			customBtn = `<button type="btn" onclick="resume(${item.id}, '${item.jobname}')">재가동</button>`
		}
		
		tbody.innerHTML +=
			`<tr>
            <td>${index + 1}</td>
            <td>${item.keyword}</td>
            <td>${item.type}</td>
            <td>${item.sort}</td>
            <td>${item.cron}</td>
            <td>${item.status}</td>
            <td>${item.createDate}</td>
            <td>
            	${customBtn}
				<button type="btn" onclick="remove(${item.id}, '${item.jobname}')">삭제</button>
        </tr>
        `;
        
        
	});

}


