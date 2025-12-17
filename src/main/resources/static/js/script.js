let carouselImg = document.querySelector('.photo');
let imgNum = 1;

setInterval(carousel, 3000);
function carousel(){
    imgNum += 1;

    if(imgNum >= 4){
        imgNum = 1;
    }

    carouselImg.src = `/images/0${imgNum}.jpg`;
}