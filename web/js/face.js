function begin() {
    console.log("begin");
    var f1 = document.getElementById("face1");
    var f2 = document.getElementById("face2");

    document.getElementById("score").innerHTML = "";

    f1.style.backgroundImage = "url('../images/scanner.gif')";
    f2.style.backgroundImage = "url('../images/scanner.gif')";

}
/**
 * Created by sun on 2018/11/26.
 */
function facecomp() {
    console.log("face compare...");

    var f1 = document.getElementById("face1");
    var f2 = document.getElementById("face2");

    var f1n = f1.style.backgroundImage;
    var f2n = f2.style.backgroundImage

    console.log(f1n);
    console.log(f2n);

    begin();

    var xhr = new XMLHttpRequest();

    xhr.open("post", "/Servlet");

    xhr.send();

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200 || xhr.status == 304) {

                // console.log(xhr.responseText);

                //if (xhr.responseText != null) {
                var ret = JSON.parse(xhr.responseText);
                var score = document.getElementById("score");

                console.log("error_msg:" + ret.error_msg);

                if (ret.error_msg == "SUCCESS") {
                    console.log("score:" + ret.result.score);

                    f1.style.backgroundImage = f1n;
                    f2.style.backgroundImage = f2n;

                    score.innerHTML = Number(ret.result.score).toFixed(2) + "%";
                    // score.style.backgroundImage = "url('../images/qinren.png')";
                    //}
                } else {
                    score.innerHTML = ret.error_msg;
                }
            }
        }
    }
}

//first face
function selectfile1() {
    var o = document.getElementById("file1");
    o.click();
}

function uploadpic1() {
    var f = document.getElementById("form1");
    var fd = new FormData(f);

    var xhr = new XMLHttpRequest();
    xhr.open("post", "/ServletUploadPicAjax");
    xhr.send(fd);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200 || xhr.status == 304) {
                var ret = xhr.responseText;
                console.log(ret);

                var o = document.getElementById("face1");
                o.style.backgroundImage = "url(" + ret + ")";
            }
        }
    }
}

//second face
function selectfile2() {
    var o = document.getElementById("file2");
    o.click();
}

function uploadpic2() {
    var f = document.getElementById("form2");
    var fd = new FormData(f);

    var xhr = new XMLHttpRequest();
    xhr.open("post", "/ServletUploadPicAjax2");
    xhr.send(fd);

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4) {
            if (xhr.status == 200 || xhr.status == 304) {
                var ret = xhr.responseText;
                console.log(ret);

                var o = document.getElementById("face2");
                o.style.backgroundImage = "url(" + ret + ")";
            }
        }
    }
}

