async function signup(){
	
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


async function isDuplicate(){
	

	
	
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