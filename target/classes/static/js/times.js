setTime();

function setTime() {
    var times =  document.getElementsByClassName("time");

    for(var i=0, length =times.length;i<length;i++){
        console.log(times.item(i));
        var parts = times.item(i).innerHTML.split(' ');
        //  console.log(parts[0]);
        times.item(i).innerHTML=parts[0];

    }

    var times =  document.getElementsByClassName("timeComment");
    for(var i=0, length =times.length;i<length;i++){
        //  console.log(times.item(i));
        var parts = times.item(i).innerHTML.split(':');
        //  console.log(parts[0]);
        times.item(i).innerHTML=parts[0]+":"+parts[1];

    }
}
