
const email = document.querySelector('.ID input[type = email]');
const password = document.querySelector('.password input[type = password]');
const passwordEye = document.querySelector('.password .eye');
const logIn = document.querySelector('input[type = submit]');
const signUpBtn = document.querySelector('#signUpBtn');
const forgetPasswordBtn = document.querySelector('#forgetPasswordBtn');

signUpBtn.addEventListener('click', () => {
    window.open(
        '/signup',          // 열 페이지 (Spring 매핑)
        'signup',
        'width=600,height=750,resizable=no'
    );
});
forgetPasswordBtn.addEventListener('click', () => {
    window.open(
        '/forgetPassword',          // 열 페이지 (Spring 매핑)
        'forgetPassword',
        'width=600,height=750,resizable=no'
    );
});


// 입력창 포커스 시 아웃라인 색상 함수
function outlineColor(enter, color){
    enter.addEventListener('focus', () => {
        enter.style.outline = `2px solid ${color}`;
    });
    enter.addEventListener('blur', () => {
        enter.style.outline = 'none';
    });
}

// 아이디 입력창, 비밀번호 입력창 비어있을 시 라인 붉은 색으로 변경
logIn.addEventListener('click', function(){
    if(email.value.length === 0){
        email.style.border = '1px solid #FF7D7D';
        outlineColor(email, '#FF7D7D');
    } else{
        email.style.border = '1px solid #CAC7C7';
        outlineColor(email, '#CAC7C7');
    };

    if(password.value.length === 0){
        password.style.border = '1px solid #FF7D7D';
        passwordEye.style.color = '#FF7D7D';
        outlineColor(password, '#FF7D7D')
    } else{
        password.style.border = '1px solid #CAC7C7';
        passwordEye.style.color = '#CAC7C7';
        outlineColor(password, '#CAC7C7');
    };

    // 둘 다 입력되었을 때 로그인 되었다는 알림뜨기
    if(password.value.length > 0 && email.value.length > 0){

        alert("로그인 성공");
    };
});

// 비밀번호 보이기
passwordEye.addEventListener('click', function(){
    if (password.type === 'password') {
        password.type = 'text';
        passwordEye.icon = 'proicons:eye-off';
    } else {
        password.type = 'password';
        passwordEye.icon = 'proicons:eye';
    };
});

const photo = document.querySelector('.photo');
let turn = 1;

setInterval(function() {
    photo.src = `images/0${turn}.jpg`;

    turn++;

    if(turn >= 4){
        turn=1;
    }
}, 3000);
