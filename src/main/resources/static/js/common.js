/**
 * common.js
 * ---------------------------------------
 * 공통으로 사용하는 JS 유틸 모음
 * (auth 페이지 중심)
 */

/**
 * input 포커스 시 outline 색상 변경
 * @param {HTMLElement} input - input 요소
 * @param {string} color - outline 색상
 */
export function outlineColor(input, color) {
    input.addEventListener('focus', () => {
        input.style.outline = `2px solid ${color}`;
    });

    input.addEventListener('blur', () => {
        input.style.outline = 'none';
    });
}
