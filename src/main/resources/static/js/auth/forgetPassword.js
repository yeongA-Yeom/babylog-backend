/**
 * forgetPassword.js
 * ---------------------------------------
 * 비밀번호 찾기 페이지 전용
 * - 입력값 검증
 * - 서버 요청 (예정)
 */

const forgetPasswordForm = document.querySelector('#forgetPasswordForm');

forgetPasswordForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const loginId = document.querySelector('.login-id input').value.trim();
    const email = document.querySelector('.email input').value.trim();
    const name = document.querySelector('.name input').value.trim();

    // ===== 프론트 검증 =====
    if (!loginId || !email || !name) {
        alert('모든 항목을 입력해주세요.');
        return;
    }

    console.log('비밀번호 찾기 요청', { loginId, email, name });

    // TODO: 비밀번호 찾기 API 연동
    alert('비밀번호 찾기 요청이 접수되었습니다.');
});
