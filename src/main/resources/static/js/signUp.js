const signUpForm = document.querySelector('#signupForm');

signUpForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const email = document.querySelector('.ID input').value;
    const password = document.querySelector('.password input').value;
    const name = document.querySelector('.name input').value;

    console.log('전송 데이터', { email, password, name });

    fetch('/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            email,
            password,
            name
        })
    })
        .then(res => {
            if (!res.ok) throw new Error('회원가입 실패');
            return res.json();
        })
        .then(() => {
            alert('회원가입 완료!');
            window.close(); // 팝업일 경우
        })
        .catch(err => {
            console.error(err);
            alert('회원가입 중 오류 발생');
        });
});
