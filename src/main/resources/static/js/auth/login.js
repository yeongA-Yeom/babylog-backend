/**
 * login.js
 * ---------------------------------------
 * 로그인 페이지 전용 스크립트
 * - 입력값 검증
 * - 비밀번호 보기 토글
 * - 회원가입 / 비밀번호 찾기 팝업
 */

import { outlineColor } from '../common.js';

// ===== DOM 요소 =====
const emailInput = document.querySelector('.ID input[type=email]');
const passwordInput = document.querySelector('.password input[type=password]');
const passwordEye = document.querySelector('.password .eye');

const loginBtn = document.querySelector('.Sign-in');
const signUpBtn = document.querySelector('#signUpBtn');
const forgetPasswordBtn = document.querySelector('#forgetPasswordBtn');

// ===== 회원가입 팝업 =====
signUpBtn.addEventListener('click', () => {
    window.open(
        '/signup',
        'signup',
        'width=600,height=750,resizable=no'
    );
});

// ===== 비밀번호 찾기 팝업 =====
forgetPasswordBtn.addEventListener('click', () => {
    window.open(
        '/forgetPassword',
        'forgetPassword',
        'width=600,height=750,resizable=no'
    );
});

// ===== 로그인 버튼 클릭 =====
loginBtn.addEventListener('click', (e) => {
    e.preventDefault();

    let isValid = true;

    // 이메일 검증
    if (!emailInput.value.trim()) {
        emailInput.style.border = '1px solid #FF7D7D';
        outlineColor(emailInput, '#FF7D7D');
        isValid = false;
    } else {
        emailInput.style.border = '1px solid #CAC7C7';
    }

    // 비밀번호 검증
    if (!passwordInput.value.trim()) {
        passwordInput.style.border = '1px solid #FF7D7D';
        passwordEye.style.color = '#FF7D7D';
        outlineColor(passwordInput, '#FF7D7D');
        isValid = false;
    } else {
        passwordInput.style.border = '1px solid #CAC7C7';
        passwordEye.style.color = '#CAC7C7';
    }

    // 실제 로그인 처리 (현재는 테스트용)
    if (isValid) {
        alert('로그인 성공');
        // TODO: 서버 로그인 API 연동
    }
});

// ===== 비밀번호 보이기 / 숨기기 =====
passwordEye.addEventListener('click', () => {
    const isHidden = passwordInput.type === 'password';

    passwordInput.type = isHidden ? 'text' : 'password';
    passwordEye.icon = isHidden
        ? 'proicons:eye-off'
        : 'proicons:eye';
});
