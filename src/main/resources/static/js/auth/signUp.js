/**
 * signUp.js
 * ---------------------------------------
 * 회원가입 페이지 전용 스크립트
 * - 입력값 검증
 * - 비밀번호 일치 여부 확인
 * - 회원가입 API 호출
 */

const signUpForm = document.querySelector('#signupForm');

signUpForm.addEventListener('submit', (e) => {
    e.preventDefault();

    // ===== 입력값 =====
    const loginId = document.querySelector('.login-id input').value.trim();
    const password = document.querySelector('.password input').value.trim();
    const rePassword = document.querySelector('.re-password input').value.trim();
    const email = document.querySelector('.email input').value.trim();
    const name = document.querySelector('.name input').value.trim();

    // ===== 프론트 검증 =====
    if (!loginId || !password || !rePassword || !email || !name) {
        alert('모든 항목을 입력해주세요.');
        return;
    }

    if (password !== rePassword) {
        alert('비밀번호가 일치하지 않습니다.');
        return;
    }

    // ===== 서버 요청 =====
    fetch('/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
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
                    throw new Error(msg);
                });
            }
            if (!res.ok) {
                throw new Error('회원가입 실패');
            }
            alert('회원가입이 완료되었습니다.');
            window.close();
        })
        .catch(err => {
            console.error(err);
            alert(err.message);
        });
});
