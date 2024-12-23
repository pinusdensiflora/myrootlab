let duplication = true;

let username;
let name;
let password;
let confirmpassword;
let phone;
let email;

document.addEventListener('DOMContentLoaded', () => {

	username = document.getElementById("username");
	name = document.getElementById("name");
	password = document.getElementById("password");
	confirmpassword = document.getElementById("confirmPassword");
	phone = document.getElementById("phone");
	email = document.getElementById("email");

});

async function signup() {

	/*	let frm = document.signupForm;
		frm.method="post";
		frm.action="/parking/signup";
		frm.submit();
		
		alert('업로드 되었습니다.');*/

	const publicKey = await getPublicKey();
	//console.log("publickey : \n",publicKey);

	let frm = document.signupForm; // 폼 선택
	let formData = new FormData(frm); // FormData 객체 생성

	const password = formData.get('password');// frm 내용 가져오김
	const encryptedData = await encryptData(password);
	//console.log("전환: \n",password, encryptedData);
	formData.set('password', encryptedData); // 암호화된 데이터로 대체

	try {
		let response = await fetch("/parking/signup", {
			method: 'POST',
			body: formData
		});

		if (response.redirected) {
			// 서버가 리디렉션 응답을 보낸 경우
			// window.location.href = response.url; // 리디렉션 일단 주석
			alert('업로드 되었습니다.'); // 성공 메시지
		} else {
			let data = await response;//.json(); 
			console.log(data); // 서버로부터 받은 데이터 출력
		}
	} catch (error) {
		console.error('가입 중 오류 발생:', error);
		alert('가입에 실패했습니다. 다시 시도해주세요.');
	}

}


async function isDuplicate() {
	const value = username.value;
	if(value == ""){
		alert("아이디를 입력해주세요");
		return;
	}
	// 정규식 검증
	const regex = /^[a-z0-9_]{4,15}$/;//4-15글자
	if(!regex.test(value)){
		alert("사용불가능한 형식의 아이디 입니다.\n알파벳 소문자와 숫자 기호(_) 로 이루어진\n4-15자리의 아이디를 입력해주세요");
		return;
	}
	
	
	console.log(username);
	try {
		let response = await fetch("/parking/duplicate", {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json' //json 보냄
			},
			//body: JSON.stringify({ 'username': username }) //서버에서 추가 처리 필요
			body: JSON.stringify(value) //단순 문자열 전송
		});

		const result = await response;
		console.log(result);
		if (result.ok) {
			// 응답이 성공적일 경우
			const data = await result.json(); //true false
			duplication = data;
			alert(data ? '이미 존재하는 아이디입니다.' : '사용가능한 아이디 입니다.');

		} else {
			// 응답이 실패할 경우
			console.error('Error에러:', result.status, result.statusText);


		}

	} catch (error) {
		console.error('가입 중 오류 발생:', error);
		alert('가입에 실패했습니다. 다시 시도해주세요.');
	}

}


async function getPublicKey() {
	const response = await fetch('/parking/public-key');
	const publicKey = await response.text();
	return publicKey;
}


async function encryptData(password) {
	const publicKey = await getPublicKey();
	const encrypt = new JSEncrypt(); //PKCS#1 v1.5 패딩
	encrypt.setPublicKey(publicKey);
	const encryptedData = encrypt.encrypt(password);
	return encryptedData;
}

function valid_all(){
	if(!valid_username()){return}
	if(!valid_name()){return}
	if(!valid_password()){return}
	if(!valid_email()){return}
}


function valid_username() {
	const value = username.value;
	if(value == ""){
		alert("아이디를 입력해주세요");
		return false;
	}
	// 정규식 검증
	const regex = /^[a-z0-9_]{4,15}$/;//4-15글자
	if(!regex.test(value)){
		alert("사용불가능한 형식의 아이디 입니다.\n알파벳 소문자와 숫자 기호(_) 로 이루어진\n4-15자리의 아이디를 입력해주세요");
		return false;
	}
	
	if (duplication) {
		alert("아이디 중복체크를 시행해주세요.");
		return false;
	}
	return true;
}

function valid_name() {
	const value = name.value;
	if(value == ""){
		alert("이름를 입력해주세요");
		return false;
	}
	// 정규식 검증
	const regex = /^[가-힣]{2,7}$/;
	if(!regex.test(value)){
		alert("사용불가능한 이름입니다. 조치 필요시 문의바랍니다.");
		return false;
	}
	
	return true;
}

function valid_password(){
	
	const value = password.value;
	const confirmvalue = confirmpassword.value;

	if(value == ""){
		alert("비밀번호를 입력해주세요");
		return false;
	}
	
	// 정규식 검증
	const regex = /^(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>])[A-Za-z\d!@#$%^&*(),.?":{}|<>]{8,20}$/;
	if(!regex.test(value)){
		alert("8자리 이상의 비밀번호를 입력해주세요\n소문자, 숫자, 특수문자를 포함해야합니다.");
		return false;
	}
	
	if(confirmvalue == ""){
		alert("비밀번호 확인이 필요합니다.");
		return false;
	}
	
	if(confirmvalue != value){
		alert("비밀번호가 일치하지 않습니다.");
		return false;
	}
	
	return true;
	
}
function valid_email(){
	const value = email.value;


	if(value == ""){
		alert("이메일을 입력해주세요");
		return false;
	}
	
	const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
	if(!regex.test(value)){
		alert("올바르지 않은 이메일 형식입니다.");
		return false;
	}
	
	return true;

}

function valid_phone(){
	const value = phone.value;


	if(value == ""){
		alert("전화번호를 입력해주세요");
		return false;
	}
	
	const regex = /^[0-9]{10,12}$/;
	if(!regex.test(value)){
		alert("올바르지 않은 이메일 형식입니다.");
		return false;
	}
	
	return true;

}
