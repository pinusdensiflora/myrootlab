let tbody;
	

function stop(id){
	console.log(id, "- 중지");
}

function remove(id){
	console.log(id, "- 삭제");
	
	let temp = `@DeleteMapping("/remove")
    public String removeJob(@RequestParam String jobName, 
                            @RequestParam String groupName) throws Exception {
        schedulerService.removeJob(jobName, groupName);
        return "Job removed successfully.";
    }`;
    
    
    
    
}


document.addEventListener('DOMContentLoaded', () => {
	rend();
});


async function getList(){
	
	const url =`http://localhost:8080/community/api/reservation`;
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

async function rend(){
	const list = await getList();
	
	const tbody = document.getElementById("tbody");
	list.forEach(function(item, index){
		tbody.innerHTML += 
		`<tr>
            <td>${index+1}</td>
            <td>${item.keyword}</td>
            <td>${item.type}</td>
            <td>${item.sort}</td>
            <td>${item.cron}</td>
            <td>${item.status}</td>
            <td>${item.createDate}</td>
            <td>
            	<button type="btn" onclick="stop(${item.id})">중지</button>
				<button type="btn" onclick="remove(${item.id})">삭제</button>
        </tr>
        `
	});
	
}


