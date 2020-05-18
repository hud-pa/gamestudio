setpicture();



function setpicture() {
    var picture = document.getElementById("pictureDiv").classList.item(1);


    console.log(picture);

    document.getElementById("pictureDiv").style.backgroundImage='url(images/reversi/'+picture+'.jpg)';

}

