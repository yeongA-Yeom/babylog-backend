/**
 * home.js
 * ---------------------------------------
 * 홈(index) 페이지 전용 스크립트
 * - 메인 이미지 자동 슬라이드
 */

const photo = document.querySelector('.photo');

if (photo) {
    let index = 1;

    setInterval(() => {
        index = index >= 3 ? 1 : index + 1;
        photo.src = `/images/0${index}.jpg`;
    }, 3000);
}
