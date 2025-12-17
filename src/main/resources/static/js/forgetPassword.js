const forgetPasswordForm = document.querySelector('#forgetPasswordForm');

forgetPasswordForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const loginId = document.querySelector('.login-id input').value.trim();
    const email = document.querySelector('.email input').value.trim();
    const name = document.querySelector('.name input').value.trim();

    console.log('전송 데이터', {
        loginId,
        email,
        name
    });

    // ===== 프론트 1차 검증 =====
    if (!loginId || !email || !name) {
        alert('모든 항목을 입력해주세요.');
        return;
    }

    // ===== 서버 요청 =====
    fetch('/users', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            loginId,
            password,
            rePassword,
            email,
            name
        })
    })
        .then(res => {
            if (res.status === 409) {
                return res.text().then(msg => {
                    alert(msg);
                    throw new Error(msg);
                });
            }

            if (!res.ok) {
                throw new Error('회원가입 실패');
            }
        })
        .then(() => {
            alert('회원가입이 완료되었습니다.');
            window.close(); // 팝업일 경우
        })
        .catch(err => {
            console.error(err);
            alert('회원가입 중 오류가 발생했습니다.');
        });
});
